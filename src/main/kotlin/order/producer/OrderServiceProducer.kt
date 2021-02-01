package order.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import java.util.concurrent.Future

class OrderServiceProducer {

    fun produceMessage(message: String): String {

        var producerRecord: ProducerRecord<String, String> = ProducerRecord("order-submitted", message)


        val map = mutableMapOf<String, String>()

        map["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"

        map["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"

        map["bootstrap.servers"] = "localhost:9092"


        var producer = KafkaProducer<String, String>(map as Map<String, Any>?)

        var future: Future<RecordMetadata> = producer.send(producerRecord)!!

        return " message sent to " + future.get().topic();

    }
}