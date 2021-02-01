## OrderingApp

# Installation Instructions

# Pre-Requisites

1. Download and extract Kafka latest version from official website - https://kafka.apache.org/downloads
2. Run below commands to - 
   start zookeeper
   $ bin/zookeeper-server-start.sh config/zookeeper.properties
   
   start kafka server
   $ bin/kafka-server-start.sh config/server.properties
   
   create topic
   $ bin/kafka-topics.sh --create --topic order-submitted --bootstrap-server localhost:9092
   
3. Run MailServiceConsumer at file location -  kotlin/order/kafka/MailServiceConsumer.kt 

4. Run OrderApp by passing command line arguments like Apple Apple Orange Apple
      without offers - kotlin/order/app/OrderServiceApp.kt
      with offers - kotlin/order/app/OrderServiceAppWithOffer.kt


   
 ### Sample Outputs
Running OrderServiceApp.kt with command line arguments - [Apple, Apple, Orange, Apple]


