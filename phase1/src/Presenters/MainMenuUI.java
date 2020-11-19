package Presenters;

import coreUtil.IRunnable;

import java.util.Map;

/**
 * The class responsible for the main menu displayed to the user.
 */
public class MainMenuUI {

    /**
     * Constructs an instance of MainMenuUI.
     */
    public MainMenuUI() { }

    /**
     * Displays main menu options to the user.
     * @param subsystems the subsystems that implement the actions the users can take
     */
    public void displayOptions(Map<Integer, IRunnable> subsystems) {
        for (int i = 1; i < subsystems.size(); i++) {
            System.out.println(i + ": " + subsystems.get(i).toString());
        }
        System.out.println(subsystems.size() + ": Exit");
    }

    /**
     * Displays the main menu to the user.
     * @param subsystems the subsystems that implement the actions the users can take
     */
    public void displayMainMenu(Map<Integer, IRunnable> subsystems) {
        System.out.println(" _________  ____  ____   ______ \n|  _   _  ||_   ||   _|.' ___  | \n|_/ | | " +
                "\\_|  | |__| | / .'   \\_|  \n    | |      |  __  | | | \n   _| |_    _| |  | |_\\ `.___.'" +
                "\\  \n  |_____|  |____||____|`.____ .' \nWelcome to TecHConference! Press a number and then " +
                "ENTER to get started:");
        displayOptions(subsystems);
    }

    /**
     * Indicates to the user that their input was invalid and prompts them to enter a new input.
     * @param subsystems the subsystems that implement the actions the users can take
     */
    public void displayInvalidInput(Map<Integer, IRunnable> subsystems) {
        System.out.flush();
        System.out.println("Invalid input! Try again:");
        displayOptions(subsystems);
    }
}
