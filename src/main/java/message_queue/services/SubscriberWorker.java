package message_queue.services;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import message_queue.models.Message;
import message_queue.models.Topic;
import message_queue.models.TopicSubscriber;

@Getter
public class SubscriberWorker implements Runnable {
  private Topic topic;
  private TopicSubscriber topicSubscriber;

  public SubscriberWorker(@NonNull final Topic topic, @NonNull final TopicSubscriber topicSubscriber) {
    this.topic = topic;
    this.topicSubscriber = topicSubscriber;
  }

  @SneakyThrows
  @Override
  public void run() {
    // synchronized making sure only one thread is accessing topicSubscriber in the
    // loop
    synchronized (topicSubscriber) {
      // infinite loop handling consume of message according to offset in topic
      // subscriber
      do {
        int curOffset = topicSubscriber.getOffset().get();
        while (curOffset >= topic.getMessages().size()) {
          // -ask this thread to wait to coordinate with other threads
          // -gets notified when sysnchronized addmessage() transaction
          // completes and notifiesAll()
          topicSubscriber.wait();
        }
        Message message = topic.getMessages().get(curOffset);
        topicSubscriber.getSubscriber().consume(message);

        // We cannot just increment here since subscriber offset can be reset while it
        // is consuming. So, after
        // consuming we need to increase only if it was previous one.
        topicSubscriber.getOffset().compareAndSet(curOffset, curOffset + 1);
      } while (true);
    }
  }

  public synchronized void wakeUpIfNeeded() {
    synchronized (topicSubscriber) {
      topicSubscriber.notifyAll();
    }
  }
}
