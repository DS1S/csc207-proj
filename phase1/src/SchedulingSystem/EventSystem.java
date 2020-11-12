package SchedulingSystem;

import CoreEntities.Users.Perms;
import LoginSystem.UserManager;
import coreUtil.IRunnable;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.*;

import Presenters.EventUI;

import static CoreEntities.Users.Perms.*;

public class EventSystem implements IRunnable {
    private EventManager eventManager;
    private UserManager userManager;
    private EventUI eventUI;

    @Override
    public void run() {
        Perms[] possiblePerms = {canSchedule, canSignUpEvent, canSpeakAtTalk};
        Scanner scanner = new Scanner(System.in);

        if (userManager.loggedInHasPermission(canSchedule)) {
            eventUI.displayScheduleOptions();
            int index = processIndexInput(4);
            switch (index) {
                case(1):
                    eventUI.displayEvents(eventManager.retrieveAllEvents());
                case(2):
                    scheduleEvent();
                case(3):
                    rescheduleEvent();
                case(4):
                    cancelEvent();
            }
        }
        else if (userManager.loggedInHasPermission(canSignUpEvent)) {
            eventUI.displaySignupOptions();
            int index = processIndexInput(3);
            switch (index) {
                case(1):
                    eventUI.displayEvents(eventManager.retrieveAllEvents());
                case(2):
                    SignUpforEvent();
                case(3):
                    eventUI.displayEvents(eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID()));
            }
        }
        else if (userManager.loggedInHasPermission(canSpeakAtTalk)) {
            eventUI.displaySpeakerOptions();
            int index = processIndexInput(2);
            switch (index) {
                case(1):
                    eventUI.displayEvents(eventManager.retrieveAllEvents());
                case(2):
                    eventUI.displayEvents(eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()));
            }
        }
    }

    @Override
    public String toString() {
        return "Events";
    }

    /**
     * Call the EventUI presenter to show message to use whether the use has successfully registered to an event or not
     * Adds the new attendee to the Event iff there are no conflicting problems (Double-Booking or Full).
     *
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the list of the events that the given attendee is signed up for
     * @param title the title of the new Event
     * @param speakerUUID the UUID of the speaker of the new Event
     */

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

    /**
     * Call the EventUI presenter to show message to use whether the use has successfully unregistered
     * to an event or not
     *
     * Adds the new attendee to the Event iff there are no conflicting problems. (Already Cancel)
     *
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the list of the events that the given attendee is signed up for
     * @param title the title of the new Event
     * @param speakerUUID the UUID of the speaker of the new Event
     */

    public void CancelSignUpforEvent(int index, UUID attendee, String title, UUID speakerUUID) {
        if (eventManager.retrieveAttendees(title, speakerUUID).contains(attendee)) {
            eventManager.removeAttendee(attendee, index);
            eventUI.displayCancelSignupSuccess();
        } else {
            eventUI.displayCancelSignUpDouble();
        }
    }

    public void scheduleEvent() {
        Scanner scanner = new Scanner(System.in);
        eventUI.displayScheduleStart();

        eventUI.displayTitlePrompt();
        String title = scanner.nextLine();

        eventUI.displaySpeakerPrompt();
        String username = scanner.nextLine();
        while (!(userManager.containsUserWithUsername(username))) {
            eventUI.displayInvalidSpeaker();
            eventUI.displaySpeakerPrompt();
            username = scanner.nextLine();
        }
        UUID speaker = userManager.getUUIDWithUsername(username);

        eventUI.displayRoomPrompt();
        String room = scanner.nextLine();

        eventUI.displayCapacityPrompt();
        Integer capacity = null;
        while (capacity == null) {
            try {
                capacity = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                eventUI.displayInvalidCapacity();
                eventUI.displayCapacityPrompt();
            }
        }

        LocalTime startTime = processTimeInput();

        int duration = processDurationInput();

        List<Map<String, Object>> eventConflicts = eventManager.scheduleEvent(capacity, room, startTime, title, speaker,
                duration);
        if (eventConflicts.isEmpty()) {
            eventUI.displayScheduleSuccess();
        }
        else {
            eventUI.displayScheduleFailure(eventConflicts);
        }
    }

    public void rescheduleEvent() {
        Scanner scanner = new Scanner(System.in);
        eventUI.displayRescheduleStart();
        List<Map<String, Object>> eventsList = eventManager.retrieveAllEvents();
        eventUI.displayEvents(eventsList);

        int index = processIndexInput(eventsList.size());

        LocalTime startTime = processTimeInput();

        int duration = processDurationInput();

        List<Map<String, Object>> eventConflicts = eventManager.rescheduleEvent(index, startTime, duration);
        if (eventConflicts.isEmpty()) {
            eventUI.displayRescheduleSuccess();
        }
        else {
            eventUI.displayRescheduleFailure(eventConflicts);
        }
    }

    public void cancelEvent() {
        Scanner scanner = new Scanner(System.in);
        eventUI.displayCancelStart();
        List<Map<String, Object>> eventsList = eventManager.retrieveAllEvents();
        eventUI.displayEvents(eventsList);

        int index = processIndexInput(eventsList.size());

        eventManager.cancelEvent(index);

        eventUI.displayCancelSuccess();
    }

    private int processIndexInput(int max) {
        Scanner scanner = new Scanner(System.in);
        eventUI.displayIndexPrompt();
        Integer index = null;
        while (index == null) {
            try {
                index = scanner.nextInt();
                if (index <= 0 || index > max) {
                    index = null;
                    eventUI.displayInvalidIndex();
                    eventUI.displayIndexPrompt();
                }
            }
            catch (InputMismatchException e) {
                eventUI.displayInvalidIndex();
                eventUI.displayIndexPrompt();
            }
        }
        return index;
    }

    private LocalTime processTimeInput() {
        Scanner scanner = new Scanner(System.in);
        eventUI.displayTimePrompt();
        LocalTime startTime = null;
        while (startTime == null) {
            try {
                String[] hourAndMinute = scanner.nextLine().split(":");
                startTime = LocalTime.of(Integer.parseInt(hourAndMinute[0]), Integer.parseInt(hourAndMinute[1]));
            }
            catch (ArrayIndexOutOfBoundsException | NumberFormatException | DateTimeException e) {
                eventUI.displayInvalidTime();
                eventUI.displayTimePrompt();
            }
        }
        return startTime;
    }

    private int processDurationInput() {
        Scanner scanner = new Scanner(System.in);
        eventUI.displayDurationPrompt();
        Integer duration = null;
        while (duration == null) {
            try {
                duration = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                eventUI.displayInvalidDuration();
                eventUI.displayDurationPrompt();
            }
        }
        return duration;
    }

}
