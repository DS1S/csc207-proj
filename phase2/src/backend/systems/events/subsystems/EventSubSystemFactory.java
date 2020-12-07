package backend.systems.events.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;

/**
 * The class that constructs new event subsystems.
 */
public class EventSubSystemFactory {

    /**
     * Constructs a new event subsystem using the given parameters.
     * @param systemName name of the subsystem
     * @param eventManager event manager used by the subsystem
     * @param userManager user manager used by the subsystem
     * @param eventUI the subsystem UI
     * @param numOptions the number of options available on the UI offered by the subsystem
     * @return the newly constructed event subsystem
     */
    public EventMenuSystem createEventSubSystem(String systemName, EventManager eventManager, UserManager userManager,
                                                EventUI eventUI, int numOptions){
        switch (systemName){
            case "scheduler":
                return new EventSchedulerMenuSystem(eventManager, userManager, eventUI, numOptions);
            case "signup":
                return new EventSignUpMenuSystem(eventManager, userManager, eventUI, numOptions);
            case "viewer":
                return new EventViewerMenuSystem(eventManager, userManager, eventUI, numOptions);
            case "retriever":
                return new EventCategoryRetrieverMenuSystem(eventManager, userManager, eventUI, numOptions);
            default:
                return null;
        }

    }
}
