FROM maven:3.6.3-jdk-11

WORKDIR /usr/src/app

COPY /test-automation/NifiTest/pom.xml /usr/src/app
COPY /test-automation/NifiTest/src/test/java /usr/src/app/src/test/java

CMD mvn test -Dkarate.options="--tags @nifi1"