FROM java:8
MAINTAINER f-teacher

RUN useradd -d /home/java -u 2022 -m java
COPY --chown=java *.jar /home/java/
WORKDIR /home/java/
USER java
ENTRYPOINT ["java","-jar","f-teacher-1.0.jar"]
