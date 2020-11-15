package EventSystem;

import CoreEntities.Event;
import EventSystem.Managers.EventManager;
import EventSystem.subsytems.EventSignUpSystem;
import EventSystem.subsytems.EventSubSystem;
import EventSystem.subsytems.EventViewerSystem;
import EventSystem.subsytems.ScheduleSystem;
import LoginSystem.UserManager;
import Main.SubSystem;
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

    public EventSystem(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.eventUI = new EventUI(this.userManager);
    }

    @Override
    public void run() {
        SubSystem subsystem = null;
        if (userManager.loggedInHasPermission(canSchedule)) {
            subsystem = new ScheduleSystem(eventManager, userManager, eventUI, 5);
        }
        else if (userManager.loggedInHasPermission(canSignUpEvent)) {
            subsystem = new EventSignUpSystem(eventManager, userManager, eventUI, 5);
        }
        else if (userManager.loggedInHasPermission(canSpeakAtTalk)) {
            subsystem = new EventViewerSystem(eventManager, userManager, eventUI, 3);
        }
        subsystem.run();
    }

    @Override
    public String toString() {
        return "Events";
    }

}
