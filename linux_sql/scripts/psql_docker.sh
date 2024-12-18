#!/bin/bash

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Start docker if not already running
sudo systemctl status docker > /dev/null || sudo systemctl start docker

docker container inspect jrvs-psql > /dev/null
container_status=$?

# User switch case to handle create|stop|start operations
case $cmd in
  create)
  # Check if container is alraedy created
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1
  fi

  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_USER="$db_username" -e POSTGRES_PASSWORD=$db_password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
  ;;

  start|stop)
  # Check instance status
  if [ $container_status -ne 0 ]; then
    echo 'Container does not exist'
    exit 1
  fi
  # Start or stop the container
  docker container $cmd jrvs-psql
  exit $?
  ;;

  *)
  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac



