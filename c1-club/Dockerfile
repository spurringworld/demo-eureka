FROM java:8
MAINTAINER c1-club

RUN useradd -d /home/java -u 2022 -m java
COPY --chown=java *.jar /home/java/
WORKDIR /home/java/
USER java
ENTRYPOINT ["java","-jar","c1-club-1.0.jar"]
