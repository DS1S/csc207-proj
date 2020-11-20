package CoreEntities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.String;
import java.time.LocalTime;
import java.io.Serializable;

/**
 * A message with a message id, a sender UUID, a recipient UUID, a body and a timestamp.
 */
public class Message implements Serializable {
    private final UUID msgID;
    private final UUID sender;
    private final UUID recipient;
    private final String body;
    private final LocalTime timeSent;

    /**
     * Constructs a new Message with the information below.
     * @param msgID The ID of this Message.
     * @param sender The UUID of the person who sent this Message.
     * @param recipient The UUID of the person who received this Message.
     * @param body The body of this Message as a string.
     * @param timeSent The time this Message was sent.
     */
    public Message(UUID msgID, UUID sender, UUID recipient, String body, LocalTime timeSent) {
        this.msgID = msgID;
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.timeSent = timeSent;
    }

    /**
     * Gets the UUID of the message recipient.
     * @return The UUID of the person who received this Message.
     */
    public UUID getRecipient() {
        return recipient;
    }


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

        return messageData;
    }
}