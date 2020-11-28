package backend.systems.messaging.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;

import java.util.List;

public class MessageSubSystemFactory {

    public MessageMenuSystem createMessageSubSystem(String systemName, UserManager userManager, MessageManager messageManager,
                                                    int numOptions, List<EventManager> eventManagers){
        switch (systemName){
            case "organizer":
                return new OrganizerMessageMenuSystem(userManager, messageManager, numOptions);
            case "regular":
                return new RegularMessageMenuSystem(userManager, messageManager, numOptions);
            case "speaker":
                return new SpeakerMessageMenuSystem(userManager, messageManager, numOptions, eventManagers);
            default:
                return null;
        }
    }
}
