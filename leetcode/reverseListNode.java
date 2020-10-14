public class reverse{
   public ListNode reverseListNode(ListNode head){
        ListNode pre=null;
        while(head!=null){
            ListNode ne = head.next;
            head.next = pre;
            pre = head;
            head = ne;;
        }
   }
}