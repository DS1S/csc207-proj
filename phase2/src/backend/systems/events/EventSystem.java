package backend.systems.events;

import backend.systems.events.subsystems.EventSubSystem;
import backend.systems.events.subsystems.EventSubSytemFactory;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import utility.IRunnable;

import frontend.EventUI;

import backend.entities.users.PERMS;

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
        // TODO: Change it so EventSystem is actually running subsystems in parallel; otherwise it is an over glorified facade at this point.
        EventSubSystem subsystem = null;
        EventSubSytemFactory subSystemFactory = new EventSubSytemFactory();
        if (userManager.loggedInHasPermission(PERMS.canSchedule)) {
            subsystem = subSystemFactory.createEventSubSystem("scheduler", eventManager, userManager,
                    eventUI, 5);
        }
        else if (userManager.loggedInHasPermission(PERMS.canSignUpEvent)) {
            subsystem = subSystemFactory.createEventSubSystem("signup", eventManager, userManager,
                    eventUI, 5);
        }
        else if (userManager.loggedInHasPermission(PERMS.canSpeakAtTalk)) {
            subsystem = subSystemFactory.createEventSubSystem("viewer", eventManager,
                    userManager, eventUI, 3);
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
