FROM openjdk:8

EXPOSE 3015
ADD target/docker-comment.jar docker-comment.jar
ENTRYPOINT ["java","-jar","/docker-comment.jar"]
