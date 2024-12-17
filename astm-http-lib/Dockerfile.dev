##
# Build Stage
#
FROM maven:3.9.7-eclipse-temurin-21-jammy AS build

RUN --mount=target=/var/lib/apt/lists,type=cache,sharing=locked \
  --mount=target=/var/cache/apt,type=cache,sharing=locked \
  rm -f /etc/apt/apt.conf.d/docker-clean \
  && apt-get -y update \
  && apt-get -y --no-install-recommends install \
  git

##
# Copy Source Code and Library
#
ADD ./pom.xml /build/astm-http-lib/pom.xml
WORKDIR /build/astm-http-lib
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
  mvn dependency:go-offline 
ADD ./src /build/astm-http-lib/src
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
  mvn clean install -DskipTests

##
# "Run" the library by having a dummy entrypoint allowing maven commands to be run for testing/dev purposes
#
ENTRYPOINT ["tail", "-f", "/dev/null"]
