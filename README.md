# GlobalOrdersDemo
A demo app written in Spring Boot useful for demonstrating Pivotal Cloud Foundry

Loosely based on this app: https://github.com/Pivotal-Field-Engineering/PCF-demo

Build each of the apps and push them to Cloud Foundry

mvn clean install
cf push <app> -b java_buildpack -m 1G --random-route -p target/<app>.jar

If you navigate your browser to the orders-demo-api app, you will see a user interface
presenting a map of the world and some information about the other microservices.

To start with, nothing will work! Time for some configuration.

1. Obtain the hostname of the monitor and generator apps
2. Provide the names to the api app by setting environment variables
  cf set-env orders-demo-api monitorHost <monitorHostname>
  cf set-env orders-demo-api generatorHost <generatorHostname>
3. Restart the api app
  cf restage orders-demo-api
  
It still doesn't work!

Now you'll need to create a RabbitMQ instance and bind it to the generator and monitor apps.

In PWS (https://run.pivotal.io) you might do the following

1. cf create-service cloudamqp lemur ordermq
2. cf bind-service orders-demo-monitor ordermq
3. cf bind-service orders-demo-generator ordermq
4. cf restage orders-demo-generator
5. cf restage orders-demo-monitor

Now the UI should should the name of the RabbitMQ host for each microservice.

At this point you should be able to click on "Start" to kick the generator off generating dummy orders - and the map will start to update.


  