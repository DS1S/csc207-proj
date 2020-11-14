package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Presenters.EventUI;
import coreUtil.InputProcessors.EventIndexProcessor;
import coreUtil.InputProcessors.IndexProcessor;

import java.util.List;
import java.util.Map;


public class EventSignUpSystem extends EventSubSystem {

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
        int index = processEvents(eventList);

        if(index != -1){
            if(!eventManager.isEventatCapacity(index)){
                eventManager.registerAttendee(userManager.getLoggedInUserUUID(),index);
                eventUI.displaySignupSuccess();
            }
        }
    }

    private void CancelSignUpForEvent() {
        List<Map<String, Object>> eventList = eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID());
        int index = processEvents(eventList);

        if (index != -1){
            eventManager.removeAttendee(userManager.getLoggedInUserUUID(), index);
            eventUI.displayCancelSignupSuccess();
        }
    }

    private int processEvents(List<Map<String, Object>> eventsData){
        if (eventsData.isEmpty()) return -1;

        IndexProcessor<Integer> eventProcessor = new EventIndexProcessor(input, eventUI, eventsData.size());
        eventUI.displayEvents(eventsData);
        eventUI.displayEnterIndexEvent();
        return eventProcessor.processInput();
    }
}
