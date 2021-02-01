package order.config

//import org.apache.kafka.clients.producer.ProducerConfig
//import org.apache.kafka.common.serialization.StringSerializer
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.core.DefaultKafkaProducerFactory
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.kafka.core.ProducerFactory
//import org.springframework.kafka.support.serializer.JsonSerializer
//import reactor.kafka.sender.internals.ProducerFactory
//import java.awt.print.Book


//
//class KafkaConfig {
//    @Bean
//    fun producerFactory(): ProducerFactory<String, Book> {
//        val config: MutableMap<String, Any> = HashMap()
//        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "127.0.0.1:9092"
//        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
//        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
//        return DefaultKafkaProducerFactory(config)
//    }
//
//    @Bean
//    fun kafkaTemplate(): KafkaTemplate {
//        return KafkaTemplate(producerFactory())
//    }
//}