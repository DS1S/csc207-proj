package Main;

import Presenters.ErrorUI;
import coreUtil.IRunnable;
import coreUtil.InputProcessors.IndexProcessor;

import java.util.Scanner;

/**
 * Represents an abstract Subsystem.
 */
public abstract class SubSystem  implements IRunnable {
    private int numOptions;
    private IndexProcessor<Integer> indexProcessor;
    protected Scanner input = new Scanner(System.in);

    /**
     * Constructs a subsystem with a number of options and an InputProcessor to handle them.
     * @param numOptions the number of options accepted by this system.
     * @param indexProcessor the InputProcessor to handle them.
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
            processMainSignInput(option);
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
    protected abstract void processMainSignInput(int index);

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
