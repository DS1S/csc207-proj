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
     * Creates a DurationInputProcessor with a new Scanner and EventUI.
     * @param scanner The Scanner to use for input.
     * @param eventUI The UI in which to ask the prompt and display errors in.
     */
    public CapacityIndexProcessor(Scanner scanner, EventUI eventUI) {
        super(scanner);
        this.eventUI = eventUI;
    }

    /**
     * Asks the user for input until they input a valid positive integer.
     * @return A valid integer input.
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
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return capacity;
    }
}
