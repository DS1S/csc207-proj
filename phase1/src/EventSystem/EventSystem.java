package EventSystem;

import EventSystem.Managers.EventManager;
import EventSystem.subsytems.EventSignUpSystem;
import EventSystem.subsytems.EventViewerSystem;
import EventSystem.subsytems.ScheduleSystem;
import LoginSystem.UserManager;
import Main.SubSystem;
import coreUtil.IRunnable;

import Presenters.EventUI;

import static CoreEntities.Users.PERMS.*;

/**
 * An EventSystem that allows the user to perform actions related to the sign up and scheduling of Events.
 */
public class EventSystem implements IRunnable {
    private final EventManager eventManager;
    private final UserManager userManager;
    private final EventUI eventUI;

    /**
     * Constructs a new EventSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventSystem.
     * @param userManager The UserManager that will be used by the EventSystem.
     */
    public EventSystem(EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.eventUI = new EventUI(this.userManager);
    }

    /**
     * Runs this System, displaying prompts, UI, errors etc. that allow the user to perform actions related to the
     * sign up and scheduling of Events.
     */
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

        // By Default if user does not have access to effect events, just display to them all events at the conference.
        if (subsystem == null) {
            eventUI.displayEvents(eventManager.retrieveAllEvents());
        }
        else subsystem.run();
    }

    /**
     * An override of the built-in toString method.
     * @return the string "Events"
     */
    @Override
    public String toString() {
        return "Events";
    }

}
