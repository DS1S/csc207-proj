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
    public void SignUpforEvent(int index, UUID attendee) {
        if (eventManager.isEventatCapacity(index)) {
            eventUI.displaySignUpFull();
        }
        else {
            if (attendee in eventManager)
        }
    }
    //cancel
    //others
}
