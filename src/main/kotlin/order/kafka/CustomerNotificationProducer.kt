package order.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*
import java.util.concurrent.Future

class CustomerNotificationProducer {

    fun createProducer(): Producer<String, String> {

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

        return KafkaProducer<String, String>(props)
    }

    fun sendMessage(message: String, producer: Producer<String, String>) {
        val outputTopic = "order-submitted"
        var producerRecord: ProducerRecord<String, String> = ProducerRecord("order-submitted", message)

        var future: Future<RecordMetadata> = producer?.send(producerRecord)!!
        producer.flush()
        println(" message sent to " + future.get().topic())

    }
}