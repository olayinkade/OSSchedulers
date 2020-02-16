import java.io.File;
import java.io.IOException;
import java.util.*;

/******************************************************
 * This class act as a the main class to determine what 
 * type Simulator is used
 *******************************************************/
public class Simulator {
    private static PriorityQueue<Process> queue;
    private static PriorityQueue<Process> queueLength;
    private static PriorityQueue<Process> hierarchy;
    private static PriorityQueue<Process> highQueue;
    private static PriorityQueue<Process> mediumQueue;
    private static PriorityQueue<Process> lowQueue;
    private static String schedulerType;
    private static final int TIMESLICE = 5;

    private static int countOfHighPriority;
    private static int countOfMediumPriority;
    private static int countOfLowPriority;
    private static int countOfIO;
    private static int countOfLong;
    private static int countOfShort;
    private static int countOfMedium;
    private static int totalTimeForLowPriority;
    private static int totalTimeForMediumPriority;
    private static int totalTimeForHighPriority;
    private static int totalTimeForThreadTypeIo;
    private static int totalTimeForThreadTypeShort;
    private static int totalTimeForThreadTypeMedium;
    private static int totalTimeForThreadTypeLong;
    private static int wait = 0;


    private static void initGlobalVars(){
        countOfHighPriority = countOfMediumPriority= countOfLowPriority = 0;
        totalTimeForHighPriority =totalTimeForMediumPriority = totalTimeForLowPriority =0;
        totalTimeForThreadTypeIo = totalTimeForThreadTypeLong = totalTimeForThreadTypeMedium = totalTimeForThreadTypeShort= 0;
        countOfIO = countOfLong = countOfMedium = countOfShort = 0;
    }

    private static void initMainQueue(){
        initGlobalVars();
        queue = new PriorityQueue<>(new StartTimeComparator());

    }

    private static void initPriorityRR(){
        hierarchy = new PriorityQueue<>(new PriorityComparator());
        queue = new PriorityQueue<>(new StartTimeComparator());
    }

    private static void initMLFQ(){
        highQueue = new PriorityQueue<>(new StartTimeComparator());
        mediumQueue = new PriorityQueue<>(new StartTimeComparator());
        lowQueue = new PriorityQueue<>(new StartTimeComparator());
    }

    private static void initShort(){
        queue = new PriorityQueue<>(new StartTimeComparator());
        queueLength = new PriorityQueue<>(new StartTimeComparator());
    }

    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        schedulerType = in.nextLine();
        initMainQueue();

