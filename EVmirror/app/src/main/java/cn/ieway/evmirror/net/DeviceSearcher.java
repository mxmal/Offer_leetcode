package cn.ieway.evmirror.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import cn.ieway.evmirror.entity.DeviceBean;
import cn.ieway.evmirror.util.LogUtil;
import cn.ieway.evmirror.util.NetWorkUtil;


/**
 * 设备搜索类
 * 搜索局域网下设备IP地址
 */
public abstract class DeviceSearcher extends Thread {
    private static final String TAG = DeviceSearcher.class.getSimpleName();

    private static final int DEVICE_FIND_PORT = /*5679*/5003; //需要扫描的端口
    private static final int RECEIVE_TIME_OUT = 500; // 接收超时时间
    private static final int RESPONSE_DEVICE_MAX = 200; // 响应设备的最大个数，防止UDP广播攻击
    private static final int SEND_TIME_MAX = 2; // 广播发送次数
    private DatagramSocket hostSocket;
    private Set<DeviceBean> mDeviceSet;
    private String ipAddress = ""; //本机ip地址
    int[] pionts = new int[]{DEVICE_FIND_PORT, 5680, 5681, 5682, 5683, 5684, 5685, 5686, 5687, 5688, 5689};//本地绑定端口
    Gson gson;
    private static final String SERVER_BROADCAST = "ev_screen_share_server_broadcast";

    protected DeviceSearcher() {
        mDeviceSet = new HashSet<>();
        gson = new GsonBuilder().create();
    }

    static class BroadCastBean {
        String msg_type = "ev_screen_share_client_broadcast";
        String id;
        String name;
        String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(String msg_type) {
            this.msg_type = msg_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public void run() {
        try {
            onSearchStart();
            BroadCastBean broadCastBean = new BroadCastBean();
            broadCastBean.setId(String.valueOf(System.currentTimeMillis()));
            byte[] sendData = gson.toJson(broadCastBean).getBytes();
            if (sendData == null) {
                return;
            }
            ipAddress = NetWorkUtil.getIpAddress();

            InetAddress broadIP = InetAddress.getByName("255.255.255.255");

            DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, broadIP, DEVICE_FIND_PORT);
            hostSocket = new DatagramSocket(null);
            // 设置接收超时时间
            hostSocket.setSoTimeout(RECEIVE_TIME_OUT);
            hostSocket.setReuseAddress(true);
            for (int p : pionts) {
                hostSocket.bind(new InetSocketAddress(p));
                if (hostSocket.isBound()) {
                    break;
                }
            }

            for (int i = 0; i < SEND_TIME_MAX; i++) {
                // 发送搜索广播
                hostSocket.send(sendPack);
                // 监听来信
                byte[] receData = new byte[1024];
                DatagramPacket recePack = new DatagramPacket(receData, receData.length);
                try {
                    // 最多接收RESPONSE_DEVICE_MAX个，或超时跳出循环
                    int rspCount = RESPONSE_DEVICE_MAX;
                    while (rspCount-- > 0) {
                        hostSocket.receive(recePack);
                        if (parsePack(recePack)) {
                        }
                    }
                } catch (SocketTimeoutException e) {
                    LogUtil.i("[DeviceSearcher] run() SocketTimeoutException " + e.toString());
//                    e.printStackTrace();
                }
            }
            onSearchFinish(mDeviceSet);
        } catch (UnknownHostException e) {
            Log.d(TAG, "run: UnknownHostException: " + e.getMessage());
            e.printStackTrace();
        } catch (SocketException e) {
            Log.d(TAG, "run: SocketException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "run: IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, "run: Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (hostSocket != null) {
                hostSocket.close();
            }
        }
    }

    /**
     * 搜索开始时执行
     */
    public abstract void onSearchStart();

    /**
     * 搜索结束后执行
     *
     * @param deviceSet 搜索到的设备集合
     */
    public abstract void onSearchFinish(Set deviceSet);

    /**
     * 解析报文
     * 协议：$ + packType(1) + data(n)
     * data: 由n组数据，每组的组成结构type(1) + length(4) + data(length)
     * type类型中包含name、room类型，但name必须在最前面
     */
    private boolean parsePack(DatagramPacket pack) {
        if (pack == null || pack.getAddress() == null) {
            return false;
        }
        String ip = pack.getAddress().getHostAddress();
        int port = pack.getPort();
        if (ip.equals(ipAddress)) { //过滤本地广播
            return false;
        }

        for (DeviceBean d : mDeviceSet) { //过滤重复广播
            if (d.getUrl().contains(ip)) {
                return false;
            }
        }

        int dataLen = pack.getLength();
        DeviceBean device = null;
        if (dataLen < 2) {
            return false;
        }

        byte[] data = new byte[dataLen];
        System.arraycopy(pack.getData(), pack.getOffset(), data, 0, dataLen);//获取有效data
        String dataStr = new String(data);
        Log.d(TAG, "parsePack: "+dataStr);
        BroadCastBean bean = gson.fromJson(new String(data), BroadCastBean.class);

//        if (!bean.getMsg_type().equals(SERVER_BROADCAST)) {
//            return false;
//        }

        device = new DeviceBean();
        device.setName(bean.getName());
        device.setUrl(bean.getUrl());

        if (device != null) {
            mDeviceSet.add(device);
            return true;
        }
        return false;
    }


}
