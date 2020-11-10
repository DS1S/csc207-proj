package MessagingSystem;
import CoreEntities.User;
import CoreEntities.Message;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class MessageManager {
    private Map<UUID, List<Message>> tempInbox;

    public MessageManager(Map<UUID, List<Message>> tempInbox) {
        this.tempInbox = tempInbox;
    }

    public void sendMessageToIndividual(User recipient, Message msg) {
        tempInbox.get(recipient.getUUID()).add(msg);
    }

    public void deleteMessage(User recipient, Message msg) {
            tempInbox.get(recipient.getUUID()).remove(msg);
        }

    public void sendMessageToMultiple(List<User> recipients, Message msg){
        for (User recipient : recipients){
            sendMessageToIndividual(recipient, msg);
        }
    }

    public void replyToMessage(Message received, Message reply){
        tempInbox.get(received.getSender(received)).add(reply);
    }

    public List<Message> getMessages(User user){
        return tempInbox.get(user.getUUID());
    }
}
