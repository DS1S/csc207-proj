package backend.systems.messaging.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.UserLinkUI;
import utility.RunnableSystem;
import utility.Web.WebOpener;
import utility.Web.WebValidator;

import java.util.List;

public class MessageSubSystemFactory {

    public RunnableSystem createMessageSubSystem(String systemName, UserManager userManager, MessageManager messageManager,
                                                 int numOptions, List<EventManager> eventManagers){
        switch (systemName){
            case "organizer":
                return new OrganizerMessageMenuSystem(userManager, messageManager, numOptions);
            case "regular":
                return new RegularMessageMenuSystem(userManager, messageManager, numOptions);
            case "speaker":
                return new SpeakerMessageMenuSystem(userManager, messageManager, numOptions, eventManagers);
            case "linker":
                return new UserLinkSystem(new WebValidator(), new WebOpener(), userManager, numOptions);
            default:
                return null;
        }
    }
}
