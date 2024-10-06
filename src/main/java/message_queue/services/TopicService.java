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
    // for each subscriber call the start subscriber worker
    for (TopicSubscriber topicSubscriber : topic.getSubscribers()) {
      startSubsriberWorker(topicSubscriber);
    }
  }

  // to start the subscriber worker or wake up if worker exists
  public void startSubsriberWorker(TopicSubscriber topicSubscriber) {
    final String subscriberId = topicSubscriber.getSubscriber().getId();
    if (!subscriberWorkers.containsKey(subscriberId)) {
      // if subscriber is new then create new thread of subscriber worker runnable and
      // start it
      final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
      subscriberWorkers.put(subscriberId, subscriberWorker);
      new Thread(subscriberWorker).start();
    }
    // if subscriber is already present just wakeup the subscriber which might be
    // waiting for new message
    final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
    subscriberWorker.wakeUpIfNeeded();
  }
}
