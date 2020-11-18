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
    private UUID msgId;
    private UUID sender;
    private UUID recipient;
    private String body;
    private LocalTime timeSent;

    /**
     * Constructs a new Message with the information below.
     * @param msgId the message id
     * @param sender the message sender
     * @param recipient the message recipient
     * @param body the message body
     * @param timeSent the time the message was sent
     */
    public Message(UUID msgId, UUID sender, UUID recipient, String body, LocalTime timeSent){
        this.msgId = msgId;
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.timeSent = timeSent;
    }

    /**
     * Returns the UUID of the message recipient.
     * @return message recipient UUID
     */
    public UUID getRecipient(){
        return recipient;
    }


    /**
     * Returns a map whose values are the message's properties corresponding to those properties in
     * string format.
     * @return a map whose values are message properties
     */
    public Map<String, Object> extractData() {
        Map<String, Object> messageData = new HashMap<>();

        messageData.put("msgID", msgId);
        messageData.put("sender", sender);
        messageData.put("recipient", recipient);
        messageData.put("body", body);
        messageData.put("timeSent", timeSent);

        return messageData;
    }
}