package CoreEntities;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Inbox {
    private String ownerUUID;
    private List<Message> messages;


    public Inbox(String ownerUUID){
        this.ownerUUID = ownerUUID;
        this.messages  = new ArrayList<>();
    }

    public void addMessage(Message msg){
        messages.add(msg);
    }

    public void removeMessage(Message msg){
        messages.remove(msg);
    }

    public String toString(){
        StringBuilder inboxStr = new StringBuilder();
        for(Message msg : messages){
            inboxStr.append(msg.toString() + "\n");
        }
        return inboxStr.toString();
    }

    public String toString(String Criterion, Object value){
        List<Message> searchedMessages = retrieveMessageByCriterion(Criterion, value);
        StringBuilder searchedMsgStr = new StringBuilder();
        for(Message msg : searchedMessages){
            searchedMsgStr.append(msg.toString() + "\n");
        }
        if (searchedMsgStr.toString().isEmpty()) return "Search results: 0";
        return searchedMsgStr.toString();
    }

    private List<Message> retrieveMessageByCriterion(String criterion, Object value) {
        List<Message> matchedMessages = new ArrayList<>();
        try{
            for (Message msg : messages){
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
