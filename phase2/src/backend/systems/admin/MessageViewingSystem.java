package backend.systems.admin;

import backend.systems.MenuSystem;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.AdminUI;
import frontend.InboxUI;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class MessageViewingSystem extends MenuSystem {

    UserManager um;
    private AdminUI adminUI;
    private InboxUI inboxUI;
    private final Scanner scanner = new Scanner(System.in);
    private MessageManager messageManager;

    public MessageViewingSystem(UserManager um, MessageManager messageManager){
        super(4);
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
            case 1:
                inboxUI.displayInbox(messageManager.getSentMessageData(um.getUUIDWithUsername(username)));
                break;
            case 2:
                inboxUI.displayInbox(messageManager.getInboxData(um.getUUIDWithUsername(username)));
                break;
            case 3:
                inboxUI.displayInbox(messageManager.getInboxData(um.getUUIDWithUsername(username)));
                if (!messageManager.userHasMail(um.getUUIDWithUsername(username))) {
                    adminUI.displayNoMail();
                    return;
                }

                inboxUI.deletionPrompt();
                String title = askForString("Title");

                if (messageManager.deleteMessage(messageManager.getMessageIdByTitle(title,
                        um.getUUIDWithUsername(username)),
                        um.getUUIDWithUsername(username))){
                    inboxUI.deletedPrompt();
                }
                else { adminUI.displayDeleteFailure(); }
                break;
        }
    }
}
