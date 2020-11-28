package backend.systems.events.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;

public class EventSubSytemFactory {

    public EventSubSystem createEventSubSystem(String systemName, EventManager eventManager, UserManager userManager,
                                EventUI eventUI, int numOptions){
        switch (systemName){
            case "scheduler":
                return new EventSchedulerSubSystem(eventManager, userManager, eventUI, numOptions);
            case "signup":
                return new EventSignUpSubSystem(eventManager, userManager, eventUI, numOptions);
            case "viewer":
                return new EventViewerSubSystem(eventManager, userManager, eventUI, numOptions);
            default:
                return null;
        }

    }
}
