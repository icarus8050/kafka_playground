# Kafka Playground
* 주키퍼 서버 실행 명령어
```shell
./kafka_2.12-3.2.1/bin/zookeeper-server-start.sh ./kafka_2.12-3.2.1/config/zookeeper.properties
```

* 카프카 브로커 서버 실행 명령어
```shell
./kafka_2.12-3.2.1/bin/kafka-server-start.sh ./kafka_2.12-3.2.1/config/server.properties
```

* 토픽 생성 명령어
```shell
./kafka_2.12-3.2.1/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic {topic} --partitions {n} --replication-factor {n}
```

* 토픽 상세 보기 명령어
```shell
./kafka_2.12-3.2.1/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic {topic}
```

* 토픽 목록 보기 명령어
```shell
./kafka_2.12-3.2.1/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

* 카프카 콘솔 프로듀서 실행 명령어
```shell
./kafka_2.12-3.2.1/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic {topic}
```

* 카프카 콘솔 컨슈머 실행 명령어
```shell
./kafka_2.12-3.2.1/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic {topic}
```