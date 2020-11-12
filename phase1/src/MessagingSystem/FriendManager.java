package MessagingSystem;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import CoreEntities.Users.User;
import LoginSystem.UserManager;

/* This class has been commented out because it is not needed for phase 1. It is being kept because it may be
useful in phase 2.
 */

/*
public class FriendManager {
    private UserManager userManager;
    private Map<UUID, FriendsList> friendLists;

    public FriendManager(UserManager userManager, Map<UUID, FriendsList> friendLists){
        this.userManager = userManager;
        this.friendLists = friendLists;
    }

    public FriendsList getFriendsList(User user){
        return friendLists.get(user.getUUID());
    }

    public void addFriendsList(UUID userId, FriendsList listToAdd){
        friendLists.put(userId, listToAdd);
    }

    public void addMultipleFriendsLists(Map<UUID, FriendsList> toAdd){
        friendLists.putAll(toAdd);
    }

    public void sendFriendRequest(User sender, User recipient){
        friendLists.get(recipient.getUUID()).sendRequest(sender, recipient);
    }

    public void removeFriend(User user, User toRemove){
        friendLists.get(user.getUUID()).deleteFriend(user, toRemove);
    }

    public User searchByUUID(User user, UUID toFind) throws Exception{
        if (friendLists.get(user.getUUID()).getFriends(user).contains(toFind)){
            return userManager.getUserWithUUID(toFind);
        }
        else{
            throw new Exception("User not found!");
        }
    }

    public List<User> retrieveFriends(User user){
        List<UUID> friendUUIDs = friendLists.get(user.getUUID()).getFriends(user);
        List<User> friends = new ArrayList<>();
        for (UUID id : friendUUIDs){
            friends.add(userManager.getUserWithUUID(id));
        }
        return friends;
    }

    public List<User> retrieveRequests(User user){
        FriendsList userFriendList = friendLists.get(user.getUUID());
        List<UUID> pendingIds = userFriendList.getPendingRequests(user);
        List<User> pending = new ArrayList<>();
        for (UUID pendingId : pendingIds){
            pending.add(userManager.getUserWithUUID(pendingId));
        }
        return pending;
    }

    public void acceptRequest(User user, UUID toAccept){
        FriendsList userFriendList = friendLists.get(user.getUUID());
        userFriendList.acceptRequest(userManager.getUserWithUUID(toAccept), user);
    }
}
*/