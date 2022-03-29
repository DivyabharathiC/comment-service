FROM openjdk:8

ADD target/docker-comment.jar docker-comment.jar

EXPOSE 3015

ENTRYPOINT ["java","-jar","docker-comment.jar"]