package backend.systems.messaging.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;

public class MessageSubSystemFactory {

    public MessageSubSystem createMessageSubSystem(String systemName, UserManager userManager, MessageManager messageManager,
                                                   int numOptions, EventManager eventManager){
        switch (systemName){
            case "organizer":
                return new OrganizerMessageSubSystem(userManager, messageManager, numOptions);
            case "regular":
                return new RegularMessageSubSystem(userManager, messageManager, numOptions);
            case "speaker":
                return new SpeakerMessageSubSystem(userManager, messageManager, numOptions, eventManager);
            default:
                return null;
        }
    }
}
