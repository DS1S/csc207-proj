package backend.systems.admin;

import backend.entities.users.Perms;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;

import java.time.LocalDateTime;
import java.util.*;

public class StatisticsCalculator{
    private final EventManager eventManager;
    private final UserManager userManager;

    public StatisticsCalculator (EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    private void parallelSort(List<Integer> arr, List<Object> arr1) {
        int n = arr.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr.get(j) > arr.get(j+1))
                {
                    int temp = arr.get(j);
                    arr.add(j,arr.get(j+1));
                    arr.add(j+1,temp);
                    Object temp1 = arr1.get(j);
                    arr1.add(j,arr1.get(j+1));
                    arr1.add(j+1,temp1);
                }
    }

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

    public List<Map<String, Object>> top5Events() {
        List<Map<String, Object>> top5Events = new ArrayList<>();
        List<Map<String, Object>> topEvents = new ArrayList<>();
        List<Integer> numberofAttendees = new ArrayList<>();
        List<Map<String, Object>> eventsData = eventManager.retrieveAllEvents();
        for (Map<String, Object> eventData : eventsData) {
            numberofAttendees.add((int)eventData.get("Registered"));
            topEvents.add(eventData);
        }
        parallelSort(numberofAttendees, Collections.singletonList(topEvents));
        for (int i = 0; i < Math.min(topEvents.size(), 5); i++) {
            top5Events.add(topEvents.get(i));
        }
        return top5Events;
    }

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
        parallelSort(speakersNumberOfEvents, Collections.singletonList(speakers));
        for (int i = 0; i < Math.min(speakers.size(), 5); i++) {
            top5Speaker.add(speakers.get(i) + " : " + speakersNumberOfEvents.get(i));
        }
        return top5Speaker;
    }

}
