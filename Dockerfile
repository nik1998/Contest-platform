FROM maven:3.8-jdk-11 AS MAVEN_TOOL_CHAIN
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN sed -i -e 's/localhost/mypostgres/g' /usr/src/app/src/main/resources/application.properties
RUN mvn -f /usr/src/app/pom.xml clean install war:exploded -DskipTests

FROM tomcat:jre11-slim
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=MAVEN_TOOL_CHAIN /usr/src/app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]
