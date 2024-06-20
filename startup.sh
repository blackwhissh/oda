#!/bin/bash
cd /home/ec2-user
aws s3 cp s3://oda-dev-bucket/oda-0.0.1-SNAPSHOT.jar
java -jar demo-0.0.1-SNAPSHOT.jar
