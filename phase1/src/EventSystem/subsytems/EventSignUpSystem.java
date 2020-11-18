package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Presenters.EventUI;
import coreUtil.InputProcessors.OptionIndexProcessor;
import coreUtil.InputProcessors.IndexProcessor;

import java.util.List;
import java.util.Map;

/**
 * A subsystem of the EventSystem that allows the user to perform actions related to the sign up of Events.
 */
public class EventSignUpSystem extends EventSubSystem {

    /**
     * Constructs a new EventSignUpSystem with the given information.
     * @param eventManager the EventManager that will be used by the EventSignUpSystem
     * @param userManager the UserManager that will be used by the EventSignUpSystem
     * @param eventUI the EventUI that will be used by the EventSignUpSystem
     * @param numOptions the number of menu options given by the EventSignUpSystem
     */
    public EventSignUpSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions){
        super(eventManager, userManager, eventUI, numOptions);
    }

    @Override
    protected void displayOptions() {
        eventUI.displaySignupOptions();
    }

    protected void processMainSignInput(int index){
        switch (index) {
            case (1):
                eventUI.displayEvents(eventManager.retrieveAllEvents());
                break;
            case (2):
                SignUpForEvent();
                break;
            case (3):
                CancelSignUpForEvent();
                break;
            case (4):
                eventUI.displayEvents(eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID()));
                break;
        }
    }

    private void SignUpForEvent() {
        List<Map<String, Object>> eventList = eventManager.retrieveAllEvents();
        int index = processEvents(eventList) - 1;

        if(index != -1){
            if(!eventManager.isEventatCapacity(index)){
                eventManager.registerAttendee(userManager.getLoggedInUserUUID(),index);
                eventUI.displaySignupSuccess();
            }
        }
    }

    private void CancelSignUpForEvent() {
        List<Map<String, Object>> eventList = eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID());
        int index = processEvents(eventList) - 1;

        if (index != -1){
            eventManager.removeAttendee(userManager.getLoggedInUserUUID(), index);
            eventUI.displayCancelSignupSuccess();
        }
    }

    private int processEvents(List<Map<String, Object>> eventsData){
        eventUI.displayEvents(eventsData);
        if(!eventsData.isEmpty()){
            IndexProcessor<Integer> eventProcessor = new OptionIndexProcessor(input, eventsData.size());
            eventUI.displayEnterIndexEvent();
            return eventProcessor.processInput();
        }
        return 0;
    }
}
