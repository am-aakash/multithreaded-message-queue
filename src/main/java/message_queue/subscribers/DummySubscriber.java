package message_queue.subscribers;

import message_queue.models.Message;

public class DummySubscriber implements Subscriber {
  private final String id;

  public DummySubscriber(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void consume(Message message) throws InterruptedException {
    Thread.sleep(2000);
    System.out.println(id.toUpperCase() + " recieved: " + message.getMessageString());
  }
}
