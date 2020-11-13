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

            mainLoop:
            while (true) {
                eventUI.displayScheduleOptions();
                int index = processIndexInput(5);
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
                    case(5):
                        scanner.close();
                        break mainLoop;
                }
            }
        }
        else if (userManager.loggedInHasPermission(canSignUpEvent)) {
            mainLoop:
            while (true) {
                eventUI.displaySignupOptions();
                int index = processIndexInput(5);
                switch (index) {
                    case (1):
                        eventUI.displayEvents(eventManager.retrieveAllEvents());
                    case (2):
                        SignUpforEvent();
                    case (3):
                        CancelSignUpforEvent();
                    case (4):
                        eventUI.displayEvents(eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID()));
                    case (5):
                        scanner.close();
                        break mainLoop;
                }
            }
        }
        else if (userManager.loggedInHasPermission(canSpeakAtTalk)) {
            mainLoop:
            while (true) {
                eventUI.displaySpeakerOptions();
                int index = processIndexInput(3);
                switch (index) {
                    case (1):
                        eventUI.displayEvents(eventManager.retrieveAllEvents());
                    case (2):
                        eventUI.displayEvents(eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()));
                    case(3):
                        scanner.close();
                        break mainLoop;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Events";
    }

    /**
     * Call the EventUI presenter to show message that the user has successfully registered
     * Adds the new attendee to the Event.
     */

    public void SignUpforEvent() {
        List<Map<String, Object>> eventList = eventManager.retrieveSignupAbleEvents(userManager.getLoggedInUserUUID());
        eventUI.displayEvents(eventList);
        eventUI.displayEnterIndexEvent();
        int index = processIndexInput(eventList.size());
        eventManager.registerAttendee(userManager.getLoggedInUserUUID(),index);
        eventUI.displaySignupSuccess();
    }

    /**
     * Call the EventUI presenter to show message that the user has successfully unregistered
     * Cancel the attendee to the registered Event.
     */

    public void CancelSignUpforEvent() {
        List<Map<String, Object>> eventList = eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID());
        eventUI.displayEvents(eventList);
        eventUI.displayEnterIndexEvent();
        int index = processIndexInput(eventList.size());
        eventManager.removeAttendee(userManager.getLoggedInUserUUID(), index);
        eventUI.displayCancelSignupSuccess();
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
        scanner.close();
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
        scanner.close();
    }

    public void cancelEvent() {
        Scanner scanner = new Scanner(System.in);
        eventUI.displayCancelStart();
        List<Map<String, Object>> eventsList = eventManager.retrieveAllEvents();
        eventUI.displayEvents(eventsList);

        int index = processIndexInput(eventsList.size());

        eventManager.cancelEvent(index);

        eventUI.displayCancelSuccess();

        scanner.close();
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
        scanner.close();
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
        scanner.close();
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
        scanner.close();
        return duration;
    }

}
