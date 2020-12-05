package frontend;

import java.util.ArrayList;
import java.util.List;

public class UserLinkUI extends MenuUI {
    public void displayUserLinkMenu() {
        List<String> options = new ArrayList<>();
        options.add("Add link");
        options.add("Explore other user's links");
        options.add("View your own links");
        displayOptions(options, true);
    }

    public void displayAddLinksPrompt() {
        System.out.println("What link would you like to add?");
    }

    public void displayExploreLinksPrompt() {
        System.out.println("Enter the user name of whom you would like to explore.");
    }

    public void displayEmptyLinksError() {
        System.out.println("This user has no links!");
    }
}
