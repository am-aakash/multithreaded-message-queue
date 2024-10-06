package message_queue.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.NonNull;
import message_queue.models.Message;
import message_queue.models.Topic;
import message_queue.models.TopicSubscriber;
import message_queue.subscribers.Subscriber;

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

  public void subscribe(@NonNull final Subscriber subscriber, @NonNull final Topic topic) {
    topic.addSubscriber(new TopicSubscriber(subscriber));
    System.out.println(subscriber.getId() + " subscribed to topic: " + topic.getTopicName());
  }

  public void publish(@NonNull final Topic topic, @NonNull final Message message) {
    topic.addMessage(message);
    System.out.println(message.getMessageString() + " published to topic: " + topic.getTopicName());
    new Thread(() -> topicProcessors.get(topic.getTopicId()).publish()).start();
  }
}
