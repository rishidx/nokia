# Project Title
Event generator service using microservice architecture

# Project Description

<u><strong>
Event Generator
</u></strong>

This microservice is responsible to generate events. Events can be generated in two ways. 

1) REST service '/punch' is exposed to post punch data

2) Sample events are generated at regular interval of 30 seconds with predefined data

Events are sent to kafka brokers. Its address and topics name has to be updated in application.properties file


<u><strong>
Event Consumer
</u></strong>

This microservice is responsible to consume events. It listen to specified topics of kafka broker, process it and then save it to embedded H2 database

To access events, REST service '/view-all' can be use to view all events  

Events are consumed from kafka brokers' topics. Its address and name has to be updated in application.properties file


# Getting started
Dockerfile is also there to create docker image. if docker is running on same machine as the code then simply run: 
	mvnw spring-boot:build-image
 
navigate to kubernetes-files, kubernetes deployment file can be use to deploy microservices in kubernetes
Run command: 
	kubectl create -f *-deployment.yml

<u><strong> Metrics </u></strong> <br/>
Actuators can be use to get metrics information. Access '/actuator/info' to get node info, '/actuator/metrics' to get metrics. 

Prometheus micrometer dependency cam also be added to get all metrics from actuator 

<u><strong>Swagger documentation </u></strong><br/>
Swagger documentation can be accessed via '/swagger-ui/'