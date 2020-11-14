package EventSystem;

import EventSystem.Managers.EventManager;
import EventSystem.subsytems.EventSignUpSystem;
import EventSystem.subsytems.EventSubSystem;
import EventSystem.subsytems.EventViewerSystem;
import EventSystem.subsytems.ScheduleSystem;
import LoginSystem.UserManager;
import coreUtil.IRunnable;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.*;

import Presenters.EventUI;

import static CoreEntities.Users.Perms.*;

public class EventSystem implements IRunnable {
    private EventManager eventManager;
    private UserManager userManager;
    private EventUI eventUI;

    @Override
    public void run() {
        if (userManager.loggedInHasPermission(canSchedule)) {
            EventSubSystem schedulingSystem = new ScheduleSystem(eventManager, userManager, eventUI, 5);
            schedulingSystem.run();
        }
        else if (userManager.loggedInHasPermission(canSignUpEvent)) {
            EventSubSystem eventSignUpSystem = new EventSignUpSystem(eventManager, userManager, eventUI, 5);
            eventSignUpSystem.run();
        }
        else if (userManager.loggedInHasPermission(canSpeakAtTalk)) {
            EventSubSystem eventViewerSystem = new EventViewerSystem(eventManager, userManager, eventUI, 3);
            eventViewerSystem.run();
        }
    }

    @Override
    public String toString() {
        return "Events";
    }

}
