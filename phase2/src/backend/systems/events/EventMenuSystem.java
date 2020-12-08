package backend.systems.events;

import backend.systems.MenuSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;
import utility.inputprocessors.InputProcessor;
import utility.inputprocessors.OptionInputProcessor;


import java.util.List;
import java.util.Map;

/**
 * An abstract subsystem of EventSystem that allows the user to perform a particular action related to the sign up
 * of Events.
 */
public abstract class EventMenuSystem extends MenuSystem {
    protected final EventManager eventManager;
    protected final UserManager userManager;
    protected final EventUI eventUI;
    protected final int numOptions;

    /**
     * Constructs a new EventMenuSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventMenuSystem.
     * @param userManager The UserManager that will be used by the EventMenuSystem.
     * @param eventUI The EventUI that will be used by the EventMenuSystem.
     * @param numOptions The number of menu options given by the EventMenuSystem.
     */
    public EventMenuSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions) {
        super(numOptions);
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.eventUI = eventUI;
        this.numOptions = numOptions;
    }

    /**
     * Displays the given events using eventsData and takes in an integer input from the user.
     * @param eventsData The data of the events to be displayed.
     * @return A valid input from the user if there are events in eventsData. 0 otherwise.
     */
    protected int processEvents(List<Map<String, Object>> eventsData) {
        eventUI.displayEvents(eventsData);
        if (!eventsData.isEmpty()) {
            InputProcessor<Integer> eventProcessor = new OptionInputProcessor(input, eventsData.size());
            eventUI.displayEnterIndexEvent();
            return eventProcessor.processInput();
        }
        return 0;
    }
}
