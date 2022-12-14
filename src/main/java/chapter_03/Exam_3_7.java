package chapter_03;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class Exam_3_7 {
    public static void main(String[] args) {
        Properties props = new Properties(); //Properties 오브젝트를 시작.
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); //브로커 리스트를 정의.
        props.put(GROUP_ID_CONFIG, "peter-consumer01"); //컨슈머 그룹 아이디 정의.
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false"); //오토 커밋을 사용하지 않음.
        props.put(AUTO_OFFSET_RESET_CONFIG, "latest"); //컨슈머 오프셋을 찾지 못하는 경우 latest로 초기화. 가장 최근부터 메시지를 가져옴.
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer"); //문자열을 사용했으므로 StringDeserializer 지정.
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props); //Properties 오브젝트를 전달하여 새 컨슈머를 생성.

        try (consumer) {
            consumer.subscribe(List.of("peter-basic-01")); //구독할 토픽을 지정.
            while (true) { //무한 루프 시작. 메시지를 가져오기 위해 카프카에 지속적으로 poll()을 함.
                ConsumerRecords<String, String> records = consumer.poll(1000); //컨슈머는 폴링하는 것을 계속 유지하며, 타임 아웃 주기를 설정.해당 시간만큼 블럭함.
                for (ConsumerRecord<String, String> record : records) { //poll()은 레코드 전체를 리턴하고, 하나의 메시지만 가져오는 것이 아니므로, 반복문 처리.
                    System.out.printf("Topic: %s, Partition: %s, Offset: %d, Key: %s, Value: %s\n",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                }
                consumer.commitAsync(); //현재 배치를 통해 읽은 모든 메시지들을 처리한 후, 추가 메시지를 폴링하기 전 현재의 오프셋을 비동기 커밋합니다.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
