package backend.systems.admin;

import backend.systems.MenuSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.StatisticsUI;

import java.time.LocalDateTime;

public class StatisticsSystem extends MenuSystem {
    private final StatisticsUI statisticsUI;
    private final StatisticsCalculator statisticsCalculator;

    public StatisticsSystem(int numOptions, EventManager eventManager, UserManager userManager) {
        super(numOptions);
        this.statisticsUI = new StatisticsUI(userManager);
        this.statisticsCalculator = new StatisticsCalculator(eventManager, userManager);
    }

    protected void displayOptions() { statisticsUI.displayStatisticsOptions(); }

    protected void processInput(int input) {
        switch (input) {
            case(1):
                statisticsUI.displayEventStats(statisticsCalculator.Top5Events(),
                        statisticsCalculator.AverageNumberOfAttendees());
                // Display top 5 most attended events, average number of people participating at events,
                break;
            case(2):
                statisticsUI.displayTrafficStats(statisticsCalculator.TrafficNumber(LocalDateTime.now().minusDays(1),
                        LocalDateTime.now()),statisticsCalculator.TrafficNumber(LocalDateTime.now().minusDays(7),
                        LocalDateTime.now()),statisticsCalculator.TrafficNumber(LocalDateTime.now().minusDays(30),
                        LocalDateTime.now()));
                // Display traffic stats (logged in past day, 7 days, month).
                break;
            case(3):
                statisticsUI.displaySpeakerStats(statisticsCalculator.Top5Speaker());
                // Display the top 5 speakers who spoke at the most events
                break;
        }
    }

    @Override
    public String toString() { return "Statistics"; }
}
