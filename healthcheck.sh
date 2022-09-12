#!/bin/sh

if curl --fail --silent -k https://localhost:8443/actuator/health | grep UP 
then 
	exit 0;
else
	if curl --fail --silent http://localhost:8443/actuator/health | grep UP 
	then 
		exit 0;
	else
		exit 1;
	fi
fi
