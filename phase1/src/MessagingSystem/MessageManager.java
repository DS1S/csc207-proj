package MessagingSystem;
import CoreEntities.Users.User;
import CoreEntities.Message;

import java.lang.reflect.Method;
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

    public String toString(UUID userID){
        StringBuilder inboxStr = new StringBuilder();
        List<Message> inbox = tempInbox.get(userID);
        for(Message msg : inbox){
            inboxStr.append(msg.toString() + "\n");
        }
        return inboxStr.toString();
    }

    public String toString(String Criterion, Object value, UUID userID){
        List<Message> searchedMessages = retrieveMessageByCriterion(Criterion, value, userID);
        StringBuilder searchedMsgStr = new StringBuilder();
        for(Message msg : searchedMessages){
            searchedMsgStr.append(msg.toString() + "\n");
        }
        if (searchedMsgStr.toString().isEmpty()) return "Search results: 0";
        return searchedMsgStr.toString();
    }

    private List<Message> retrieveMessageByCriterion(String criterion, Object value, UUID userID) {
        List<Message> matchedMessages = new ArrayList<>();
        List<Message> inbox = tempInbox.get(userID);
        try{
            for (Message msg : inbox){
                Class<?> msgType = msg.getClass();
                Method desiredMethod = msgType.getMethod("get"+criterion);
                if(desiredMethod.invoke(msgType).equals(value)){
                    matchedMessages.add(msg);
                }
            }
        } catch (ReflectiveOperationException e) {
            return new ArrayList<>();
        }

        return matchedMessages;
    }
}
