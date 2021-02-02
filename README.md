## OrderingApp

# Installation Instructions

# Pre-Requisites

1. Download and extract Kafka latest version from official website - https://kafka.apache.org/downloads
2. Run below commands to - <br/>
   start zookeeper<br/>
  ` $ bin/zookeeper-server-start.sh config/zookeeper.properties` <br/>
   
   start kafka server<br/>
   `$ bin/kafka-server-start.sh config/server.properties>` <br/>
   
   create topic<br/>
   `$ bin/kafka-topics.sh --create --topic order-submitted --bootstrap-server localhost:9092` <br/>
   
3. Run MailServiceConsumer at file location -  `kotlin/order/kafka/MailServiceConsumer.kt` <br/>

4. Run OrderApp by passing command line arguments like Apple Apple Orange Apple<br/>
      without offers - `kotlin/order/app/OrderServiceApp.kt`<br/>
      with offers - `kotlin/order/app/OrderServiceAppWithOffer.kt`<br/>
      
      
      
      


   
  ### Sample Outputs
 #####1.Running OrderServiceApp.kt with command line arguments - [Apple, Apple, Orange, Apple] <br/>
 &nbsp; &nbsp; &nbsp; OrderServiceApp.kt output <br/>
          ![Alt text](src/main/resources/Outputs/1.png?raw=true "Optional Title") <br/>
 
 &nbsp; &nbsp; &nbsp; Order notification sent via MailService console <br/>
          ![Alt text](src/main/resources/Outputs/2.png?raw=true "Optional Title") <br/>

 #####2.Running OrderServiceAppWithOffer.kt with command line arguments - [Apple, Apple, Orange, Orange, Orange] <br/>
 &nbsp; &nbsp; &nbsp; OrderServiceAppWithOffer.kt output <br/>
          ![Alt text](src/main/resources/Outputs/3.png?raw=true "Optional Title") <br/>

&nbsp; &nbsp; &nbsp; Order with offer notification sent via MailService console <br/>
          ![Alt text](src/main/resources/Outputs/4.png?raw=true "Optional Title") <br/>

 #####3.Running OrderServiceAppWithOffer.kt with command line arguments with exceeded stock limit- [Apple, Apple, Orange, Orange, Orange, Orange, Orange] <br/>
&nbsp; &nbsp; &nbsp; OrderServiceAppWithOffer.kt output <br/>
         ![Alt text](src/main/resources/Outputs/5.png?raw=true "Optional Title") <br/>

&nbsp; &nbsp; &nbsp; Order out of stock notification sent via MailService console <br/>
        ![Alt text](src/main/resources/Outputs/6.png?raw=true "Optional Title") <br/>