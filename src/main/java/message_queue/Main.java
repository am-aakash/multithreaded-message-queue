package message_queue;

import java.util.HashMap;
import java.util.Map;
import message_queue.models.Message;
import message_queue.models.Topic;
import message_queue.services.MessageQueue;
import message_queue.subscribers.DummySubscriber;

public class Main {
    public static void main(String[] args) {

        // Acts as controller layer & stores topic and subscriber details as cache or DB
        final MessageQueue queue = new MessageQueue();
        final Topic sportsTopic = queue.createTopic("sports");
        final Topic entertainmentTopic = queue.createTopic("entertainment");
        final DummySubscriber subscriber1 = new DummySubscriber("sportsSubscriber");
        final DummySubscriber subscriber2 = new DummySubscriber("entertainmentSubscriber");
        queue.subscribe(subscriber1, sportsTopic);
        // queue.subscribe(subscriber2, entertainmentTopic);

        // final DummySubscriber subscriber3 = new
        // DummySubscriber("entertainmentSubscriber2");
        // queue.subscribe(subscriber3, entertainmentTopic);

        Map<String, String> dummyObjectForMessage = new HashMap<>();
        dummyObjectForMessage.put("jsonkey", "jsonValue");

        queue.publish(sportsTopic, new Message("RCB wins IPL 2025", dummyObjectForMessage));
        queue.publish(entertainmentTopic, new Message("Shahrukh Khan gives another superhit", dummyObjectForMessage));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.publish(sportsTopic, new Message("India wins the Champions Trophy 2025", dummyObjectForMessage));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.publish(entertainmentTopic,
                new Message("Shahrukh Khan's new movie available on Netflix now", dummyObjectForMessage));

        final DummySubscriber subscriber4 = new DummySubscriber("allTopicsSubscriber");
        queue.subscribe(subscriber4, sportsTopic);
        queue.subscribe(subscriber4, entertainmentTopic);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.publish(entertainmentTopic, new Message("Entertainment news after eternity", dummyObjectForMessage));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.publish(sportsTopic, new Message("Badminton keeps you fit", dummyObjectForMessage));
    }
}