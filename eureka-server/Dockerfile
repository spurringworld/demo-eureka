FROM java:8
MAINTAINER eureka-server

RUN useradd -d /home/java -u 2022 -m java
COPY --chown=java *.jar /home/java/
WORKDIR /home/java/
USER java
ENTRYPOINT ["java","-jar","eureka-server-1.0.jar"]
