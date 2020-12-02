package backend.systems.admin;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;


import java.time.LocalDateTime;
import java.util.*;

public class StatisticsCalculator {
    private EventManager eventManager;
    private UserManager userManager;

    public StatisticsCalculator (EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    public int AverageNumberOfAttendees() {
        return 0;
    }

    public List<Map<String, Object>> Top5Events() {
        List<Map<String, Object>> top5 = new ArrayList<>();
        return top5;
    }

    public int TrafficNumber(LocalDateTime StartTime, LocalDateTime EndTime) {
        return 0;
    }

    public List<String> Top5Speaker() {
        List<String> top5 = new ArrayList<>();
        return top5;
    }

}
