package CoreEntities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.String;
import java.time.LocalTime;
import java.io.Serializable;

public class Message implements Serializable {
    private UUID msgId;
    private UUID sender;
    private UUID recipient;
    private String body;
    private LocalTime timeSent;

    public Message(UUID msgId, UUID sender, UUID recipient, String body, LocalTime timeSent){
        this.msgId = msgId;
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.timeSent = timeSent;
    }

    public UUID getSender(Message msg){ return msg.sender; }

    public UUID getRecipient(Message msg){ return msg.recipient; }


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