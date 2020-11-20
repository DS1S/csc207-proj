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
     * Creates a DurationIndexProcessor with a new Scanner and EventUI.
     * @param scanner The Scanner to use for input.
     * @param eventUI The UI in which to ask the prompt and display errors in.
     */
    public DurationIndexProcessor(Scanner scanner, EventUI eventUI) {
        super(scanner);
        this.eventUI = eventUI;
    }

    /**
     * Asks the user for input until they return a number between 0 and 180.
     * @return A valid number between 0 and 180 inputted by the user.
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
