package Presenters;

/**
 * A class responsible for options displayed to the user.
 */
public class OptionUI {
    /**
     * Displays a message prompting the user to select an option.
     */
    public void displayIndexPrompt() { System.out.print("Enter the number of the option you wish to select: "); }

    /**
     * Displays a message informing the user that they have entered an invalid number.
     */
    public void displayInvalidIndex() { System.out.println("You have entered an invalid number."); }
}
