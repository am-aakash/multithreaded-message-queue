package message_queue.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.NonNull;
import message_queue.models.Topic;

public class MessageQueue {
  private final Map<String, TopicService> topicProcessors;

  public MessageQueue() {
    topicProcessors = new HashMap<>();
  }

  public Topic createTopic(@NonNull final String topicName) {
    final Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
    TopicService service = new TopicService(topic);
    topicProcessors.put(topic.getTopicId(), service);
    System.out.println("Created topic: " + topic.getTopicName());
    return topic;
  }
}
