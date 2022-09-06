#!/bin/sh

exec java ${JAVA_OPTS} -Dspring.profiles.active=docker -jar /app/astm-http-bridge.jar #--spring.config.location=file:./custom-config/