package frontend;

import backend.systems.usermangement.managers.UserManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class that displays prompts and messages for statistics related actions.
 */
public class StatisticsUI {
    private UserManager userManager;

    /**
     * Constructs a new StatisticsUI that uses the given user manager.
     * @param userManager The user manager used by the EventUI.
     */
    public StatisticsUI(UserManager userManager) { this.userManager = userManager; }

    /**
     * Displays the available statistics related to events.
     * @param eventList The list of extracted event data for the top 5 most attended events
     * @param average The average number of attendees per event
     */
    public void displayEventStats(List<Map<String, Object>> eventList, int average) {
        System.out.println("--------------Top 5 most attended Events--------------");
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map<String, Object> data: eventList) {
            sb.append("#" + i + ": ");
            formatEventData(sb, data);
            System.out.print(sb);
            sb.setLength(0);
            i += 1;
        }

        if (average >= 0) {
            System.out.println("An average of " + average + " people attend any event.");
        }
        else { System.out.println("No events have taken place!"); }
    }

    private void formatEventData(StringBuilder sb, Map<String, Object> data) {
        sb.append("\"" + data.get("Title") + "\"" + "\n");
        List<?> speakers = (List<?>) data.get("Speaker");
        if (!(speakers.isEmpty())){
            sb.append("Hosted by: ");
            for (Object speakerUUID: speakers) {
                sb.append(userManager.getNameWithUUID((UUID)speakerUUID) + ", ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append("\n");
        }
        sb.append(data.get("Registered") + "/" + data.get("Capacity") + " spots filled" + "\n");
    }

    /**
     * Displays the available statistics related to app traffic and use activity.
     * @param dailyLogins the number of logins for the day
     * @param weeklyLogins the number of logins for the week
     * @param monthlyLogins the number of logins for the month
     */
    public void displayTrafficStats(int dailyLogins, int weeklyLogins, int monthlyLogins) {
        System.out.println("\nUsers that have been logged in:");
        System.out.println("Today: " + dailyLogins);
        System.out.println("This week: " + weeklyLogins);
        System.out.println("This month: " + monthlyLogins);
    }

    /**
     * Displays the available statistics related to speakers.
     * @param speakerNames The list of names of the top 5 speakers
     */
    public void displaySpeakerStats(List<String> speakerNames) {
        int i = 1;

        System.out.println("--------------------Top 5 Speakers--------------------");
        for (String speaker: speakerNames) {
            System.out.println("#" + i + ": " + speaker);
            i += 1;
        }
    }

    /**
     * Displays a list of options pertaining to the viewing of statistics.
     */
    public void displayStatisticsOptions() {
        System.out.println("\nWhat statistics would you like to see?");
        System.out.println("1. Display event statistics");
        System.out.println("2. Display app traffic statistics");
        System.out.println("3. Display speaker statistics");
        System.out.println("4. Return to main menu");
    }
}
