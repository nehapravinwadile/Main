
import java.util.Scanner;

public class RingAlgorithm {
    static int numPr, oldCoord, newCoord, initiator, failedProcess;
    static boolean[] isActive;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        numPr = sc.nextInt();
        isActive = new boolean[numPr];

        // Initially all active
        for (int i = 0; i < numPr; i++) 
        isActive[i] = true;

        oldCoord = numPr - 1;  // highest ID (0-based)
        isActive[oldCoord] = false;
        System.out.println("Coordinator  " + (oldCoord + 1) + " has failed.");

        System.out.print("Enter initiator process: ");
        initiator = sc.nextInt() - 1;

        System.out.print("Enter another failed process (or 0 if none): ");
        failedProcess = sc.nextInt();
        if (failedProcess != 0) 
            isActive[failedProcess - 1] = false;

         newCoord = runElection(initiator);
         System.out.println("New leader is process " + (newCoord + 1));

        for (int i = 0; i < numPr; i++) {
            if (isActive[i]) {
                System.out.println("Coordinator (" + (newCoord + 1) + ") informs process " + (i + 1));
            }
        }
        sc.close();
    }

    public static int runElection(int initiator) {
        System.out.println("Election started by process " + (initiator + 1));
        int maxId = initiator;
        int current = (initiator + 1) % numPr;              //next node of starter

        while (current != initiator) {
            if (isActive[current]) {
                System.out.println("Process " + (current + 1) + " is active in election.");
                if (current > maxId)          //finds highest ID like 4 then,5,6...
                    maxId = current;     
            }
            current = (current + 1) % numPr;     //move to next process
        }

        return maxId;
    }
}

