package MessagingSystem;
import CoreEntities.Users.User;
import CoreEntities.Message;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.time.LocalTime;

public class MessageManager {
    private Map<UUID, List<Message>> tempInbox;

    public MessageManager(Map<UUID, List<Message>> tempInbox) {
        this.tempInbox = tempInbox;
    }

    public void sendMessageToIndividual(UUID sender, UUID recipient, String msg) {
        Message m = new Message(UUID.randomUUID(), sender, recipient, msg, LocalTime.now());
        tempInbox.get(recipient).add(m);
    }

    public void deleteMessage(UUID recipient, Message msg) {
            tempInbox.get(recipient).remove(msg);
        }

    public void sendMessageToMultiple(UUID sender, List<UUID> recipients, String msg){
        for (UUID recipient : recipients) {
            sendMessageToIndividual(sender, recipient, msg);
        }
    }

    public void replyToMessage(Message received, Message reply){
        tempInbox.get(received.getSender(received)).add(reply);
    }

    public List<Message> getMessages(User user){
        return tempInbox.get(user.getUUID());
    }
}
