package backend.systems.messaging.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.UserLinkUI;
import utility.RunnableSystem;
import utility.Web.WebOpener;
import utility.Web.WebValidator;

import java.util.List;

/**
 * The class that constructs new message subsystems.
 */
public class MessageSubSystemFactory {

    /**
     * Creates a new message subsystem using the given parameters.
     * @param systemName the name of the subsystem
     * @param userManager the user manager used by the subsystem
     * @param messageManager the message manager used by the subsystem
     * @param numOptions the number of options displayed to the user based on the subsystem
     * @param eventManagers the event managers used by the subsystem
     * @return a newly constructed message subsystem
     */
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
