package message_queue.services;

import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;
import message_queue.models.Topic;
import message_queue.models.TopicSubscriber;

public class TopicService {
  private Topic topic;
  private Map<String, SubscriberWorker> subscriberWorkers;

  public TopicService(@NonNull final Topic topic) {
    this.topic = topic;
    this.subscriberWorkers = new HashMap<>();
  }

  public void publish() {
    for (TopicSubscriber topicSubscriber : topic.getSubscribers()) {
      startSubsriberWorker(topicSubscriber);
    }
  }

  public void startSubsriberWorker(TopicSubscriber topicSubscriber) {
    final String subscriberId = topicSubscriber.getSubscriber().getId();
    if (!subscriberWorkers.containsKey(subscriberId)) {
      final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
      subscriberWorkers.put(subscriberId, subscriberWorker);
      new Thread(subscriberWorker).start();
    }
    final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
    subscriberWorker.wakeUpIfNeeded();
  }
}
