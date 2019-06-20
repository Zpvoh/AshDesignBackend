FROM java
VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
COPY ./ ./
ENTRYPOINT ["java","-jar","target/design-0.0.1-SNAPSHOT.jar"]
