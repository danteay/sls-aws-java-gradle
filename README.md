# sls-aws-java-gradle

A serverless Java Scaffolding using gradle

## Requirements

* Java 11
* Make command
* NodeJS >= 12
* Serverless CLI

## Project commands

### Installation

This will install a project instance of the serverless cli and the configured serverless
plugins on the serverless.yml file

```console
make install
```

### Project test

This will run the Gradle test task from the local Gradle wrapper

```console
make test
```

### Project build

This will execute the Gradle clean and build task from the local Gradle wrapper

```console
make build
```

### Local execution

This will simulate a local APIGateway to test the endpoints

```console
make run
```