package frontend;

import java.util.List;

public class MenuUI {

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          Options                                                //
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Displays a message prompting the user to select an option.
     */
    public void displayIndexPrompt() { System.out.print("Enter the number of the option you wish to select: "); }

    /**
     * Displays a message informing the user that they have entered an invalid number.
     */
    public void displayInvalidIndex() { System.out.println("You have entered an invalid number."); }


    /**
     * Displays main menu options to the user.
     * @param optionNames the options of the menu to be displayed.
     */
    public void displayOptions(List<String> optionNames){
        System.out.println("Please choose an option or return to main menu.");
        for(int i = 0; i < optionNames.size(); i++){
            System.out.println((i + 1) + ". " + optionNames.get(i));
        }
        System.out.println(optionNames.size() + 1 + ": Return/Exit.");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          Errors                                                 //
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Displays an error to the user.
     * @param s The error that is displayed.
     */
    public void displayError(String s) {
        System.out.println(s);
    }

}
