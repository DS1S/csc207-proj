package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Presenters.EventUI;
import coreUtil.InputProcessors.*;

import java.time.LocalTime;
import java.util.*;

/**
 * A subsystem of the EventSystem that allows the user to perform actions related to the scheduling of Events.
 */
public class ScheduleSystem extends EventSubSystem{

    /**
     * Constructs a new ScheduleSystem with the given information.
     * @param eventManager the EventManager that will be used by the ScheduleSystem
     * @param userManager the UserManager that will be used by the ScheduleSystem
     * @param eventUI the EventUI that will be used by the ScheduleSystem
     * @param numOptions the number of menu options given by the ScheduleSystem
     */
    public ScheduleSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions) {
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
        IndexProcessor<Integer> capacityProcessor = new CapacityIndexProcessor(input, eventUI);

        eventUI.displayScheduleStart();

        eventUI.displayTitlePrompt();
        String title = askForString("Title");

        UUID speaker = processInputSpeaker();

        eventUI.displayRoomPrompt();
        String room = askForString("Room");

        int capacity = capacityProcessor.processInput();

        LocalTime startTime = timeProcessor.processInput();

        int duration = durationProcessor.processInput();

        attemptScheduling(capacity, room, startTime, title, speaker, duration);
    }

    private UUID processInputSpeaker() {
        eventUI.displaySpeakerPrompt();
        String username = askForString("Speaker");
        while (!(userManager.containsUserWithUsername(username))) {
            eventUI.displayInvalidSpeaker();
            eventUI.displaySpeakerPrompt();
            username = input.nextLine();
        }
        return userManager.getUUIDWithUsername(username);
    }

    private void attemptScheduling(int capacity, String room, LocalTime startTime, String title, UUID speaker,
                                   int duration) {
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
        eventUI.displayRescheduleStart();
        int index = setupEventList() - 1;

        if(index != -1) {
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

        if(index != -1) {
            eventUI.displayCancelStart();
            eventManager.cancelEvent(index);
            eventUI.displayCancelSuccess();
        }
    }

    private int setupEventList() {
        List<Map<String, Object>> eventsList = eventManager.retrieveAllEvents();
        eventUI.displayEvents(eventsList);
        IndexProcessor<Integer> eventProcessor = new OptionIndexProcessor(input, eventsList.size());
        if(!eventsList.isEmpty()) {
            eventUI.displayEnterIndexEvent();
            return eventProcessor.processInput();
        }
        return 0;
    }
}
