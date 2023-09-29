# Credit Card PAN Generator #
## Overview ##
The Credit Card PAN Generator is a Java application built with the Spring 
Boot framework. It is designed to generate valid credit card Primary 
Account Numbers (PANs) for various credit card networks like Visa, 
MasterCard, American Express, and more. This application can be used for 
testing, educational purposes, or any scenario where valid credit card 
numbers are required without actual financial transactions.

## Features ##
- **Generate Valid PANs**: The application can generate PANs adhering to the structure and rules of major credit card networks, ensuring they pass basic validation checks.

- **Customization**: Users can specify the credit card network, prefix, and other parameters to generate PANs tailored to their needs.

- **Data Privacy**: The generated PANs do not contain any actual customer data and are not associated with real accounts, ensuring data privacy and security.

## Prerequisites ##
Before running the application, ensure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or later
- Apache Maven
- Spring Boot CLI (optional)

## Getting Started ##
Clone this repository to your local machine:

``` git clone https://github.com/yourusername/credit-card-pan-generator.git ```

Build the application using Maven:
```
cd credit-card-pan-generator
mvn clean install
```

Run the application:


```java -jar target/credit-card-pan-generator.jar ```
Access the application in your web browser at http://localhost:8080.

## Usage
Open the web interface provided by the application.

Customize the PAN generation options such as credit card network, prefix, and quantity.

Click the "Generate PANs" button to generate PANs based on your settings.

Copy and use the generated PANs as needed for your testing or educational purposes.

### Contributing
Contributions are welcome! If you have any ideas for improvements, bug fixes, or new features, please open an issue or submit a pull request.

### License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt) file for details.

### Acknowledgments
This application is built using the Spring Boot framework, which simplifies the development of Java-based web applications.

Credit card PAN generation logic is based on industry standards and is intended for non-commercial use only.

