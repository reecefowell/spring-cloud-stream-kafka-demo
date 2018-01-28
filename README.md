README
======

A Spring with Kafka demo.

Provisioning
------------

With Vagrant and Ansible.

```
vagrant up
```

Testing
-------

Add message to Kafka.

```
echo "Hello, World" | ~/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic TutorialTopic > /dev/null
```

View messages from Kafka.

```
~/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic TutorialTopic --from-beginning
```
