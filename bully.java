import java.util.Scanner;

public class BullyAlgorithm {
    static int num_pr, oldCoord, newCoord, initiator, failed_process;
    static boolean[] isActive;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        num_pr = sc.nextInt();
        isActive = new boolean[num_pr + 1]; // index 1 to num_pr

        // Initialize all processes as active
        for (int i = 1; i <= num_pr; i++) {
            isActive[i] = true;
        }

        // Coordinator initially assumed as highest ID
        oldCoord = num_pr;
        isActive[oldCoord] = false;  // Coordinator has failed
        System.out.println("The process that failed is: " + oldCoord);

        System.out.print("\nEnter the process that initiates the election process: ");
        initiator = sc.nextInt();

        System.out.print("Enter the process that fails (other than the leader process), if none then enter 0: ");
        failed_process = sc.nextInt();
        if (failed_process != 0) {
            isActive[failed_process] = false;
        }

        newCoord = runElection(initiator);
        System.out.println("Finally, process " + newCoord + " became the new leader\n");

        // Send coordinator message to all active processes
        for (int i = 1; i <= num_pr; i++) {
            if (isActive[i] && i != newCoord) {
                System.out.println("Process " + newCoord + " passes a Coordinator (" + newCoord + ") message to process " + i);
            }
        }

        sc.close();
    }

    public static int runElection(int initiator) {
        int maxId = initiator;

        // Send election messages
        for (int i = initiator; i <= num_pr; i++) {
            if (isActive[i]) {
                for (int j = i + 1; j <= num_pr; j++) {
                    if (isActive[j]) {
                        System.out.println("Process " + i + " passes Election(" + initiator + ") message to process " + j);
                    }
                }

                // Receive OK messages
                for (int j = i + 1; j <= num_pr; j++) {
                    if (isActive[j]) {
                        System.out.println("Process " + j + " passes OK(" + j + ") message to process " + i);
                        if (j > maxId) {
                            maxId = j;
                        }
                    }
                }
            }
        }

        return maxId;
    }
}
