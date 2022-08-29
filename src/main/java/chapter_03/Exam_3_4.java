package chapter_03;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class Exam_3_4 {
    public static void main(String[] args) {
        Properties props = new Properties(); //Properties 오브젝트를 시작합니다.
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); //브로커 리스트를 정의.
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        //Properties 오브젝트를 전달해 새 프로듀서를 생성.

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < 3; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>("peter-basic-01", "Apache Kafka is a distributed streaming platform - " + i); //ProducerRecord 오브젝트를 생성.
                producer.send(record, new PeterProducerCallback(record)); //프로듀서에서 레코드를 보낼 때 콜백 오브젝트를 같이 보냄.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
