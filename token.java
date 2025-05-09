
import java.util.*;

public class TokenRing{
public static void main(String args[]){
Scanner sc=new Scanner(System.in);
System.out.println("Enter number of nodes you want in ring:");
int n=sc.nextInt();

System.out.print("Ring Formed:");
for(int i=0;i<n;i++){
System.out.print(i+" ");
}
System.out.println("0");

int choice=0;
int token=0;
do{
System.out.print("Enter sender:");
int sender=sc.nextInt();

System.out.print("Enter receiver:");
int receiver=sc.nextInt();

System.out.print("Enter data to send:");
int data=sc.nextInt();

System.out.print("Token passing:");
for(int i=token;i!=sender;i=(i+1)%n){
System.out.print(i+"->");
}
System.out.println(sender);
System.out.println("Sender "+sender+" sending data "+data);

for(int i=sender;i!=receiver;i=(i+1)%n){
System.out.println("Data"+data+" forwarded by "+i);
}
System.out.println("receiver "+receiver+" received the data "+data);

token=sender;

System.out.println("Do you want to continue:");
System.out.println("No(press 0)");
System.out.println("Yes(press 1)");
choice=sc.nextInt();
}while(choice==1);
}
}

/*OUTPUT
sanika@sanika-VirtualBox:~/DS$ javac tokenring.java
sanika@sanika-VirtualBox:~/DS$ java tokenring
Enter number of nodes you want in ring:
6
Ring Formed:0 1 2 3 4 5 0
Enter sender:2
Enter receiver:5
Enter data to send:50
Token passing:0->1->2
Sender 2 sending data 50
Data50 forwarded by 2
Data50 forwarded by 3
Data50 forwarded by 4
receiver 5 received the data 50
Do you want to continue:
No(press 0)
Yes(press 1)
1
Enter sender:1
Enter receiver:4
Enter data to send:100
Token passing:2->3->4->5->0->1
Sender 1 sending data 100
Data100 forwarded by 1
Data100 forwarded by 2
Data100 forwarded by 3
receiver 4 received the data 100
Do you want to continue:
No(press 0)
Yes(press 1)
*/
