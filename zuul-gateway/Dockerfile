FROM java:8
MAINTAINER zuul-gateway

RUN useradd -d /home/java -u 2022 -m java
COPY --chown=java *.jar /home/java/
WORKDIR /home/java/
USER java
ENTRYPOINT ["java","-jar","zuul-gateway-1.0.jar"]
