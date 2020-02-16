public class Process {
    private int priority;
    private String thread_Name;
    private int thread_Length;
    private int thread_Type;
    private int startTime;
    private int odds_of_IO;

    public Process( String tName,int tType,int priority, int tLength, int startTime, int odds ){
        this.priority = priority;
        thread_Name = tName;
        thread_Length = tLength;
        thread_Type = tType;
        this.startTime = startTime;
        odds_of_IO = odds;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getThread_Length() {
        return thread_Length;
    }

    public int getThread_Type() {
        return thread_Type;
    }

    public String getThread_Name() {
        return thread_Name;
    }
    public int getOdds_of_IO() {
        return odds_of_IO;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setOdds_of_IO(int odds_of_IO) {
        this.odds_of_IO = odds_of_IO;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setThread_Length(int thread_Length) {
        this.thread_Length = thread_Length;
    }

    public void setThread_Name(String thread_Name) {
        this.thread_Name = thread_Name;
    }

    public void setThread_Type(int thread_Type) {
        this.thread_Type = thread_Type;
    }
}
