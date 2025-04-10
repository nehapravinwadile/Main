import java.util.Scanner;

public class TokenRing {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Get message input
        System.out.println("Enter the message: ");
        String message = sc.nextLine();

        // Get number of processes
        System.out.println("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.println("The processes are: ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("1\n");

        // Get sender and receiver
        System.out.println("Enter the sender: ");
        int sender = sc.nextInt();

        System.out.println("Enter the receiver: ");
        int receiver = sc.nextInt();

        // Token passing from sender to receiver
        for (int i = 1; i <= n; i++) {
            if (i == sender) {
                System.out.println("Sender process -> " + sender);
                System.out.println("The token is received by the sender");
                System.out.println("The message to be sent: " + message);
                break;
            }
        }

        // Simulating token passing in a ring
        for (int i = sender + 1; i != receiver; i = (i % n) + 1) {
            System.out.println("Token passed to process -> " + i);
        }

        // Message received by the receiver
        System.out.println("\nReceiver process -> " + receiver);
        System.out.println("The message received: " + message);

        sc.close();
    }
}
