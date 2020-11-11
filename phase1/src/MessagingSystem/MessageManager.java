package MessagingSystem;
import CoreEntities.Users.User;
import CoreEntities.Message;
import java.util.UUID;
import java.util.List;
import java.util.Map;

public class MessageManager {
    private Map<UUID, List<Message>> tempInbox;

    public MessageManager(Map<UUID, List<Message>> tempInbox) {
        this.tempInbox = tempInbox;
    }

    public void sendMessageToIndividual(UUID recipient, Message msg) {
        tempInbox.get(recipient).add(msg);
    }

    public void deleteMessage(UUID recipient, Message msg) {
            tempInbox.get(recipient).remove(msg);
        }

    public void sendMessageToMultiple(List<UUID> recipients, Message msg){
        for (UUID recipient : recipients){
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
