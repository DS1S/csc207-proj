package backend.systems.events.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;

public class EventSubSystemFactory {

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
