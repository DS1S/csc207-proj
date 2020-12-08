package utility.models;

import backend.systems.conference.ConferenceManager;
import backend.systems.events.managers.EventManager;
import utility.filehandling.FileSerializer;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.social.managers.MessageManager;

import java.time.LocalDate;

/**
 * A helper class to aid in testing.
 */
public class ModelCreator {
    /**
     * A helper class to reset the 'database' of the serialized UserManager, EventManager and MessageManager to a
     * uniform state for testing purposes.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.addUser("attendee", "user1", "pass1", "Daniel");
        userManager.addUser("speaker", "user2", "pass2", "Walter");
        userManager.addUser("organizer", "user3", "pass3", "Adil");
        userManager.addUser("speaker", "user4", "pass4", "Edwin");
        userManager.addUser("admin", "user5", "pass5", "Daniel2");

        MessageManager messageManager = new MessageManager(userManager.getUUIDs());
        EventManager eventManager1 = new EventManager();
        EventManager eventManager2 = new EventManager();
        EventManager eventManager3 = new EventManager();
        ConferenceManager conferenceManager = new ConferenceManager();


        conferenceManager.addConference("Saw Con", LocalDate.of(2020, 11, 28));
        conferenceManager.addConference("Li  Con", LocalDate.of(2020, 11, 29));
        conferenceManager.addConference("Cho Con", LocalDate.of(2020, 11, 30));

        FileSerializer<UserManager> userManagerFileSerializer = new FileSerializer<>("phase2/database/UManager.ser");
        FileSerializer<MessageManager> messageManagerFileSerializer = new FileSerializer<>("phase2/database/MSManager.ser");
        FileSerializer<ConferenceManager> conferenceManagerFileSerializer = new FileSerializer<>("phase2/database/CCManager.ser");

        FileSerializer<EventManager> eventManagerFileSerializer1 = new FileSerializer<>("phase2/database/ESManagerCon1.ser");
        FileSerializer<EventManager> eventManagerFileSerializer2 = new FileSerializer<>("phase2/database/ESManagerCon2.ser");
        FileSerializer<EventManager> eventManagerFileSerializer3 = new FileSerializer<>("phase2/database/ESManagerCon3.ser");

        conferenceManagerFileSerializer.saveObject(conferenceManager);
        userManagerFileSerializer.saveObject(userManager);
        eventManagerFileSerializer1.saveObject(eventManager1);
        eventManagerFileSerializer2.saveObject(eventManager2);
        eventManagerFileSerializer3.saveObject(eventManager3);
        messageManagerFileSerializer.saveObject(messageManager);

    }
}
