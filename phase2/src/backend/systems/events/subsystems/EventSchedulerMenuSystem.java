package backend.systems.events.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;
import utility.inputprocessors.CapacityIndexProcessor;
import utility.inputprocessors.DurationIndexProcessor;
import utility.inputprocessors.IndexProcessor;
import utility.inputprocessors.TimeIndexProcessor;

import java.time.LocalTime;
import java.util.*;

import static backend.entities.users.PERMS.canSpeakAtTalk;

/**
 * A subsystem of the EventSystem that allows the user to perform actions related to the scheduling of Events.
 */
class EventSchedulerMenuSystem extends EventMenuSystem {
    private List<Map<String, Object>> eventsData;

    /**
     * Constructs a new ScheduleSystem with the given information.
     * @param eventManager The EventManager that will be used by the ScheduleSystem.
     * @param userManager The UserManager that will be used by the ScheduleSystem.
     * @param eventUI The EventUI that will be used by the ScheduleSystem.
     * @param numOptions The number of menu options given by the ScheduleSystem.
     */
    public EventSchedulerMenuSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions) {
        super(eventManager, userManager, eventUI, numOptions);
        this.eventsData = eventManager.retrieveAllEvents();
    }

    /**
     * Tells the event UI to display event options for organizers.
     */
    @Override
    protected void displayOptions() {
        eventUI.displayScheduleOptions();
    }

    /**
     * Processes an integer input in the event viewing page.
     * @param index The input to be processed. 1 allows for viewing of all events. 2 allows for
     *              scheduling of events. 3 allows for rescheduling events. 4 allows for cancellation
     *              of events.
     */
    @Override
    protected void processInput(int index) {
        switch (index) {
            case (1):
                eventUI.displayEvents(eventsData);
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

        List<UUID> speakers = processInputSpeakers();

        eventUI.displayRoomPrompt();
        String room = askForString("Room");

        int capacity = capacityProcessor.processInput();

        eventUI.displayStartTimePrompt();
        LocalTime startTime = timeProcessor.processInput();

        int duration = durationProcessor.processInput();

        attemptScheduling(capacity, room, startTime, title, speakers, duration);
    }

    private List<UUID> processInputSpeakers() {
        List<UUID> speakers = new ArrayList<>();
        eventUI.displayFirstSpeakerPrompt();
        if (askForBoolean()) {
            speakers.add(processSingleInputSpeaker());
            eventUI.displayAnotherSpeakerPrompt();
            while (askForBoolean()) {
                UUID speaker = processSingleInputSpeaker();
                if (!(speakers.contains(speaker))) {
                    speakers.add(speaker);
                }
                else {
                    eventUI.displayRepeatSpeakerError();
                }
                eventUI.displayAnotherSpeakerPrompt();
            }
        }
        return speakers;
    }

    private UUID processSingleInputSpeaker() {
        eventUI.displaySpeakerPrompt();
        String username = askForString("Speaker");
        while (!(userManager.containsUserWithUsername(username)) ||
                !(userManager.hasPermission(userManager.getUUIDWithUsername(username), canSpeakAtTalk))) {
            eventUI.displayInvalidSpeaker();
            eventUI.displaySpeakerPrompt();
            username = input.nextLine();
        }
        return userManager.getUUIDWithUsername(username);
    }

    private void attemptScheduling(int capacity, String room, LocalTime startTime, String title, List<UUID> speakers,
                                   int duration) {
        List<Map<String, Object>> eventConflicts = eventManager.scheduleEvent(capacity, room, startTime, title, speakers,
                duration);
        if (eventConflicts.isEmpty()) {
            eventsData = eventManager.retrieveAllEvents();
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
        int index = processEvents(eventsData) - 1;

        if(index != -1) {
            eventUI.displayStartTimePrompt();
            LocalTime startTime = timeProcessor.processInput();

            int duration = durationProcessor.processInput();

            List<Map<String, Object>> eventConflicts = eventManager.rescheduleEvent(index, startTime, duration);
            if (eventConflicts.isEmpty()) {
                eventsData = eventManager.retrieveAllEvents();
                eventUI.displayRescheduleSuccess();
            }
            else {
                eventUI.displayRescheduleFailure(eventConflicts);
            }
        }
    }

    private void cancelEvent() {
        int index = processEvents(eventsData) - 1;

        if(index != -1) {
            eventUI.displayCancelStart();
            eventManager.cancelEvent(index);
            eventsData = eventManager.retrieveAllEvents();
            eventUI.displayCancelSuccess();
        }
    }
}
