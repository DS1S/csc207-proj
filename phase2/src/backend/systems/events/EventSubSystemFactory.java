package backend.systems.events;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;

/**
 * The class that constructs new event subsystems.
 * We use the factory to further encapsulate what class is being used
 * to implement the type of event systems.
 */
class EventSubSystemFactory {

    /**
     * Constructs a new event subsystem using the given parameters.
     * @param systemName name of the subsystem
     * @param eventManager event manager used by the subsystem
     * @param userManager user manager used by the subsystem
     * @param eventUI the subsystem UI
     * @return the newly constructed event subsystem
     */
    public EventMenuSystem createEventSubSystem(String systemName, EventManager eventManager, UserManager userManager,
                                                EventUI eventUI){
        switch (systemName){
            case "scheduler":
                return new EventSchedulerMenuSystem(eventManager, userManager, eventUI);
            case "signup":
                return new EventSignUpMenuSystem(eventManager, userManager, eventUI);
            case "viewer":
                return new EventViewerMenuSystem(eventManager, userManager, eventUI);
            case "retriever":
                return new ScheduleRetrieverMenuSystem(eventManager, userManager, eventUI);
            default:
                return null;
        }

    }
}
