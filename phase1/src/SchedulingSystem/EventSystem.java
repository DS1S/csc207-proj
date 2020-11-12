package SchedulingSystem;

import coreUtil.IRunnable;
import java.util.UUID;
import Presenters.EventUI;

public class EventSystem implements IRunnable {
    private EventManager eventManager;
    private EventUI eventUI;


    @Override
    public void run() {
    }

    @Override
    public String toString() {
        return "Events";
    }

    //signup
    public void SignUpforEvent(int index, UUID attendee, String title, UUID speakerUUID) {
        if (eventManager.isEventatCapacity(index)) {
            eventUI.displaySignUpFull();
        }
        else {
            if (eventManager.retrieveAttendees(title, speakerUUID).contains(attendee)) {
                eventUI.displaySignUpDouble();
            }
            else {
                eventManager.registerAttendee(attendee,index);
                eventUI.displaySignupSuccess();
            }
        }
    }
    //cancel
    public void CancelSignUpforEvent(int index, UUID attendee, String title, UUID speakerUUID) {
        if (eventManager.retrieveAttendees(title, speakerUUID).contains(attendee)) {
            eventManager.removeAttendee(attendee, index);
            eventUI.displayCancelSignupSuccess();
        } else {
            eventUI.displayCancelSignUpDouble();
        }
    }

    //others
}
