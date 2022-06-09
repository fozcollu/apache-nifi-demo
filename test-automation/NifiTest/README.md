## Setup:
* java8 or higher
* Maven
* Git

## Local Run:
* mvn  clean test -Dkarate.options="--tags @<TAG_NAME>" -Dkarate.env="dev"

## Docker Run:
- docker build -t karatetest .
- docker run -it karatetest  

- docker-compose up --build

## Performance Run:
- mvn clean test-compile gatling:test
  
