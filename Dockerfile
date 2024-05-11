FROM openjdk:17-oracle
RUN mkdir data-shared
COPY target/ms-eportafolio.jar ms-eportafolio.jar
EXPOSE 8025
ENTRYPOINT ["java","-jar","/ms-eportafolio.jar"]