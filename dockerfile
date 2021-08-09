FROM openjdk:11
MAINTAINER jukka.com
COPY target/vulnweb-0.0.1-SNAPSHOT.jar vulnweb.jar
ENTRYPOINT ["java","-jar","/vulnweb.jar"]
