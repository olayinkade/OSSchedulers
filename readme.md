How to compile java program

Use javac Simulator.java StartTimeComparator.java Process.java <br>
java 1 to 5
1 -  Pure round-robin
2 - Priority round-robin
3 - Shortest job first
4 - Shortest time to completion first
5 - Multi-level feedback queue



Comparing Pure Round-Robin and Priority round-robin:
from the statistics, these two schedulers vary from themselves.
For the Pure Round-Robin,
it doesn't use the priority, we just run each process for a specific time slice.
The reason for a significant difference is that in pure Round-Robin, we run
each process for a fixed time slice, and the process will stop when it finishes
its time slice we place it back deducting the value of the time slice from it
and increasing the start time
in this case the waiting time goes up drastically.

For the  Priority Round-Robin,  the priority is being assigned to each process,
and going to treat each processes with higher priority.
1. it will longer running time for the low priority process.
2.  shorter running time for the high priority process.
