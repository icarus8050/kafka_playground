package chapter_05;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ExactlyOnceProducer {
    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092";
        Properties props = new Properties();
        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ENABLE_IDEMPOTENCE_CONFIG, "true"); // 정확히 한번 전송을 위한 설정
        props.setProperty(ACKS_CONFIG, "all"); // 정확히 한번 전송을 위한 설정
        props.setProperty(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); // 정확히 한번 전송을 위한 설정
        props.setProperty(RETRIES_CONFIG, "5"); // 정확히 한번 전송을 위한 설정
        props.setProperty(TRANSACTIONAL_ID_CONFIG, "peter-transaction-01"); // 정확히 한번 전송을 위한 설정 (프로듀서 프로세스마다 고유한 아이디로 설정해야 한다.)

        Producer<String, String> producer = new KafkaProducer<>(props);

        producer.initTransactions(); // 프로듀서 트랜잭션 초기화
        producer.beginTransaction(); // 프로듀서 트랜잭션 시작
        try {
            for (int i = 0; i < 1; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>("peter-test05", "Apache Kafka is a distributed streaming platform - " + i);
                producer.send(record);
                producer.flush();
                System.out.println("Message sent successfully");
            }
        } catch (Exception e) {
            producer.abortTransaction(); // 프로듀서 트랜잭션 중단
            e.printStackTrace();
        } finally {
            producer.commitTransaction(); // 프로듀서 트랜잭션 커밋
            producer.close();
        }
    }
}
