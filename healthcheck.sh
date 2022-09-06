#!/bin/sh


if curl --fail --silent -k http://localhost:8080/actuator/health | grep UP 
then 
	exit 0;
else
	exit 1;
fi