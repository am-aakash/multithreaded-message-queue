package message_queue.models;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import message_queue.subscribers.Subscriber;

@AllArgsConstructor
@Getter
public class TopicSubscriber {
  private AtomicInteger offset;
  private Subscriber subscriber;

  public TopicSubscriber(@NonNull final Subscriber subscriber) {
    this.subscriber = subscriber;
    this.offset = new AtomicInteger(0);
  }
}
