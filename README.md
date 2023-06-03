# Chatbot Service

This service provides APIs for chat functionality, allowing users to send messages and start chat sessions with agents. It utilizes an external NLP service for message processing.

## Technologies Used

`-` Java \
`-` Spring Boot \
`-` HttpClient \
`-` JSON \
`-` RESTful API

## Getting Started

### Prerequisites
`.` Java Development Kit (JDK) installed \
`.` Maven build tool installed \
`.` NLP service URL configured in application.properties 

### Installation
`1` Clone the repository:
> git clone git clone https://github.com/cos730/chat-service.git

`2` Build the project using Maven:
> cd chat-service \
mvn clean install
> 

### Configuration

In the application.properties file, configure the NLP service URL:

> nlp-url=http://localhost:8080/nlp-service/analyze

Make sure the NLP service is running at the specified URL.

### Usage
Start the service by running the following command:

>mvn spring-boot:run

The service will be accessible at http://localhost:8080/api.

## API Endpoints

### Send and Receive Messages

#### Endpoint: PUT /api/{id}

This endpoint allows sending a message and receiving a response from the NLP service.

### Start Chat Session

#### Endpoint: POST /api/start-session
This endpoint starts a chat session with an agent.

## Error Handling

`.` If the NLP service is unavailable or encounters an error, a corresponding error message will be returned.\
`.` If no agent is found for the provided agent ID, a NoAgentFoundException will be thrown.\
`.` If there is an issue with the session, a SessionException will be thrown.
