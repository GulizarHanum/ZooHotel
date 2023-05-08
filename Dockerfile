FROM openjdk:11
VOLUME /tmp
ADD build/libs/zoo_hotel-0.0.1-SNAPSHOT.jar backend.jar
#ARG JAR_FILE
#COPY ${JAR_FILE} build/libs/zoo_hotel-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","backend.jar"]