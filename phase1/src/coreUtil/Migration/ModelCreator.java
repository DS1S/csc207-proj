package coreUtil.Migration;

import EventSystem.Managers.EventManager;
import FileHandleSystem.FileSerializer;
import LoginSystem.UserManager;
import MessagingSystem.MessageManager;

/**
 * A helper class to aid in testing.
 */
public class ModelCreator {
    /**
     * A helper class to reset the 'database' of the serialized UserManager, EventManager and MessageManager to a uniform state for testing purposes.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.addUser("attendee", "user1", "pass1", "Daniel");
        userManager.addUser("speaker", "user2", "pass2", "Walter");
        userManager.addUser("organizer", "user3", "pass3", "Adil");

        MessageManager messageManager = new MessageManager(userManager.getUUIDs());
        EventManager eventManager = new EventManager();

        FileSerializer<UserManager> userManagerFileSerializer = new FileSerializer<>("phase1/database/UManager.ser");
        FileSerializer<EventManager> eventManagerFileSerializer = new FileSerializer<>("phase1/database/ESManager.ser");
        FileSerializer<MessageManager> messageManagerFileSerializer = new FileSerializer<>("phase1/database/MSManager.ser");

        userManagerFileSerializer.saveObject(userManager);
        eventManagerFileSerializer.saveObject(eventManager);
        messageManagerFileSerializer.saveObject(messageManager);
    }
}
