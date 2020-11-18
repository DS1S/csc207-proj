package coreUtil.InputProcessors;

import Presenters.EventUI;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class which asks a user for the number of minutes an event should last. Hardwired between 0 and 3 hours.
 */
public class DurationIndexProcessor extends IndexProcessor<Integer> {

    EventUI eventUI;

    /**
     * Creates a DurationIndexProcessor with a new Scanner and eventUI
     * @param scanner the Scanner to use for input
     * @param eventUI the UI in which to ask the prompt and display errors in.
     */
    public DurationIndexProcessor(Scanner scanner, EventUI eventUI) {
        super(scanner);
        this.eventUI = eventUI;
    }

    /**
     * Asks the user for input until they return a number between 0 and 180.
     * @return the valid number between 0 and 180 input by the user
     */
    @Override
    public Integer processInput() {
        Integer duration = null;
        while (duration == null || duration <= 0 || duration > 180) {
            eventUI.displayDurationPrompt();
            try {
                duration = scanner.nextInt();
                if (duration <= 0 || duration > 180) eventUI.displayInvalidDuration();
            }
            catch (InputMismatchException e) {
                eventUI.displayInvalidDuration();
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return duration;
    }
}
