How to compile java program

Use javac Simulator.java StartTimeComparator.java Process.java <br>
java 1 to 5
1 -  Pure round-robin
2 - Priority round-robin
3 - Shortest job first
4 - Shortest time to completion first
5 - Multi-level feedback queue

Pure RR												
Attempt	1	2	3	4	5	6	7	8	9	10	Average	Std. Dev.
Priority 0	14164	14159	14155	14166	14161	14162	14166	14159	14169	14166	14162.7	4.270050741
Priority 1	11599	11605	11586	11604	11599	11607	11603	11605	11603	11597	11600.8	6.08824003
Priority 2	11435	11444	11442	11444	11444	11444	11439	11444	11442	11443	11442.1	2.960855732
Type     0	 4500	 4504	 4496	4494	4504	4507	4497	4504	4507	4502	4501.5	4.576510073
Type     1	 7430	 7431	 7418	7434	7431	7434	7441	7431	7435	7431	7431.6	5.777350412
Type     2	17667	17670	17668	17668	17677	17667	17673	17670	17670	17675	17670.5	3.43996124
Type     3	17246	17253	17239	17256	17248	17255	17248	17253	17252	17253	17250.3	5.121848625

Priority Round robin												
Attempt	1	2	3	4	5	6	7	8	9	10	Average	Std. Dev.
Priority 0	7280	7275	7280	7276	7280	7276	7280	7270	7280	7276	7277.3	3.334999584
Priority 1	6074	6075	6074	6071	6064	6074	6064	6074	6074	6071	6071.5	4.169998668
Priority 2	4769	4770	4769	4770	4775	4769	4769	4769	4769	4769	4769.8	1.87379591
Type     0	 149	 152	 149	 141	 189	 149	 145	 149	 149	 139	 151.1	13.92400006
Type     1	 749	 747	 749	 750	 729	 749	 749	 747	 749	 749	 746.7	6.290204024
Type     2	11188	11188	11188	11188	11188	11188	11178	11188	11188	11188	11187  	3.16227766
Type     3	10259	10258	10259	10258	10259	10259	10259	10253	10259	10259	10258.2	1.87379591


Analysis:
when using Queues for each scheduler, Round Robin using a Priority queue, each
threads is assigned a fixed time slice and  it acts as first come first serve.
sorting by arrival time
The Priority Round Robin using a Priority Queue, the order for each threads be
served is according by its priority and arrival time.

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


The z-test tells us that
for when the priority 0
It is concluded that the null hypothesis Ho is rejected. Therefore, there is
enough evidence to claim that the population mean \muμ is less than 14162.7, at
the 0.5 significance level.
