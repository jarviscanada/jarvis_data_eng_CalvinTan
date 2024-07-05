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

#usage info
timestamp=$(date +"%Y-%m-%d %H:%M:%S")
memory_free=$(vmstat --unit M | tail -1 | awk -v col="4" '{print $col}')
cpu_idle=$(vmstat | tail -1 | awk -v col="15" '{print $col}')
cpu_kernel=$(vmstat | tail -1 | awk -v col="14" '{print $col}')
disk_io=$(vmstat --unit M -d | tail -1 | awk -v col="10" '{print $col}')
disk_available=$(df -BM / | tail -1 | awk -v col="4" '{print $col}' | head -c -2 | xargs)

export PGPASSWORD=$psql_password

test_stmt="(SELECT id FROM host_info WHERE hostname='$hostname')"
host_id=$(psql -t -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$test_stmt" | xargs)

insert_stmt="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
  VALUES('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available);"

#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?