Apache Spark Streaming example
==============================

This is an exmaple to test [Apache Spark Streaming](http://spark.apache.org). One task writes data to a [RabbitMQ](http://www.rabbitmq.com/) and another Spark Streaming task reads data from the queue.

### Modules 
* root project: Apache Spark Streaming from a MQTT source ([RabbitMQ plugin](http://www.rabbitmq.com/mqtt.html)).
* data-acquisition: publishes data to RabbitMQ.

## Requirements
* Install [RabbitMQ](http://www.rabbitmq.com/).

## Run
To start generating data run in a shell:
```
sbt acquisition/run
```
To start the Spark Streaming task run in a second shell:
```
sbt run
```
