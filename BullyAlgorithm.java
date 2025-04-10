import java.util.Scanner;

public class BullyAlgorithm {
    static int num_pr; // Number of processes
    static int old_cord; // The failed coordinator or leader
    static int new_cord; // The new elected leader
    static int curr_elec; // The current process that is holding the election
    static int[] isActive;
    static int failed_process;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of processes: ");
        num_pr = sc.nextInt();

        isActive = new int[num_pr + 1];

        for (int i = 1; i <= num_pr; i++) {
            isActive[i] = 1;
        }

        old_cord = num_pr; // Assume the highest ID process is the leader

        // Leader has failed
        isActive[old_cord] = 0;

        System.out.println("Enter the process that initiates the election process: ");
        curr_elec = sc.nextInt();

        System.out.println("The process that failed is: " + old_cord + "\n");

        System.out.println("Enter the process that fails (other than the leader process), if none then enter 0: ");
        failed_process = sc.nextInt();

        if (failed_process != 0) {
            isActive[failed_process] = 0;
        }

        // Conduct election
        new_cord = election_process(isActive, old_cord, curr_elec);
        System.out.println("Finally, process " + new_cord + " became the new leader\n");

        // Inform all processes about the new leader
        for (int i = 1; i <= num_pr; i++) {
            if (isActive[i] == 1) {
                System.out.println("Process " + new_cord + " passes a Coordinator (" + new_cord + ") message to process " + i);
            }
        }

        sc.close();
    }

    public static int election_process(int isActive[], int old_cord, int curr_elec) {
        int higher_process = curr_elec;

        for (int i = curr_elec; i <= num_pr; i++) {
            if (isActive[i] == 1) {
                for (int j = i + 1; j <= num_pr; j++) {
                    if (isActive[j] == 1) {
                        System.out.println("Process " + i + " passes Election(" + curr_elec + ") message to process " + j);
                    }
                }

                for (int j = i + 1; j <= num_pr; j++) {
                    if (isActive[j] == 1) {
                        System.out.println("Process " + j + " passes OK(" + j + ") message to process " + i);
                        if (higher_process < j) {
                            higher_process = j;
                        }
                    }
                }
            }
        }
        return higher_process;
    }
}
