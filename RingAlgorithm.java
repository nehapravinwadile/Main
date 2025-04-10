
import java.util.Scanner;
public class RingAlgorithm {
    static int num_pr, old_cord, new_cord, initiator, failed_process;
    static int[] isActive, arr;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes:"); 
        num_pr = sc.nextInt();
        isActive = new int[num_pr + 1];

        for (int i = 1; i <= num_pr; i++) isActive[i] = 1;
        old_cord = num_pr;
        isActive[old_cord] = 0;

        System.out.println("Enter the process that initiates the election process:");
        initiator = sc.nextInt();
        System.out.println("The process that failed is: " + old_cord);

        System.out.println("Enter the process that fails (other than the leader process), if none then enter 0:");
        failed_process = sc.nextInt();
        if (failed_process != 0) isActive[failed_process] = 0;

        new_cord = election_process(isActive, old_cord, initiator);
        System.out.println("Finally, process " + new_cord + " became the new leader");

        for (int i = 1; i <= num_pr; i++) {
            if (isActive[i] == 1) {
                System.out.println("Process " + new_cord + " passes a Coordinator (" + new_cord + ") message to process " + i);
            }
        }
        sc.close();
    }

    public static int election_process(int isActive[], int old_cord, int initiator) {
        System.out.println("The election process is started by " + initiator);
        int index = 0, i = initiator, receiver = (i % num_pr) + 1;
        arr = new int[num_pr + 1];

        while (index < num_pr - 1) {
            if (isActive[i] == 1 && i != receiver) {
                while (isActive[receiver] == 0) receiver = (receiver % num_pr) + 1;
                System.out.println(i + " sends the Election message to " + receiver);
                arr[index] = i;
                print_array(arr, index + 1);
                i = (i % num_pr) + 1;
                receiver = (i % num_pr) + 1;
                index++;
            }
        }

        new_cord = 0;
        for (int j = 0; j < num_pr; j++) {
            if (new_cord < arr[j]) new_cord = arr[j];
        }
        return new_cord;
    }

    public static void print_array(int arr[], int size) {
        System.out.print("[ ");
        for (int i = 0; i < size; i++) {
            if (arr[i] == 0) continue;
            System.out.print(arr[i] + " ");
        }
        System.out.println("]");
    }
}

//sudo apt update
//sudo apt install default-jdk
//javac RingAlgorithm.java
//java RingAlgorithm
