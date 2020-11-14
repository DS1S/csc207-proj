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

    protected void processMainSignInput(int index){
        switch (index) {
            case (1):
                eventUI.displayEvents(eventManager.retrieveAllEvents());
            case (2):
                SignUpForEvent();
            case (3):
                CancelSignUpForEvent();
            case (4):
                eventUI.displayEvents(eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID()));
        }
    }

    private void SignUpForEvent() {
        int index = processEvents();
        eventManager.registerAttendee(userManager.getLoggedInUserUUID(),index);
        eventUI.displaySignupSuccess();
    }

    private void CancelSignUpForEvent() {
        int index = processEvents();
        eventManager.removeAttendee(userManager.getLoggedInUserUUID(), index);
        eventUI.displayCancelSignupSuccess();
    }

    private int processEvents(){
        List<Map<String, Object>> eventList = eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID());
        IndexProcessor<Integer> eventProcessor = new EventIndexProcessor(input, eventUI, eventList.size());
        eventUI.displayEvents(eventList);
        eventUI.displayEnterIndexEvent();
        return eventProcessor.processInput();
    }
}
