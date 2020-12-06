package frontend;

import java.util.ArrayList;
import java.util.List;

/**
 * UI class for printing prompts and errors related to the AdminSystem.
 */
public class AdminUI extends MenuUI{

    /**
     * Display options for main AdminSystem screen given a boolean array of relevant permissions
     * @param perms An boolean array of permissions handled by getOptions(). Each index pertains to a permission:
     *              In order, CAN_VIEW_STATS, CAN_BAN_USERS, CAN_SEE_ALL_MESSAGES
     */
    public void displayAdminOptions(boolean[] perms){
        displayOptions(getOptions(perms), true);
    }

    /**
     * Display options for banning/unbanning a user
     */
    public void displayBanOptions(){
        ArrayList<String> options = new ArrayList(){
            {
                add("Unban user");
                add("Ban user");
            }
        };

        displayOptions(options, true);
    }

    /**
     * Prompts the user for a username
     */
    public void promptUserName(){
        System.out.println("Please enter the name of the user:");
    }

    /**
     * Displays an error for invalid usernames
     */
    public void displayInvalidUser(){
        System.out.println("A user by that name was not found!");
    }

    /**
     * Display success/failure of a ban
     */
    public void displayBanSuccess(boolean banned, String username){
        System.out.println(username +  " has been successfully " + (banned?"banned":"unbanned"));
    }

    /**
     * Displays options for managing messages
     */
    public void displayMessageViewOptions(){
        ArrayList<String> options = new ArrayList(){
            {
                add("View all messages sent by a user");
                add("View all messages received by a user");
                add("Delete a user's message");
            }
        };

        displayOptions(options, true);
    }

    /**
     * Displays error for an invalid message being input
     */
    public void displayDeleteFailure(){
        System.out.println("Message was not found!");
    }

    private List<String> getOptions(boolean[] perms){
        final int CAN_VIEW_STATS = 0;
        final int CAN_BAN_USERS = 1;
        final int CAN_SEE_ALL_MESSAGES = 2;

        List<String> options = new ArrayList<>();

        if (perms[CAN_VIEW_STATS]){
            options.add("View Statistics");
        }
        if (perms[CAN_BAN_USERS]){
            options.add("Ban a User");
        }
        if (perms[CAN_SEE_ALL_MESSAGES]) {
            options.add("View/Manage a User's Messages");
        }
        return options;
    }
}
