package backend.systems.admin;

import backend.systems.MenuSystem;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.AdminUI;
import frontend.InboxUI;

import java.util.Scanner;

public class MessageViewingSystem extends MenuSystem {

    UserManager um;
    private AdminUI adminUI;
    private InboxUI inboxUI;
    private final Scanner scanner = new Scanner(System.in);
    private MessageManager messageManager;

    public MessageViewingSystem(UserManager um, MessageManager messageManager){
        super(2);
        adminUI = new AdminUI();
        inboxUI = new InboxUI(um);
        this.um = um;
        this.messageManager = messageManager;
    }

    @Override
    protected void displayOptions() {
        adminUI.displayMessageViewOptions();
    }

    @Override
    protected void processInput(int index) {
        adminUI.promptUserName();
        String username = scanner.nextLine();
        if (um.getUUIDWithUsername(username) == null){
            adminUI.displayInvalidUser();
            return;
        }

        switch(index){
            case 0:
                inboxUI.displayInbox(messageManager.getSentMessageData(um.getUUIDWithUsername(username)));
                break;
            case 1:
                inboxUI.displayInbox(messageManager.getInboxData(um.getUUIDWithUsername(username)));
                break;
        }
    }
}
