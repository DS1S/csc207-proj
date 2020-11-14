package Presenters;

import coreUtil.IRunnable;

import java.util.Map;

public class MainMenuUI {

    public MainMenuUI() { }

    public void displayOptions(Map<Integer, IRunnable> subsystems) {
        for (int i = 1; i < subsystems.size(); i++) {
            System.out.println(i + ": " + subsystems.get(i).toString());
        }
    }

    public void displayMainMenu(Map<Integer, IRunnable> subsystems) {
        System.out.flush();
        System.out.println(" _________  ____  ____   ______ \n|  _   _  ||_   ||   _|.' ___  | \n|_/ | | " +
                "\\_|  | |__| | / .'   \\_|  \n    | |      |  __  | | | \n   _| |_    _| |  | |_\\ `.___.'" +
                "\\  \n  |_____|  |____||____|`.____ .' \nWelcome to TecHConference! Press a number and then " +
                "ENTER to get started:");
        displayOptions(subsystems);
    }

    public void displayInvalidInput(Map<Integer, IRunnable> subsystems) {
        System.out.flush();
        System.out.println("Invalid input! Try again:");
        displayOptions(subsystems);
    }
}
