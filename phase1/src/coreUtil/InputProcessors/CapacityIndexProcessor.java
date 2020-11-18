package coreUtil.InputProcessors;

import Presenters.EventUI;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class which asks a user for the capacity of an event.
 */
public class CapacityIndexProcessor extends IndexProcessor<Integer> {

    EventUI eventUI;

    /**
     * Creates a DurationIndexProcessor with a new Scanner and eventUI
     * @param scanner the Scanner to use for input
     * @param eventUI the UI in which to ask the prompt and display errors in.
     */
    public CapacityIndexProcessor(Scanner scanner, EventUI eventUI) {
        super(scanner);
        this.eventUI = eventUI;
    }

    /**
     * Asks the user for input until they input a valid positive integer.
     * @return the valid positive integer
     */
    @Override
    public Integer processInput() {
        Integer capacity = null;
        while (capacity == null || capacity <= 0) {
            try {
                eventUI.displayCapacityPrompt();
                capacity = scanner.nextInt();
                if (capacity <= 0) eventUI.displayInvalidCapacity();
            }
            catch (InputMismatchException e) {
                eventUI.displayInvalidCapacity();
                eventUI.displayCapacityPrompt();
                scanner.nextInt();
            }
        }
        scanner.nextInt();
        return capacity;
    }
}
