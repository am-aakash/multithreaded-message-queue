package message_queue.services;

import lombok.Getter;
import lombok.SneakyThrows;
import message_queue.models.Topic;
import message_queue.models.TopicSubscriber;

@Getter
public class SubscriberService implements Runnable {
  private Topic topic;
  private TopicSubscriber topicSubscriber;

  @SneakyThrows
  @Override
  public void run() {
    synchronized (topicSubscriber) {

    }
  }

  public void wakeUpIfNeeded() {
  }
}
