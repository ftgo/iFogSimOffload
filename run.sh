#!/bin/bash

for i in {1..3}
do
	# echo "Hello $i!"
	java -jar target/ifogsim-jar-with-dependencies.jar
	./bk.sh
	./rm.sh
done

shutdown -hP now
