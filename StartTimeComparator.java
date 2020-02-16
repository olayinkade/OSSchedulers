import java.util.Comparator;

public class StartTimeComparator implements Comparator<Process>{
    @Override
    public int compare(Process o1, Process o2) {
        if(o1.getStartTime() > o2.getStartTime())
            return 1;
        else
            return -1;
    }
}

class ThreadLengthComparator implements Comparator<Process>{
    @Override
    public int compare(Process o1, Process o2) {
        if(o1.getThread_Length() > o2.getThread_Length())
            return 1;
        else
            return -1;
    }
}
class PriorityComparator implements Comparator<Process>{
    @Override
    public int compare(Process o1, Process o2) {
        if(o1.getThread_Length() > o2.getThread_Length())
            return 1;
        else
            return -1;
    }
}
