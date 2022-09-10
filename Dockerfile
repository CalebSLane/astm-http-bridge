##
# Build Stage
#
FROM maven:3-jdk-11 as build

RUN apt-get -y update
RUN apt-get -y install git

##
# Copy Source Code
#
ADD .git /build/.git
ADD .gitmodules /build/.gitmodules
WORKDIR /build/
RUN git submodule update --init --recursive

##
# Build Dependencies
#
WORKDIR /build/astm-http-lib
RUN mvn dependency:go-offline
RUN mvn clean install -DskipTests

##
# Build Project
#
WORKDIR /build
ADD ./pom.xml /build/pom.xml
RUN mvn dependency:go-offline
ADD ./src /build/src
RUN mvn clean package -DskipTests

##
# Run Stage
#
FROM alpine:3.16

RUN  apk update \
  && apk upgrade \
  && apk add --update openjdk11 curl bash \
  && rm -rf /var/cache/apk/*
  
RUN addgroup -S astm --gid 9257 && adduser -S astm -s /bin/bash -u 9257 -G astm
RUN mkdir /app
RUN chown astm:astm /app 


#Deploy the war into tomcat image and point root to it
COPY --from=build /build/target/*.jar /app/astm-http-bridge.jar
    
ADD healthcheck.sh /app/healthcheck.sh
ADD docker-entrypoint.sh /app/docker-entrypoint.sh
RUN chown astm:astm /app/docker-entrypoint.sh; \
    chmod 770 /app/docker-entrypoint.sh;  \
    chown astm:astm /app/ASTM.jar; \
    chmod 770 /app/ASTM.jar; \
    chown astm:astm /app/healthcheck.sh; \
    chmod 770 /app/healthcheck.sh; 
    
USER astm:astm

ENTRYPOINT [ "/app/docker-entrypoint.sh" ]

