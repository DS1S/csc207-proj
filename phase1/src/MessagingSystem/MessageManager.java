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

    public void sendMessageToMultiple(List<UUID> recipients, List<Message> msgs){
        for (int i = 0; i < recipients.size(); i++) {
            sendMessageToIndividual(recipients.get(i), msgs.get(i));
        }
    }

    public void replyToMessage(Message received, Message reply){
        tempInbox.get(received.getSender(received)).add(reply);
    }

    public List<Message> getMessages(UUID user){
        return tempInbox.get(user);
    }

    public String toString(UUID user) {
        List<Message> inbox = getMessages(user);

        StringBuilder s = new StringBuilder();
        for (Message message : inbox) {
            s.append(message.toString());
        }

        return s.toString();
    }
}
