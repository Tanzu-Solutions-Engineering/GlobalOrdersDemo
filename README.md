# GlobalOrdersDemo
A demo app written in Spring Boot useful for demonstrating Pivotal Cloud Foundry

Loosely based on this app: https://github.com/Pivotal-Field-Engineering/PCF-demo

## Building
Build each of the apps and push them to Cloud Foundry

	mvn clean install
	cf push <app> -b java_buildpack -m 1G --random-route -p target/<app>.jar

If you navigate your browser to the orders-demo-ui app, you will see a user interface
presenting a map of the world and some information about the other microservices.

## Configuring Services
To start with, nothing will work! Time for some configuration.

1. Obtain the hostname of the monitor and generator apps
2. Provide the names to the ui app by setting environment variables

	cf set-env orders-demo-ui monitorHost <monitorHostname>
	cf set-env orders-demo-ui generatorHost <generatorHostname>
3. Restart the ui app

	cf restage orders-demo-ui
  
It still doesn't work! This is because there is no message queue to which the generator can send orders for the monitor to summarize.

Now you'll need to create a RabbitMQ instance and bind it to the generator and monitor apps.

In PWS (https://run.pivotal.io) you might do the following

	cf create-service cloudamqp lemur ordermq
	cf bind-service orders-demo-monitor ordermq
	cf bind-service orders-demo-generator ordermq
	cf restage orders-demo-generator
	cf restage orders-demo-monitor

Now the UI should should the name of the RabbitMQ host for each microservice.

At this point you should be able to click on "Start" to kick the generator off generating dummy orders - and the map will start to update.

## Getting Metrics From Spring Boot Actuator
Spring Boot Actuator provides a simple way to expose a number of standard metrics about an application via http endpoints. PCF is able to read these metrics using the Metric Registrar (https://docs.pivotal.io/pivotalcf/2-4/metric-registrar/index.html) and display them in PCFMetrics.

There are a few steps involved in making this happen.

1. Enable Actuator
2. Enable Prometheus
3. Expose Endpoints
4. Test locally
5. Install cf-cli plugins
6. Redeploy app to PCF and bind metrics using the registrar
7. Test and view in PCF Metrics
	

### Enable Actuator 

Add actuator to your application by including the spring-boot-starter-actuator dependency

This is already done in the project by the following section in the pom.xml file

		 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>	
        
### Enable Prometheus

Expose actuator metrics using Prometheus format so they can be understood by the Metrics Registrar

Another pom.xml dependency

		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-prometheus</artifactId>
		</dependency>
        
### Expose Endpoints

Add settings in the application.properties file to enable both the default (JSON) metrics
endpoints as well as the Prometheus formatted endpoint.

This configuration also add an addition cloudfoundry endpoint which provides additional information
about application health for PCF Apps Manager to consume.

	management.cloudfoundry.enabled=true
	management.endpoints.web.exposure.include = metrics,prometheus
	management.endpoint.metrics.enabled = true
	management.endpoint.prometheus.enabled = true

  
### Testing

You can test that this has worked by running the app locally

	mvn clean install
	java -jar target/orders-demo-0.0.1-SNAPSHOT.jar

And pointing your browser at http://localhost:8080/actuator which should show the list of endpoints

	{
		"_links": {
			"self": {
			"href": "http://localhost:8080/actuator",
			"templated": false
		},
		"prometheus": {
			"href": "http://localhost:8080/actuator/prometheus",
			"templated": false
		},
		"metrics-requiredMetricName": {
			"href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
			"templated": true
		},
		"metrics": {
			"href": "http://localhost:8080/actuator/metrics",
			"templated": false
		}
	}

### Install cf-cli Plugins

The metric-registrar plugin provides commands for binding and unbinding and app's metrics.

	cf install-plugin  -r CF-Community "metric-registrar"
	
The log-cache plugin provides the ability to view metrics in the app's logs - these are normally filtered
out when the logs are viewed using the `cf logs` command.

	cf install-plugin -r "CF-Community" log-cache
	
### Redeploy App to PCF and Bind Metrics Using the Registrar

If you have made changes to the application's (orders-demo-ui) configuration, rebuild and redeploy the app.

	mvn clean install
	cf push ui -b java_buildpack -p target/orders-demo-0.0.1-SNAPSHOT.jar
	
Bind the metrics to the log stream

	cf register-metrics-endpoint ui /actuator/prometheus
	
### Test and view in PCF Metrics

Metrics should now be visible in the app's log stream

	cf tail ui
	
And once PCF Metrics has caught up you should e able to view and add available metrics in the PCF Metrics UI.
It may take a few minutes before the new metrics become available.

# Traces

The UI app calls the monitor app to get the latest heatmap. It is possible to see this interaction in PCF Metrics since the project has spring-cloud-sleuth enabled for tracing.

Go to a log entry in PCF Metrics and click on the 'View in Trace Explorer' icon.

