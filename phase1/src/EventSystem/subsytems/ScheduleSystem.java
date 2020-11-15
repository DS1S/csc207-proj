package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Presenters.EventUI;
import coreUtil.InputProcessors.DurationIndexProcessor;
import coreUtil.InputProcessors.OptionIndexProcessor;
import coreUtil.InputProcessors.IndexProcessor;
import coreUtil.InputProcessors.TimeIndexProcessor;

import java.time.LocalTime;
import java.util.*;

public class ScheduleSystem extends EventSubSystem{

    public ScheduleSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions){
        super(eventManager, userManager, eventUI, numOptions);
    }

    @Override
    protected void displayOptions() {
        eventUI.displayScheduleOptions();
    }

    @Override
    protected void processMainSignInput(int index) {
        switch (index) {
            case (1):
                eventUI.displayEvents(eventManager.retrieveAllEvents());
                break;
            case (2):
                scheduleEvent();
                break;
            case (3):
                rescheduleEvent();
                break;
            case (4):
                cancelEvent();
                break;
        }
    }

    private void scheduleEvent() {
        IndexProcessor<LocalTime> timeProcessor = new TimeIndexProcessor(input, eventUI);
        IndexProcessor<Integer> durationProcessor = new DurationIndexProcessor(input, eventUI);

        eventUI.displayScheduleStart();

        eventUI.displayTitlePrompt();
        String title = askForString("Title");

        eventUI.displaySpeakerPrompt();
        String username = askForString("Speaker");
        while (!(userManager.containsUserWithUsername(username))) {
            eventUI.displayInvalidSpeaker();
            eventUI.displaySpeakerPrompt();
            username = input.nextLine();
        }
        UUID speaker = userManager.getUUIDWithUsername(username);

        eventUI.displayRoomPrompt();
        String room = askForString("Room");

        eventUI.displayCapacityPrompt();
        Integer capacity = null;
        while (capacity == null || capacity <= 0) {
            try {
                capacity = input.nextInt();
                if (capacity <= 0) eventUI.displayInvalidCapacity();
            }
            catch (InputMismatchException e) {
                eventUI.displayInvalidCapacity();
                eventUI.displayCapacityPrompt();
                input.nextLine();
            }
        }
        input.nextLine();

        LocalTime startTime = timeProcessor.processInput();

        int duration = durationProcessor.processInput();

        List<Map<String, Object>> eventConflicts = eventManager.scheduleEvent(capacity, room, startTime, title, speaker,
                duration);
        if (eventConflicts.isEmpty()) {
            eventUI.displayScheduleSuccess();
        }
        else {
            eventUI.displayScheduleFailure(eventConflicts);
        }

    }

    private void rescheduleEvent() {

        IndexProcessor<LocalTime> timeProcessor = new TimeIndexProcessor(input, eventUI);
        IndexProcessor<Integer> durationProcessor = new DurationIndexProcessor(input, eventUI);
        int index = setupEventList() - 1;

        if(index != -1){
            LocalTime startTime = timeProcessor.processInput();

            int duration = durationProcessor.processInput();

            List<Map<String, Object>> eventConflicts = eventManager.rescheduleEvent(index, startTime, duration);
            if (eventConflicts.isEmpty()) {
                eventUI.displayRescheduleSuccess();
            }
            else {
                eventUI.displayRescheduleFailure(eventConflicts);
            }
        }
    }

    private void cancelEvent() {
        int index = setupEventList() - 1;

        if(index != -1){
            eventManager.cancelEvent(index);
            eventUI.displayCancelSuccess();
        }
    }

    private int setupEventList(){
        List<Map<String, Object>> eventsList = eventManager.retrieveAllEvents();
        eventUI.displayEvents(eventsList);
        IndexProcessor<Integer> eventProcessor = new OptionIndexProcessor(input, eventsList.size());
        if(!eventsList.isEmpty()){
            eventUI.displayCancelStart();
            return eventProcessor.processInput();
        }
        return 0;
    }
}
