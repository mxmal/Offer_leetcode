public class Outclass {
    private int a;

    private void setA() {

    }
    ////内部类可以随意使用外部类的方法和属性，不论是否是private的
    public class InnerClass {
        private void callA() {
            setA();
            a = 2;           
        }
    }
    public static void main(String[] args) {
        Outclass oc = new Outclass();
        Outclass.InnerClass innerClass=oc.new InnerClass();
        innerClass.callA();
        oc.out();
    }
    public  void out() {
        System.out.println(a);
    }
}