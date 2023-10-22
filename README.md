# PAN Generator #

## Overview ##

The PAN Generator is a Java application built with the Spring
Boot framework. It is designed to generate valid Primary
Account Numbers (PANs) for various card networks like Visa,
MasterCard, American Express, Verve and more. This application can be used for
testing, educational purposes, or any scenario where valid credit card
numbers are required without actual financial transactions.

***Note***: This microservice uses PostgreSQL as the default database for storing generated PANs.

## Features ##

- **RESTful API**: The microservice exposes a RESTful API for generating PANs,
  allowing easy integration with other applications and services.

- **Customization**: Clients can specify the credit card network, issuer
  identification number (IIN), PAN length, and quantity of PANs to generate through API requests.

- **Data Privacy**: The generated PANs do not contain any real customer data
  and are not associated with actual accounts, ensuring data privacy and security.

- **Database Integration**: The microservice stores generated PANs in a
  PostgreSQL database, providing persistence and retrieval capabilities.

## Prerequisites ##

Before running the microservice, ensure you have the following prerequisites installed:

- Java Development Kit (JDK) 11 or later
- Apache Maven
- Spring Boot CLI (optional)
- Docker (optional, for containerization)
- PostgresSQL database

## Getting Started ##

1. Clone this repository to your local machine:

``` git clone https://github.com/DevMeks/pan-generator.git ```

2. Build the application using Maven:

```
cd pan-generator
mvn clean install
```

3. Configure the PostgreSQL database connection by editing the application.properties file.

4. Run the application:

```java -jar target/pan-generator-0.0.1-SNAPSHOT.jar ```
Access the application in your web browser at http://localhost:9993.

## Usage

1. Send HTTP POST requests to the microservice's API endpoint to generate PANs.
   Here's an example using cURL:

```
curl -X POST -H "Content-Type: application/json" -d '{
    "mobileNumber": "080XXXXXXXX",
    "cardScheme": "Verve"
}' http://localhost:9993/api/v1/pan/generate-pan
```

2. The microservice will respond with a JSON object containing the generated
   PAN.

3. Integrate the microservice into your applications or workflows as needed.

### Configuration

You can customize the microservice's configuration, such as port and database settings,
by editing the ***application.properties*** file.

## OpenAPI 3.0 Documentation

You can find the OpenAPI 3.0 documentation for the PAN Generator microservice [here](./pan-generator-openapi3_0.yaml).

The OpenAPI documentation provides detailed information about the API endpoints, request and response structures, and how to interact with the PAN Generator.


### Contributing

Contributions are welcome! If you have any ideas for improvements, bug fixes, or new features, please open an issue or
submit a pull request.

### License

This project is licensed under the MIT License - see
the [LICENSE](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt) file for details.

### Acknowledgments

This application is built using the Spring Boot framework, which simplifies the development of Java-based web
applications.

PAN generation logic is based on industry standards and is intended for non-commercial use only.

