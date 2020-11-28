package backend.systems.admin;

import backend.systems.SubSystem;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.StatisticsUI;
import utility.inputprocessors.OptionIndexProcessor;

import java.util.Scanner;

public class StatisticsSystem extends SubSystem {
    private EventManager eventManager;
    private UserManager userManager;
    private StatisticsUI statisticsUI;

    public StatisticsSystem(int numOptions, EventManager eventManager, UserManager userManager) {
        super(numOptions, new OptionIndexProcessor(new Scanner(System.in), numOptions));
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.statisticsUI = new StatisticsUI();
    }

    protected void displayOptions() { statisticsUI.displayStatisticsOptions(); }

    protected void processInput(int input) {
        switch (input) {
            case(1):
                // Display top 5 most attended events, average number of people participating at events,
                break;
            case(2):
                // Display traffic stats (logged in past day, 7 days, month).
                break;
            case(3):
                // do some other thing idk
                break;
        }
    }

    //TODO: functions here to calculate stats, pass ints to UI

    @Override
    public String toString() { return "Statistics"; }
}
