package order.kafka

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration.ofMillis
import java.util.*

fun main(args: Array<String>) {

    val topic = "order-submitted"


    val props = Properties()
    props["bootstrap.servers"] = "localhost:9092"
    props["group.id"] = "test"
    props["enable.auto.commit"] = "true"
    props["auto.commit.interval.ms"] = "1000"
    props["session.timeout.ms"] = "30000"
    props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
    props["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"


    props["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
    props["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"


    val consumer = KafkaConsumer<String, String>(props).apply {
        subscribe(listOf(topic))
    }

    var totalCount = 0L

    consumer.use {
        while (true) {
            totalCount = consumer
                .poll(ofMillis(100))
                .fold(totalCount, { accumulator, record ->
                    val newCount = accumulator + 1
                    println("Message Received - ${record.value()}")
                    newCount
                })
        }
    }
}
