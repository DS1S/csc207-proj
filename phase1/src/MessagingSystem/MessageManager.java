package MessagingSystem;
import CoreEntities.Message;

import java.io.Serializable;
import java.util.*;
import java.time.LocalTime;

/**
 * Class to manage messages
 */
public class MessageManager implements Serializable {
    private final Map<UUID, List<Message>> inboxes;

    public MessageManager(List<UUID> userIDs) {
        inboxes = new HashMap<>();
        for (UUID id : userIDs){
            inboxes.put(id, new ArrayList<>());
        }
    }

    /**
     * Send a message to recipient by appending it to his list of messages.
     *
     * @param sender the UUID of the sender.
     * @param recipient the UUID of the recipient
     * @param msg A string which is the body of the message.
     */
    public void sendMessageToIndividual(UUID sender, UUID recipient, String msg) {
        Message m = new Message(UUID.randomUUID(), sender, recipient, msg, LocalTime.now());
        inboxes.get(recipient).add(m);
    }

    /**
     * Unsends or deletes a message from recipient's list of messages.
     *
     * @param recipient the UUID of the recipient
     * @param msg The message object to delete
     */
    public void deleteMessage(UUID recipient, Message msg) {
        inboxes.get(recipient).remove(msg);
    }

    /**
     * Convenience method to send a message to multiple people at once
     *
     * @param sender the UUID of the sender.
     * @param recipients A list of UUIDs of the recipients
     * @param msg A string which is the body of the message.
     */
    public void sendMessageToMultiple(UUID sender, List<UUID> recipients, String msg){
        for (UUID recipient : recipients) {
            sendMessageToIndividual(sender, recipient, msg);
        }
    }

    /**
     * 'Reply' to a message by appending it to its sender's list of messages
     *
     * @param received The message object to reply to
     * @param reply The message object to reply with
     */
    public void replyToMessage(Message received, Message reply){
        inboxes.get(received.getSender()).add(reply);
    }

    /**
     * A method to get all a user's messages and parse them into a generic format for the caller.
     *
     * @param userID the UUID of the user whose messages are desired
     * @return A list of message representations (maps) with all message info
     */
    public List<Map<String, Object>> getInboxData(UUID userID){
        List<Message> inbox = inboxes.get(userID);
        List<Map<String, Object>> inboxData = new ArrayList<>();
        for (Message message : inbox){
            inboxData.add(message.extractData());
        }
        return inboxData;
    }


    /*
    Potential use in phase 2

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
    */
}
