#!/bin/bash

# save hostname and lscpu as a variable
hostname=$(hostname -f)
specs=`lscpu`

# hardware info
hostname=$hostname
cpu_number=$(echo "$specs" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$specs" | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$specs" | egrep "Model:" | awk '{print $2}' | xargs)
cpu_mhz=$(echo "$specs" | egrep "Model name:" | awk '{print $NF}' | xargs)
l2_cache=$(echo "$specs" | egrep "L2 cache:" | awk '{print $3,$4}' | xargs)
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}')
