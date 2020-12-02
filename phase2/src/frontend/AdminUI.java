package frontend;

import java.util.ArrayList;
import java.util.List;

public class AdminUI extends MenuUI{
    public void displayAdminOptions(boolean[] perms){
        displayIndexPrompt();
        displayOptions(getOptions(perms));
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
            options.add("View a User's Messages");
        }
        return options;
    }
}
