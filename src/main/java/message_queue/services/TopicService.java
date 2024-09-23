package message_queue.services;

import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;
import message_queue.models.Topic;
import message_queue.models.TopicSubscriber;

public class TopicService {
  private Topic topic;
  private Map<String, SubscriberService> subscriberServices;

  public TopicService(@NonNull final Topic topic) {
    this.topic = topic;
    this.subscriberServices = new HashMap<>();
  }

  public void publish() {

  }

  public void startSubscriberService(TopicSubscriber topicSunscriber) {

  }
}
