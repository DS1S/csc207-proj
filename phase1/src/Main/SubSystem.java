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
            processMainSignInput(option);
        }while(option != numOptions);
    }

    protected abstract void displayOptions();

    protected abstract void processMainSignInput(int index);

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
