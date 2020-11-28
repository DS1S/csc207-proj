package frontend;

import backend.systems.usermangement.managers.UserManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StatisticsUI {
    private UserManager userManager;

    public StatisticsUI(UserManager userManager) { this.userManager = userManager; }

    public void displayEventStats(List<Map<String, Object>> eventList, int average) {
        System.out.println("--------------Top 5 most attended Events--------------");
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map<String, Object> data: eventList) {
            sb.append("#" + i + ":");
            sb.append(data.get("Title") + "\n");
            sb.append("Hosted by: " + userManager.getNameWithUUID((UUID)data.get("Speaker")) + "\n");
            sb.append(data.get("Registered") + " people registered + \n");
            System.out.print(sb);
            sb.setLength(0);
            i += 1;
        }

        if (average >= 0) {
            System.out.println("An average of " + average + " people attend any event.");
        }
        else { System.out.println("No events have taken place!"); }
    }

    public void displayTrafficStats(int dailyLogins, int weeklyLogins, int monthlyLogins) {
        System.out.println("\nUsers that have been logged in:");
        System.out.println("Today: " + dailyLogins);
        System.out.println("This week: " + weeklyLogins);
        System.out.println("This month: " + monthlyLogins);
    }

    public void displaySpeakerStats(List<String> speakerNames) {
        int i = 1;

        System.out.println("--------------------Top 5 Speakers--------------------");
        for (String speaker: speakerNames) {
            System.out.println("#" + i + ": " + speaker);
        }
    }

    public void displayStatisticsOptions() {
        System.out.println("\nWhat statistics would you like to see?");
        System.out.println("1. Display event statistics");
        System.out.println("2. Display app traffic statistics");
        System.out.println("3. Display speaker statistics");
        System.out.println("4. Return to main menu");
    }
}