        in.close();
        switch (schedulerType) {
            case "1":
                System.out.println("Pure Round Robin Scheduler.");
                loadProcesses();
                scheduler(queue);
                break;
            case "2":
                System.out.println("Priority Round Robin Scheduler.");
                initPriorityRR();
                loadProcesses();
                PriorityRoundRobin();
                break;
            case "3":
                System.out.println("Using Shortest Job First Scheduler.");
                initShort();
                loadProcesses();
                shortScheduler();
                break;
            case "4":
                System.out.println("Using Shortest Time To Completion Scheduler.");
                initShort();
                loadProcesses();
                shortScheduler();
                break;
            case "5":
                System.out.println("Using Multilevel Feedback Queue Scheduler.");
                initMLFQ();
                loadProcesses();
                scheduler(highQueue);
                scheduler(mediumQueue);
                scheduler(lowQueue);
                break;
            default:
                System.out.println("Valid arguments are from -> 1.....4, only.");
                break;
        }
        printUsingPriority();
        printUsingThreadType();
    }

    private static void PriorityRoundRobin() {
        int currTimeLine = queue.peek().getStartTime();
        int timeSpent;
        while (!queue.isEmpty() || !hierarchy.isEmpty()) {
            if (!queue.isEmpty()) {
                Process curr = queue.remove();
                hierarchy.add(curr);
                Iterator<Process> it = queue.iterator();
                Process current;
                Queue<Process> temp =  new LinkedList<>();
                while (it.hasNext() ) {
                    current = it.next();
                    if(current.getStartTime() > currTimeLine){
                        break;
                    }
                    hierarchy.add(current);
                    temp.add(current);
                }
                while (!temp.isEmpty()){
                    Process delete = temp.remove();
                    queue.remove(delete);
                }
            }
            Process prev = hierarchy.remove();

            boolean performIo = isProcessIo(prev.getOdds_of_IO());
            if(performIo){
                timeSpent = generateRandomTimeSlice(TIMESLICE);
                if (prev.getThread_Length() < timeSpent) {
                    wait += prev.getThread_Length();
                } else {
                    wait += timeSpent;
                }
            }
            else{
                timeSpent = TIMESLICE;
                if (prev.getThread_Length() < TIMESLICE) {
                    wait += prev.getThread_Length();
                } else {
                    wait += TIMESLICE;
                }
            }
            if (prev.getThread_Length() <= timeSpent ) {
                update(prev,wait);
                currTimeLine += timeSpent;
            } else {
                prev.setThread_Length(prev.getThread_Length() - timeSpent);
                prev.setStartTime(prev.getStartTime() + timeSpent);
                currTimeLine += timeSpent;
                hierarchy.add(prev);
            }
        }
    }

    private static void shortScheduler(){
        int timeSpent;
        int currTimeLine = queue.peek().getStartTime();
        while(!queue.isEmpty() || !queueLength.isEmpty()){
            if(!queue.isEmpty()){
                Process curr = queue.remove();
                queueLength.add(curr);
                Iterator<Process> it = queue.iterator();
                Process current;
                Queue<Process> temp =  new LinkedList<>();
                while (it.hasNext()) {
                    current = it.next();
                    if(current.getStartTime() > currTimeLine){
                        break;
                    }
                    queueLength.add(current);
                    temp.add(current);
                }
                while (!temp.isEmpty()){
                    Process delete = temp.remove();
                    queue.remove(delete);
                }
            }Process prev = queueLength.remove();
            if(schedulerType.equals("3")) {

                currTimeLine += prev.getThread_Length();
                update(prev, prev.getThread_Length());
            }
            else if(schedulerType.equals("4")){
                timeSpent = TIMESLICE;
                if (prev.getThread_Length() < TIMESLICE) {
                    wait += prev.getThread_Length();
                } else {
                    wait += TIMESLICE;
                }
                if (prev.getThread_Length() <= timeSpent ) {
                    update(prev,wait);
                } else {
                    prev.setThread_Length(prev.getThread_Length() - timeSpent);
                    prev.setStartTime(prev.getStartTime() + timeSpent);
                    queueLength.add(prev);
                }
            }
        }

    }

    private static void scheduler(PriorityQueue<Process> queue){
        boolean performIo;
        while(!queue.isEmpty()) {
            Process curr = queue.remove();
            performIo = isProcessIo(curr.getOdds_of_IO());
                schedulerHelperLogic(curr, performIo, queue);

        }
    }

    private static void schedulerHelperLogic(Process curr, boolean performIo,PriorityQueue<Process> queue){

        int timeSpent;

        if(performIo){
            timeSpent = generateRandomTimeSlice(TIMESLICE);
            if (curr.getThread_Length() < timeSpent) {
                wait += curr.getThread_Length();
            } else {
                wait += timeSpent;
            }
        }
        else{
            timeSpent = TIMESLICE;
            if (curr.getThread_Length() < TIMESLICE) {
                wait += curr.getThread_Length();
            } else {
                wait += TIMESLICE;
            }
        }
        if (curr.getThread_Length() <= timeSpent ) {
           update(curr,wait);
        } else {
            curr.setThread_Length(curr.getThread_Length() - timeSpent);
            curr.setStartTime(curr.getStartTime() + timeSpent);
            queue.add(curr);
        }
    }

    private static void update(Process curr, int wait){
        switch (curr.getPriority()){
            case 0:
                countOfHighPriority++;
                totalTimeForHighPriority += wait;
                break;
            case 1:
                countOfMediumPriority++;
                totalTimeForMediumPriority += wait;
                break;
            case 2:
                countOfLowPriority++;
                totalTimeForLowPriority += wait;
                break;
        }
        switch (curr.getThread_Type()){
            case 0:
                countOfShort++;
                totalTimeForThreadTypeShort += wait;
                break;
            case 1:
                countOfMedium++;
                totalTimeForThreadTypeMedium+= wait;
                break;
            case 2:
                countOfLong++;
                totalTimeForThreadTypeLong += wait;
                break;
            case 3:
                countOfIO++;
                totalTimeForThreadTypeIo+= wait;
                break;
        }
    }

    /***********************************************
     * This function would go through the text file
     * and store the processes in respective queues
     ***********************************************/

    private static void loadProcesses(){
        try {
            Scanner in = new Scanner(new File("processes.txt"));
            while (in.hasNext()){
                Process newProcess = createProcess(in.nextLine());
                queue.add(newProcess);
                if(schedulerType.equals("5")){
                    if(newProcess.getPriority() == 0){
                        highQueue.add(newProcess);
                    }
                    else if (newProcess.getPriority() == 1){
                        mediumQueue.add(newProcess);
                    }
                    else if(newProcess.getPriority() == 2){
                        lowQueue.add(newProcess);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Process createProcess(String line){
      String [] divided = line.split( " ");
      return new Process(divided[0],
              Integer.parseInt(divided[1]),
              Integer.parseInt(divided[2]),
              Integer.parseInt(divided[3]),
              Integer.parseInt(divided[4]),
              Integer.parseInt(divided[5]));
    }

    private static boolean isProcessIo(int odds) {
        double doubleOdds = (double)odds/100;
        double rand = Math.random();
        return rand > doubleOdds;
    }

    private static int generateRandomTimeSlice(int max) {
        Random rn = new Random();
        int range = max + 1;
        return rn.nextInt(range) ;
    }

    private static void printUsingPriority() {
        System.out.println("Average run time per priority:");
        System.out.println("Priority 0 average run time: " +  totalTimeForHighPriority/countOfHighPriority);
        System.out.println("Priority 1 average run time: " +  totalTimeForMediumPriority/countOfMediumPriority);
        System.out.println("Priority 2 average run time: " +  totalTimeForLowPriority/countOfLowPriority);
    }

    private static void printUsingThreadType() {
        System.out.println("Average run time per type:");
        System.out.println("Type 0 average run time: " + totalTimeForThreadTypeShort/countOfShort);
        System.out.println("Type 1 average run time: " + totalTimeForThreadTypeMedium/countOfMedium);
        System.out.println("Type 2 average run time: " + totalTimeForThreadTypeLong/countOfLong);
        System.out.println("Type 3 average run time: " + totalTimeForThreadTypeIo/countOfIO);
    }

}

