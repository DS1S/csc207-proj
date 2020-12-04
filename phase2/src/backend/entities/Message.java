package backend.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.String;
import java.time.LocalTime;
import java.io.Serializable;

/**
 * A message with a message id, a sender UUID, a recipient UUID, a body, a timestamp, a title and whether or not it is
 * read.
 */
public class Message implements Serializable {
    private final UUID msgID;
    private final UUID sender;
    private final UUID recipient;
    private final String body;
    private final LocalTime timeSent;
    private STATUSES status;
    private final String title;

    /**
     * Constructs a new Message with the information below.
     * @param msgID The ID of this Message.
     * @param sender The UUID of the person who sent this Message.
     * @param recipient The UUID of the person who received this Message.
     * @param body The body of this Message as a string.
     * @param timeSent The time this Message was sent.
     * @param title The title of the Message.
     */
    public Message(UUID msgID, UUID sender, UUID recipient, String body, LocalTime timeSent, String title) {
        this.msgID = msgID;
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.timeSent = timeSent;
        this.title = title;
        this.status = STATUSES.unread;
    }

    /**
     * Sets the status of a message.
     * @param status message status from the STATUSES enum
     */
    public void setStatus(STATUSES status){
        this.status = status;
    }

    /**
     * Returns the UUID of a message.
     * @return UUID of message
     */
    public UUID getMessageId(){return this.msgID;}

    /**
     * Returns the title of a message.
     * @return title of message
     */
    public String getMessageTitle(){return this.title;}

    /**
     * Gets the UUID of the message recipient.
     * @return The UUID of the person who received this Message.
     */
    public UUID getRecipient() {
        return recipient;
    }

    /**
     * Gets the UUID of the message sender.
     * @return The UUID of the person who sent this Message.
     */
    public UUID getSender() { return sender; }

    /**
     * Gets the body of the message.
     * @return the body of the message
     */
    public String getBody() {return this.body;}

    /**
     * Returns a map whose values are the Message's properties corresponding to those properties in
     * string format.
     * @return A map whose values are Message properties.
     */
    public Map<String, Object> extractData() {
        Map<String, Object> messageData = new HashMap<>();

        messageData.put("msgID", msgID);
        messageData.put("sender", sender);
        messageData.put("recipient", recipient);
        messageData.put("body", body);
        messageData.put("timeSent", timeSent);
        messageData.put("title", title);

        return messageData;
    }
}