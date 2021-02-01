package order.kafka

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.time.Duration.ofMillis
import java.util.*

fun main(args: Array<String>) {

    val topic = "order-submitted"
    var brokers = "localhost:9092"

//    val props = Properties()
//    props["bootstrap.servers"] = brokers
//    props["key.serializer"] = StringSerializer::class.java.canonicalName
//    props["value.serializer"] = StringSerializer::class.java.canonicalName
//    props["value.deserializer"] = StringSerializer::class.java.canonicalName

    val props = Properties()
    props["bootstrap.servers"] = "localhost:9092"
    props["group.id"] = "test"
    props["enable.auto.commit"] = "true"
    props["auto.commit.interval.ms"] = "1000"
    props["session.timeout.ms"] = "30000"
    props["key.serializer"] = StringSerializer::class.java.canonicalName
    props["key.deserializer"] = ByteArrayDeserializer::class.java.canonicalName
    props["value.serializer"] = StringSerializer::class.java.canonicalName
    props["value.deserializer"] = ByteArrayDeserializer::class.java.canonicalName

//    props["partition.assignment.strategy"] = "range"

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
                    //println("Consumed record with key ${record.key()} and value ${record.value()}, and updated total count to $newCount")
                    println(record)
                    newCount
                })
        }
    }
}
