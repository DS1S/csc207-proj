package backend.systems.social;

import backend.systems.events.managers.EventManager;
import backend.systems.social.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;
import utility.RunnableSystem;
import utility.Web.WebOpener;
import utility.Web.WebValidator;

import java.util.List;

/**
 * The class that constructs new message subsystems and a linker.
 * We use this factory to further encapsulate what class is used to implement
 * the specified system.
 */
class SocialSubSystemsFactory {

    /**
     * Creates a new message subsystem using the given parameters.
     * @param systemName the name of the subsystem
     * @param userManager the user manager used by the subsystem
     * @param messageManager the message manager used by the subsystem
     * @param eventManagers the event managers used by the subsystem
     * @return a newly constructed message subsystem
     */
    public RunnableSystem createMessageSubSystem(String systemName, UserManager userManager, MessageManager messageManager,
                                                 List<EventManager> eventManagers){
        switch (systemName){
            case "organizer":
                return new OrganizerMessageMenuSystem(userManager, messageManager);
            case "regular":
                return new RegularMessageMenuSystem(userManager, messageManager);
            case "speaker":
                return new SpeakerMessageMenuSystem(userManager, messageManager, eventManagers);
            case "linker":
                return new UserLinkSystem(new WebValidator(), new WebOpener(), userManager);
            default:
                return null;
        }
    }
}
