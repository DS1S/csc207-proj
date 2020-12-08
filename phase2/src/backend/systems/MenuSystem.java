package backend.systems;

import frontend.MenuUI;
import utility.RunnableSystem;
import utility.inputprocessors.IndexProcessor;
import utility.inputprocessors.OptionIndexProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents an abstract MenuSystem.
 */
public abstract class MenuSystem implements RunnableSystem {
    private int numOptions;
    private IndexProcessor<Integer> indexProcessor;
    protected Scanner input = new Scanner(System.in);

    /**
     * Constructs a MenuSystem with a number of options and an IndexProcessor to handle them.
     * @param numOptions the number of options accepted by this system.
     */
    public MenuSystem(int numOptions) {
        this.numOptions = numOptions;
        this.indexProcessor = new OptionIndexProcessor(input, numOptions);
    }

    public MenuSystem(){
        this(1);
    }

    /**
     * Changes the number of options available on the menu.
     * @param numOptions the new number of options to be available on the menu
     */
    protected void changeNumOptions(int numOptions){
        this.numOptions = numOptions;
        this.indexProcessor = new OptionIndexProcessor(input, numOptions);
    }

    /**
     * Runs a subsystem by asking for the required number of options and processing them.
     */
    @Override
    public void run() {
        int option;
        do{
            displayOptions();
            option = indexProcessor.processInput();
            if(option != numOptions)
                processInput(option);
        }while(option != numOptions);
    }

    /**
     * Displays the options for the subsystem.
     */
    protected abstract void displayOptions();

    /**
     * Processes an integer input in the subsystem.
     * @param index The input to be processed.
     */
    protected abstract void processInput(int index);

    /**
     * Asks the user for an input and checks if the input is valid.
     * @param attribute The attribute the user is asked for.
     * @return Whatever the user inputted.
     */
    protected String askForString(String attribute) {
        MenuUI errorUI = new MenuUI();
        String string = "";
        while (string.isEmpty()) {
            string = input.nextLine();
            if (string.isEmpty()) errorUI.displayError(attribute + " is empty, please input a " + attribute + "!");
        }
        return string;
    }

    /**
     * Asks the user for a input that is either Y or N and checks if the input is valid.
     * @return true if Y was inputted, and false if N was inputted
     */
    protected boolean askForBoolean() {
        MenuUI errorUI = new MenuUI();
        while (true) {
            String string = input.nextLine();
            if (string.equalsIgnoreCase("Y")) return true;
            else if (string.equalsIgnoreCase("N")) return false;
            else errorUI.displayError("Invalid entry! Please input Y or N.");
        }
    }

    /**
     * Gets the names of a collection of subsystems.
     * @param subSystems a collection that maps an index to a subsystem
     * @return a list of names of the subsystems in the collection
     */
    protected List<String> convertSubSystemsToNames(Map<Integer, RunnableSystem> subSystems){
        List<String> subSystemNames = new ArrayList<>();
        subSystems.forEach((integer, runnableSystem) -> subSystemNames.add(runnableSystem.toString()));
        return subSystemNames;
    }
}
