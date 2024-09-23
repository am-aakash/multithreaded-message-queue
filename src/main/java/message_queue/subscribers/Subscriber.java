package message_queue.subscribers;

import message_queue.models.Message;

public interface Subscriber {
  public String getId();

  public void consume(Message message) throws InterruptedException;
}
