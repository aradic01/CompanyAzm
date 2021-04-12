FROM adoptopenjdk:11-jre-hotspot
COPY ./target/springbootdemo-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch springbootdemo-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","springbootdemo-0.0.1-SNAPSHOT.jar"]