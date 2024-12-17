Table of contents
* [Introduction](#introduction)
* [Hadoop Cluster](#hadoop-cluster)
* [Hive Project](#hive-project)
* [Improvements](#improvements)

# Introduction
This project utlizes the Core Hadoop ecosystem to perform data analysis as well as evaluate various optimization techniques to improve performance. 
The Hadoop cluster was provisioned using Google Cloud Platform's Dataproc service. 
The Core Hadoop ecosystem worked with was HDFS, YARN, MapReduce, along with Apache Hive for writing queries and managing tables. 
Zeppelin notebook was the primary interface used to work with the Hadoop cluster and present the results in a user friendly manner. 


# Hadoop Cluster
```
Master Node
- machine type:     n2-standard-4
    - # vCPU:       4
    - # core:       2
    - memory:       16 GB
    - disk size:    100 GB   

Worker Nodes
- number of nodes:  2
- machine type:     n2-standard-2
    - # vCPU:       2
    - # core:       1
    - memory:       8 GB
    - disk size:    100 GB   

YARN
- YARN cores:       4
- YARN memory:      12.8 GB
```

# Hive Project
![](./notebook.png)

# Improvements
- Look at performance differences between managed and external tables in more depth
- Scale up by adding more vertical nodes to see how performance scales 
- Try partitioning by country to see how it affects performance for queries 