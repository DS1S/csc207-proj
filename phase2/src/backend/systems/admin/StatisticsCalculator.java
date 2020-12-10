package backend.systems.admin;

import backend.entities.users.Perms;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The class responsible for generating useful statistics that can be viewed by Admins.
 */
class StatisticsCalculator{
    private final EventManager eventManager;
    private final UserManager userManager;

    /**
     * Constructs a new instance of StatisticsCalculator given an eventManager and a userManager.
     * @param eventManager eventManager used by the StatisticsCalculator
     * @param userManager usermanager used by the StatisticsCalculator
     */
    public StatisticsCalculator (EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    private void parallelSortEvents(List<Integer> arr, List<Map<String, Object>> arr1) {
        int n = arr.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr.get(j) < arr.get(j+1))
                {
                    int temp = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,temp);
                    Map<String, Object> temp1 = arr1.get(j);
                    arr1.set(j,arr1.get(j+1));
                    arr1.set(j+1,temp1);
                }
    }

    /**
     * Gets the average number of all attendees of all events in the eventManager.
     * @return average number of all attendees of all events
     */
    public int getAverageNumberOfAttendees() {
        List<Integer> numberofAttendees = new ArrayList<>();
        List<Map<String, Object>> eventsData = eventManager.retrieveAllEvents();
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
        List<Map<String, Object>> eventsData = eventManager.retrieveAllEvents();
        for (Map<String, Object> eventData : eventsData) {
            numberofAttendees.add((int)eventData.get("Registered"));
            topEvents.add(eventData);
        }
        parallelSortEvents(numberofAttendees, topEvents);
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

    private void parallelSortSpeakers(List<Integer> arr, List<String> arr1) {
        int n = arr.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr.get(j) < arr.get(j+1))
                {
                    int temp = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,temp);
                    String temp1 = arr1.get(j);
                    arr1.set(j,arr1.get(j+1));
                    arr1.set(j+1,temp1);
                }
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
                int numberOfEvents = eventManager.retrieveEventsBySpeaker(UserID).size();
                String speakerName = userManager.getNameWithUUID(UserID);
                speakers.add(speakerName);
                speakersNumberOfEvents.add(numberOfEvents);
            }
        }
        parallelSortSpeakers(speakersNumberOfEvents, speakers);
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
