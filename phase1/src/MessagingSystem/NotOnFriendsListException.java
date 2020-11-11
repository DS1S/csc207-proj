package MessagingSystem;
import CoreEntities.User;
import CoreEntities.FriendsList;
import java.util.List;
import CoreEntities.Message;

public class NotOnFriendsListException extends Exception {
    private MessageManager messageManager;

    public NotOnFriendsListException(MessageManager messageManager){
        this.messageManager = messageManager;
    }

    public void preventMessage(User sender, User recipient){
        friendlist = FriendsList()
        if (FriendsList.getFriends(sender) )
    }
}
