package backend.systems.admin;

import backend.entities.users.Perms;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import utility.ParallelSorter;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The class responsible for generating useful statistics that can be viewed by Admins.
 */
class StatisticsCalculator{
    private final List<EventManager> eventManagers;
    private final UserManager userManager;
    private final ParallelSorter parallelSorter;

    /**
     * Constructs a new instance of StatisticsCalculator given an eventManager and a userManager.
     * @param eventManagers eventManagers used by the StatisticsCalculator
     * @param userManager usermanager used by the StatisticsCalculator
     */
    public StatisticsCalculator (List<EventManager> eventManagers, UserManager userManager) {
        this.eventManagers = eventManagers;
        this.userManager = userManager;
        this.parallelSorter = new ParallelSorter();
    }


    /**
     * Gets the average number of all attendees of all events in the eventManager.
     * @return average number of all attendees of all events
     */
    public int getAverageNumberOfAttendees() {
        List<Integer> numberofAttendees = new ArrayList<>();
        List<Map<String, Object>> eventsData = new ArrayList<>();
        for (EventManager eventManager: eventManagers) {
            eventsData.addAll(eventManager.retrieveAllEvents());
        }
        for (Map<String, Object> eventData : eventsData) {
            numberofAttendees.add((int) eventData.get("Registered"));
        }
        if(numberofAttendees.size() != 0)
            return numberofAttendees.stream().mapToInt(Integer::intValue).sum() / numberofAttendees.size();
        return 0;
    }

    /**
     * Gets the top five events in the eventManager based on number of attendees.
     * @return a list of the names of the five most popular events
     */
    public List<Map<String, Object>> top5Events() {
        List<Map<String, Object>> top5Events = new ArrayList<>();
        List<Map<String, Object>> topEvents = new ArrayList<>();
        List<Integer> numberofAttendees = new ArrayList<>();
        List<Map<String, Object>> eventsData = new ArrayList<>();
        for (EventManager eventManager: eventManagers) {
            eventsData.addAll(eventManager.retrieveAllEvents());
        }
        for (Map<String, Object> eventData : eventsData) {
            numberofAttendees.add((int)eventData.get("Registered"));
            topEvents.add(eventData);
        }
        parallelSorter.parallelSortListMap(numberofAttendees, topEvents);
        for (int i = 0; i < Math.min(topEvents.size(), 5); i++) {
            top5Events.add(topEvents.get(i));
        }
        return top5Events;
    }

    /**
     * Gets the number of users that log into the system over a certain time interval.
     * @param StartTime starting time of the interval
     * @param EndTime ending time of the interval
     * @return number of users that log in at any time over the specified interval
     */
    public int getUserTrafficNumber(LocalDateTime StartTime, LocalDateTime EndTime) {
        int trafficCount = 0;
        List<UUID> user_list = userManager.getUUIDs();
        for (UUID userID : user_list) {
            if (userManager.checkUserLoggedIn(userID, StartTime, EndTime)) {
                trafficCount += 1;
            }
        }
        return trafficCount;
    }

    /**
     * Gets the top five speakers ranked by the number of events spoken at.
     * @return a list of the names of the top five speakers based on the number of events they
     * have spoken at
     */
    public List<String> top5Speaker() {
        List<String> top5Speaker = new ArrayList<>();
        List<String> speakers = new ArrayList<>();
        List<Integer> speakersNumberOfEvents = new ArrayList<>();
        List<UUID> userList = userManager.getUUIDs();
        for (UUID UserID : userList) {
            if (userManager.hasPermission(UserID, Perms.CAN_SPEAK_AT_TALK)) {
                int numberOfEvents = 0;
                for (EventManager eventManager: eventManagers) {
                    numberOfEvents += eventManager.retrieveEventsBySpeaker(UserID).size();
                }
                String speakerName = userManager.getNameWithUUID(UserID);
                speakers.add(speakerName);
                speakersNumberOfEvents.add(numberOfEvents);
            }
        }
        parallelSorter.parallelSortListString(speakersNumberOfEvents, speakers);
        for (int i = 0; i < Math.min(speakers.size(), 5); i++) {
            if (speakersNumberOfEvents.get(i) == 1) {
                top5Speaker.add(speakers.get(i) + " : " + speakersNumberOfEvents.get(i) + " event");
            }
            else {
                top5Speaker.add(speakers.get(i) + " : " + speakersNumberOfEvents.get(i) + " events");
            }

        }
        return top5Speaker;
    }

}
