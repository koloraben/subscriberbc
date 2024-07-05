
```markdown
# Subscriber business component

## Overview

Crud Operation on subscriber at chouchou

## Project Structure

the project use hexagonal architecture

```plaintext
project-root/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── chouchou/
│   │   │   │   │   ├── subscriberbc/
│   │   │   │   │   │   ├── domain/
│   │   │   │   │   │   │   ├── business/
│   │   │   │   │   │   │   │   ├── ...
│   │   │   │   │   │   │   ├── model/
│   │   │   │   │   │   │   │   ├── ...
│   │   │   │   │   │   ├── application/
│   │   │   │   │   │   │   ├── ports/
│   │   │   │   │   │   │   │   ├── input/
│   │   │   │   │   │   │   │   │   ├── crud/
│   │   │   │   │   │   │   │   │   │   ├── ...
│   │   │   │   │   │   │   │   │   ├── search/
│   │   │   │   │   │   │   │   │   │   ├── ...
│   │   │   │   │   │   │   ├── output/
│   │   │   │   │   │   │   │   ├── ...
│   │   │   │   │   │   ├── infrastructure
│   │   │   │   │   │   │   ├── adapters/
│   │   │   │   │   │   │   │   │   ├── config/
│   │   │   │   │   │   │   │   │   │   ├── ...
│   │   │   │   │   │   │   │   │   ├── input/
│   │   │   │   │   │   │   │   │   │   ├── cli
│   │   │   │   │   │   │   │   │   │   ├── rest
│   │   │   │   │   │   │   │   │   │   ├── soap
│   │   │   │   │   │   │   │   │   ├── output/
│   │   │   │   │   │   │   │   │   │   ├── persistence
│   │   │   │   │   │   │   │   │   │   ├   │   │── jpa
│   │   │   │   │   │   │   │   │   │   ├   │   │── memory
│   │   │   ├── resources/
│   │   │   │   ├── application.yml
│   │   │   │   ├── ...
│   ├── test/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── chouchou/subscriberbc

│   │   │   │   │  │   │   │   │   ├── domain
│   │   │   │   │  │   │   │   │   ├── integrationtest
│   ├── target/
│   ├── docker
│   │   ├── Dockerfil
│   │   ├── docker-compose.yml
│   ├── README.md
```

## Frameworks and Technologies Used

- Spring 
- memory / jpa
- junit with mockito
- cucumber

## Build and Run

### Prerequisites

- JAVA_HOME to be set to java 17

### Build


```bash
./mvnw clean install
```

### Run

```bash
docker-compose -f docker\docker-compose.yml up
```

This assumes you have Docker installed.

## API Documentation

after running the app go to :

```bash
http://localhost:8080/swagger-ui/index.html
```
you will find swagger interface where you can test api
