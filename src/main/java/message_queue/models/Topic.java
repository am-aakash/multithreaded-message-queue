package message_queue.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Topic {
  private String topicId;
  private String topicName;
  private List<Message> messages;
  private List<TopicSubscriber> subscribers;

  public Topic(@NonNull final String topicId, @NonNull final String topicName) {
    this.topicId = topicId;
    this.topicName = topicName;
    this.messages = new ArrayList<>();
    this.subscribers = new ArrayList<>();
  }

  public synchronized void addMessage(@NonNull Message message) {
    messages.add(message);
  }

  public void addSubscriber(@NonNull TopicSubscriber subscriber) {
    subscribers.add(subscriber);
  }
}
