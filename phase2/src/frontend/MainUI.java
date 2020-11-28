package frontend;

import utility.RunnableSystem;

import java.util.List;
import java.util.Map;

/**
 * The class responsible for the main menu displayed to the user.
 */
public class MainUI extends MenuUI{


    /**
     * Displays the main menu to the user.
     * @param subsystemNames The subsystems that implement the actions the users can take.
     */
    public void displayMainMenu(List<String> subsystemNames) {
        System.out.println(" _________  ____  ____   ______ \n|  _   _  ||_   ||   _|.' ___  | \n|_/ | | " +
                "\\_|  | |__| | / .'   \\_|  \n    | |      |  __  | | | \n   _| |_    _| |  | |_\\ `.___.'" +
                "\\  \n  |_____|  |____||____|`.____ .' \nWelcome to TecHConference! Press a number and then " +
                "ENTER to get started:");
        displayOptions(subsystemNames);
    }

    /**
     * Indicates to the user that their input was invalid and prompts them to enter a new input.
     * @param subsystemNames The subsystems that implement the actions the users can take.
     */
    public void displayInvalidInput(List<String> subsystemNames) {
        System.out.flush();
        System.out.println("Invalid input! Try again:");
        displayOptions(subsystemNames);
    }
}
