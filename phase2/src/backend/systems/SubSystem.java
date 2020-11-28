package backend.systems;


import frontend.ErrorUI;
import utility.IRunnable;
import utility.inputprocessors.IndexProcessor;

import java.util.Scanner;

/**
 * Represents an abstract Subsystem.
 */
public abstract class SubSystem implements IRunnable {
    private int numOptions = 0;
    private IndexProcessor<Integer> indexProcessor = null;
    protected Scanner input = new Scanner(System.in);

    /**
     * Constructs a subsystem with a number of options and an IndexProcessor to handle them.
     * @param numOptions the number of options accepted by this system.
     * @param indexProcessor the IndexProcessor to handle them.
     */
    public SubSystem(int numOptions, IndexProcessor<Integer> indexProcessor) {
        this.numOptions = numOptions;
        this.indexProcessor = indexProcessor;
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
        ErrorUI errorUI = new ErrorUI();
        String string = "";
        while (string.isEmpty()) {
            string = input.nextLine();
            if (string.isEmpty()) errorUI.displayError(attribute + " is invalid, please input a " + attribute + "!");
        }
        return string;
    }
}
