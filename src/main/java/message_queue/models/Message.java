package message_queue.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Message {
  private String messageString;
  private Object messageData;
}
