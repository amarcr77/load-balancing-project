A Load Balancing Model Based on Cloud Partitioning for the Public Cloud

The increasing cloud computing services offer great opportunities for clients to find the maximum service and finest pricing, which however raises new
challenges on how to select the best service out of the huge group. Cloud computing employs a variety of computing resources to facilitate the
execution of large-scale tasks. Therefore, to select appropriate node for executing a task is able to enhance the performance of large-scale cloud
computing environment. Also the numbers of users accessing the cloud are rising day by day. As the cloud is made up of datacenters; which are very
much powerful to handle large numbers of users still then the essentiality of load balancing is vital. Load balancing in the cloud computing environment
has an important impact on the performance. Good load balancing makes cloud computing more efficient and improves user satisfaction. This Project
introduces a better load balance model for the Job Seekers Web Portal based on the cloud partitioning concept in which the jobs are partitioned based on
the arrival date and the Main Controller (Admin) balances the load.


Algorithm 1 Best Partition Searching
begin
while job do
searchBestPartition (job);
if Update(job) then
Send Job to Partition1;
else if EndDate > CurrentDate then
Send Job to Partition2;
else if ArrivalDate<=CurrentDate && EndDate =CurrentDate then
Send Job to Partition3;
else if EndDate<CurrentDate then
Send Job to Partition4;
end if
end while
end

FIRST COME FIRST SERVE ALGORITHM

Main Controller (Admin) maintains an index table of job requests.
The job requests are stored in the table on the basis of their arrival time.
The Main Controller (Admin) scans the index table from top to bottom.
The first job request according to the arrival time is allocated the grant by the Main
Controller (Admin).
The HR receives the response to the request sent and then posts jobs by providing
details about the interview.
In this way all the jobs are processed in the first come first serve basis.

Conclusion

The response time and data transfer cost is a challenge of every engineer to develop
the products that can increase the business performance and high customer
satisfaction in the cloud based sector. The several strategies lack efficient scheduling
and load balancing resource allocation techniques leading to increased operational
cost and give less customer satisfaction. 
