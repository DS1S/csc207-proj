package CoreEntities;
import CoreEntities.Users.User;

import java.io.Serializable;
import java.util.Map;
import java.util.List;
import java.util.UUID;

/* This class has been commented out because it is not needed for phase 1. It is being kept because it may be
useful in phase 2. Javadocs have not been added since the class is not in use.
 */

/*
public class FriendsList implements Serializable{
    private Map<UUID, List<UUID>> currentFriends;
    private Map<UUID, List<UUID>> pendingRequests;

    public FriendsList(Map<UUID, List<UUID>> currentFriends, Map<UUID, List<UUID>> pendingRequests){
        this.currentFriends = currentFriends;
        this.pendingRequests = pendingRequests;
    }

    public List<UUID> getFriends(User user){
        return currentFriends.get(user.getUUID());
    }

    public List<UUID> getPendingRequests(User user){
        return pendingRequests.get(user.getUUID());
    }

    public void sendRequest(User sender, User recipient){
        pendingRequests.get(recipient.getUUID()).add(sender.getUUID());
    }

    public void declineRequest(User sender, User recipient){
        pendingRequests.get(recipient.getUUID()).remove(sender.getUUID());
    }

    public void acceptRequest(User sender, User recipient){
        currentFriends.get(recipient.getUUID()).add(sender.getUUID());
        pendingRequests.get(recipient.getUUID()).remove(sender.getUUID());
    }

    public void deleteFriend(User user, User toDelete){
        currentFriends.get(user.getUUID()).remove(toDelete.getUUID());
    }

    public boolean isFriend(User user, UUID toCheck){
        return getFriends(user).contains(toCheck);
    }

}
*/