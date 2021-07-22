# Spring Boot Template for Authentication

- This template is intended to provide a simple authentication
- Kafka is included in this template
- **This template uses annotation based routing**

# Technologies Used:

- Spring Actuator
- Spring Data JPA
- Spring Web
- Spring Security Crypto
- Spring Validation
- Spring Kafka
- Spring Cloud Stream
- Spring Cloud Stream Binder Kafka Streams
- Spring Configuration Processor
- Spring Devtools
- Kafka Streams
- PostgreSQL
- Liquibase
- Lombok
- Swagger
- ModelMapper
- Docker
- AOP
- Avro
- JUnit
- Nimbus

# To Generate a public/private key pair

Private Key (RSA):<br>
``ssh-keygen -t rsa -b 4096 -m PEM -f jwtRS256.key``

Public Key:<br>
``openssl rsa -in jwtRS256.key -pubout -outform PEM -out jwtRS256.key.pub``

Private Key (PKCS8):<br>
``openssl pkcs8 -topk8 -nocrypt -in jwtRS256.key > private_key.pem``