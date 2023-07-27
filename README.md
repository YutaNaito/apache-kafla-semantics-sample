# Apache Kafka Message Semantics Sample

### Summary

The following are example implementations of Producer and Consumer that achieve the following delivery semantics in Apache Kafka
* at most once semantics
  * Process message at most once, missing is acceptable
* at least once semantics
  * Process a message at least once, duplicates are acceptable
* at exactly once semantics
  * Ensure messages are processed only once, do not tolerate duplicates and missing messages


### Dependency

* Java

  ```
  java 19.0.2 2023-01-17
  Java(TM) SE Runtime Environment (build 19.0.2+7-44)
  Java HotSpot(TM) 64-Bit Server VM (build 19.0.2+7-44, mixed mode, sharing)
  ```

* Apache Maven

  ```
  Apache Maven 3.9.2 (c9616018c7a021c1c39be70fb2843d6f5f9b8a1c)
  Maven home: /opt/homebrew/Cellar/maven/3.9.2/libexec
  Java version: 19.0.2, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-19.jdk/Contents/Home
  Default locale: ja_JP, platform encoding: UTF-8
  OS name: "mac os x", version: "13.2.1", arch: "aarch64", family: "mac"
  ```

* Apache Kafka
  * 3.5.1


### Command

* clean

  `mvn clean -f "<プロジェクトディレクトリ>/apache-kafka-message-semantics-sample/demo/pom.xml"`

* install

  `mvn install -f "<プロジェクトディレクトリ>/apache-kafka-message-semantics-sample/demo/pom.xml"`

* package

  `mvn package -f "<プロジェクトディレクトリ>/apache-kafka-message-semantics-sample/demo/pom.xml"`

* execute

  * Producer

    `java -cp target/demo-1.0-SNAPSHOT-jar-with-dependencies.jar com.kafka.sample.SampleProducer`

  * Consumer

    `java -cp target/demo-1.0-SNAPSHOT-jar-with-dependencies.jar com.kafka.sample.SampleConsumer`


### refarence
- https://kafka.apache.org/
- https://docs.confluent.io/ja-jp/platform/7.1/tutorials/examples/clients/docs/java.html#consume-records