package backend.systems.admin;

import backend.systems.MenuSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.StatisticsUI;

import java.time.LocalDateTime;

/**
 * An extension of MenuSystem that displays an Admin options to view certain statistics and processes
 * their input.
 */
public class StatisticsSystem extends MenuSystem {
    private final StatisticsUI statisticsUI;
    private final StatisticsCalculator statisticsCalculator;

    /**
     * Constructs a new instance of StatisticsSystem given an eventManager and a userManager.
     * @param eventManager the event manager used by the system
     * @param userManager the user manager used by the system
     */
    public StatisticsSystem(EventManager eventManager, UserManager userManager) {
        super(4);
        this.statisticsUI = new StatisticsUI(userManager);
        this.statisticsCalculator = new StatisticsCalculator(eventManager, userManager);
    }

    /**
     * Displays options for viewing certain statistics.
     */
    protected void displayOptions() { statisticsUI.displayStatisticsOptions(); }

    /**
     * Processes the Admin's input by calling StatisticsCalculator to generate the desired statistics
     * and statisticsUI to display these statistics.
     * @param input the Admin's input
     */
    protected void processInput(int input) {
        switch (input) {
            case(1):
                statisticsUI.displayEventStats(statisticsCalculator.top5Events(),
                        statisticsCalculator.getAverageNumberOfAttendees());
                // Display top 5 most attended events, average number of people participating at events,
                break;
            case(2):
                statisticsUI.displayTrafficStats(statisticsCalculator.getUserTrafficNumber(LocalDateTime.now().minusDays(1),
                        LocalDateTime.now()),statisticsCalculator.getUserTrafficNumber(LocalDateTime.now().minusDays(7),
                        LocalDateTime.now()),statisticsCalculator.getUserTrafficNumber(LocalDateTime.now().minusDays(30),
                        LocalDateTime.now()));
                // Display traffic stats (logged in past day, 7 days, month).
                break;
            case(3):
                statisticsUI.displaySpeakerStats(statisticsCalculator.top5Speaker());
                // Display the top 5 speakers who spoke at the most events
                break;
        }
    }

    /**
     * Overrides the built-in toString method.
     * @return a set of desired statistics in string format
     */
    @Override
    public String toString() { return "Statistics"; }
}
