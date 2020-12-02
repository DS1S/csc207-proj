package backend.systems.admin;

import backend.systems.MenuSystem;
import backend.systems.admin.StatisticsCalculator;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.StatisticsUI;

import java.time.LocalDateTime;

public class StatisticsSystem extends MenuSystem {
    private EventManager eventManager;
    private UserManager userManager;
    private StatisticsUI statisticsUI;
    private StatisticsCalculator statisticsCalculator;

    public StatisticsSystem(int numOptions, EventManager eventManager, UserManager userManager, StatisticsCalculator statisticsCalculator) {
        super(numOptions);
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.statisticsUI = new StatisticsUI(userManager);
        this.statisticsCalculator = statisticsCalculator;
    }

    protected void displayOptions() { statisticsUI.displayStatisticsOptions(); }

    protected void processInput(int input) {
        switch (input) {
            case(1):
                statisticsUI.displayEventStats(statisticsCalculator.Top5Events(), statisticsCalculator.AverageNumberOfAttendees());
                // Display top 5 most attended events, average number of people participating at events,
                break;
            case(2):
                statisticsUI.displayTrafficStats(statisticsCalculator.TrafficNumber(LocalDateTime.now().minusDays(1),LocalDateTime.now()),statisticsCalculator.TrafficNumber(LocalDateTime.now().minusDays(7),LocalDateTime.now()),statisticsCalculator.TrafficNumber(LocalDateTime.now().minusDays(30),LocalDateTime.now()));
                // Display traffic stats (logged in past day, 7 days, month).
                break;
            case(3):
                statisticsUI.displaySpeakerStats(statisticsCalculator.Top5Speaker());
                // Display the top 5 speakers who spoke at the most events
                break;
            case(4):
                displayOptions();
                break;
        }
    }

    //TODO: functions here to calculate stats, pass ints to UI
    //TODO: functions will be called from StatisticsCalculator use case class.
    //Daily Logins
    //Weekly Logins
    //Monthly Logins
    //topspeaker (5 array)
    //topevent (5 array)

    @Override
    public String toString() { return "Statistics"; }
}
