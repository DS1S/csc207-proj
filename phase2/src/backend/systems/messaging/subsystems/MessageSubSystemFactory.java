package backend.systems.messaging.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;

import java.util.List;

public class MessageSubSystemFactory {

    public MessageSubSystem createMessageSubSystem(String systemName, UserManager userManager, MessageManager messageManager,
                                                   int numOptions, List<EventManager> eventManagers){
        switch (systemName){
            case "organizer":
                return new OrganizerMessageSubSystem(userManager, messageManager, numOptions);
            case "regular":
                return new RegularMessageSubSystem(userManager, messageManager, numOptions);
            case "speaker":
                return new SpeakerMessageSubSystem(userManager, messageManager, numOptions, eventManagers);
            default:
                return null;
        }
    }
}
