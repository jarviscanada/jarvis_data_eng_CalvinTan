#!/bin/bash

# save hostname and lscpu as a variable
hostname=$(hostname -f)
specs=`lscpu`

# hardware info
hostname=$hostname
cpu_number=$(echo "$specs" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$specs" | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$specs" | egrep "Model name:" | awk '{$1=$2=""; print $0}' | xargs)
cpu_ghz=$(echo "$specs" | egrep "Model name:" | awk '{print $NF}' | head -c -4 | xargs)
cpu_mhz=$(echo $cpu_ghz | awk '{ printf("%.3f\n", $0*1000 ) }' | xargs)
L2_cache=$(echo "$specs" | egrep "L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(awk '/MemTotal/ {print $2}' /proc/meminfo | xargs)
timestamp=$(date +"%Y-%m-%d %H:%M:%S")