#!/bin/bash

# save hostname and lscpu as a variable
hostname=$(hostname -f)
specs=`lscpu`

#usage info
memory_free=$(vmstat --unit M | tail -1 | awk -v col="4" '{print $col}')
cpu_idle=$(vmstat | tail -1 | awk -v col="15" '{print $col}')
cpu_kernel=$(vmstat | tail -1 | awk -v col="14" '{print $col}')
disk_io=$(vmstat --unit M -d | tail -1 | awk -v col="10" '{print $col}')
disk_available=$(df -BM / | tail -1 | awk -v col="4" '{print $col}' | head -c -2 | xargs)

