#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

# save hostname and lscpu as a variable
hostname=$(hostname -f)
specs=`lscpu`

# hardware info
id=1
hostname=$hostname
cpu_number=$(echo "$specs" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$specs" | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$specs" | egrep "Model name:" | awk '{$1=$2=""; print $0}' | xargs)
cpu_ghz=$(echo "$specs" | egrep "Model name:" | awk '{print $NF}' | head -c -4 | xargs)
cpu_mhz=$(echo $cpu_ghz | awk '{ printf("%.3f\n", $0*1000 ) }' | xargs)
l2_cache=$(echo "$specs" | egrep "L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(awk '/MemTotal/ {print $2}' /proc/meminfo | xargs)
timestamp=$(date +"%Y-%m-%d %H:%M:%S")

insert_stmt="INSERT INTO host_info(id, hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, timestamp, total_mem)
  VALUES($id, '$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, '$timestamp', $total_mem);"

export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?