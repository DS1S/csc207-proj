package backend.systems.messaging.managers;

import backend.entities.STATUSES;
import backend.entities.Message;

import java.io.Serializable;
import java.util.*;
import java.time.LocalTime;

/**
 * Class to manage messages.*/
public class MessageManager implements Serializable {
    private final Map<UUID, List<Message>> inboxes;

    /** Constructs a new message manager with the information below.
     * @param userIDs A list of user UUIDs.
     */
    public MessageManager(List<UUID> userIDs) {
        inboxes = new HashMap<>();
        for (UUID id : userIDs) {
            inboxes.put(id, new ArrayList<>());
        }
    }

    /**
     * Send a message to recipient by appending it to his list of messages.
     * @param sender The UUID of the sender.
     * @param recipient The UUID of the recipient.
     * @param msg A string which is the body of the message.
     */
    public void sendMessageToIndividual(UUID sender, UUID recipient, String msg, String title) {
        Message m = new Message(UUID.randomUUID(), sender, recipient, msg, LocalTime.now(), title);
        inboxes.get(recipient).add(m);
    }


    /**
     * Convenience method to send a message to multiple people at once.
     * @param sender The UUID of the sender.
     * @param recipients A list of UUIDs of the recipients
     * @param msg A string which is the body of the message.
     */
    public void sendMessageToMultiple(UUID sender, List<UUID> recipients, String msg, String title) {
        for (UUID recipient : recipients) {
            sendMessageToIndividual(sender, recipient, msg, title);
        }
    }

    /**
     * Returns true if a user has an inbox in inboxes, false otherwise.
     * @param userID The UUID of the user.
     * @return True if user has an inbox, false otherwise.
     */
    public boolean userHasInbox(UUID userID) {
        return inboxes.containsKey(userID);
    }

    public boolean userHasMail(UUID userID) {
        return userHasInbox(userID) && (!inboxes.get(userID).isEmpty());
    }

    /**
     * Maps the given UUID to an empty list in inboxes.
     * @param userID The UUID of the user.
     */
    public void addBlankInbox(UUID userID) {
        inboxes.put(userID, new ArrayList<>());
    }

    /**
     * A method to get all a user's messages and parse them into a generic format for the caller.
     * @param userID the UUID of the user whose messages are desired
     * @return A list of message representations (maps) with all message info
     */
    public List<Map<String, Object>> getInboxData(UUID userID) {
        List<Message> inbox = inboxes.get(userID);
        List<Map<String, Object>> inboxData = new ArrayList<>();

        for (Message message : inbox) {
            inboxData.add(message.extractData());
        }

        return inboxData;
    }

    /**
     * Gets a message given the UUID of the message and a list of messages that contains the message.
     * @param messageId UUID of the message
     * @param messages a list of messages containing the desired message
     * @return message corresponding to the UUID
     */
    public Message getMessageById(UUID messageId, List<Message> messages) {
        for (Message message : messages) {
            if (message.getMessageId() == messageId) {
                return message;
            }
        }
        return null;
    }

    /**
     * Gets the UUID of a message given the message title.
     * @param title title of the message
     * @param messages list of messages containing the message
     * @return UUID of the message
     */
    public UUID getMessageIdByTitle(String title, List<Message> messages) {
        for (Message message : messages) {
            if (message.getMessageTitle().equals(title)){
                return message.getMessageId();
            }
        }
        return null;
    }

    public UUID getMessageIdByTitle(String title, UUID userID) {
        for (Message message : inboxes.get(userID)) {
            if (message.getMessageTitle().equals(title)){
                return message.getMessageId();
            }
        }
        return null;
    }

    /**
     * Gets the inbox of a user given the user UUID.
     * @param userId UUID of the user
     * @return user's inbox
     */
    public List<Message> getInboxByUserId(UUID userId){ return inboxes.get(userId); }

    /**
     * Deletes a message from a list of messages.
     * @param messageId UUID of the message to delete
     * @param messages a list of messages containing the message to delete
     */
    public void deleteMessage(UUID messageId, List<Message> messages){
            messages.remove(getMessageById(messageId, messages));
        }

    public boolean deleteMessage(UUID messageId, UUID userid){
        if (inboxes.get(userid).contains(getMessageById(messageId, inboxes.get(userid)))){
            inboxes.get(userid).remove(getMessageById(messageId, inboxes.get(userid)));
            return true;
        }
        return false;
    }

    /**
     * Sets the status of a message to read.
     * @param userId UUID of the user whose inbox contains the message
     * @param messageId UUID of the message
     */
    public void setRead(UUID userId, UUID messageId){
        getMessageById(messageId, inboxes.get(userId)).setStatus(STATUSES.read);
    }

    /**
     * Sets the status of a message to unread.
     * @param userId UUID of the user whose inbox contains the message
     * @param messageId UUID of the message
     */
    public void setUnread(UUID userId, UUID messageId){
        getMessageById(messageId, inboxes.get(userId)).setStatus(STATUSES.unread);
    }

    /**
     * Sets the status of a message to archived.
     * @param userId UUID of the user whose inbox contains the message
     * @param messageId UUID of the message
     */
    public void SetArchived(UUID userId, UUID messageId){
        getMessageById(messageId, inboxes.get(userId)).setStatus(STATUSES.archived);
    }

    /**
     * A method to get all a user's sent messages and parse them into a generic format for the caller.
     * @param userID the UUID of the user whose sent messages are desired
     * @return A list of message representations (maps) with all message info
     */
    public List<Map<String, Object>> getSentMessageData(UUID userID) {
        List<Map<String, Object>> inboxData = new ArrayList<>();
        for (List<Message> inbox : inboxes.values()) {
            for (Message msg : inbox) {
                if (msg.getSender() == userID) {
                    inboxData.add(msg.extractData());
                }
            }
        }
        return inboxData;
    }

    /*
    Potential use in phase 2

    public String toString(String Criterion, Object value, UUID userID) {
        List<Message> searchedMessages = retrieveMessageByCriterion(Criterion, value, userID);
        StringBuilder searchedMsgStr = new StringBuilder();
        for(Message msg : searchedMessages) {
            searchedMsgStr.append(msg.toString() + "\n");
        }
        if (searchedMsgStr.toString().isEmpty()) return "Search results: 0";
        return searchedMsgStr.toString();
    }

    private List<Message> retrieveMessageByCriterion(String criterion, Object value, UUID userID) {
        List<Message> matchedMessages = new ArrayList<>();
        List<Message> inbox = tempInbox.get(userID);
        try{
            for (Message msg : inbox) {
                Class<?> msgType = msg.getClass();
                Method desiredMethod = msgType.getMethod("get"+criterion);
                if(desiredMethod.invoke(msgType).equals(value)) {
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
