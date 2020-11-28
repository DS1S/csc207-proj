package utility.inputprocessors;

import frontend.EventUI;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * A class which handles asking a user for a valid time code of form xx:xx
 */
public class TimeIndexProcessor extends IndexProcessor<LocalTime> {
    EventUI eventUI;

    /**
     * Constructs a TimeIndexProcessor which uses scanner to ask for input and displays prompts in eventUI.
     * @param scanner The Scanner to use for input.
     * @param eventUI The EventUI to display prompts on.
     */
    public TimeIndexProcessor(Scanner scanner, EventUI eventUI) {
        super(scanner);
        this.eventUI = eventUI;
    }

    /**
     * Asks the user for a time string until they return a valid time code.
     * @return The valid time input by the user.
     */
    @Override
    public LocalTime processInput() {
        LocalTime startTime = null;
        while (startTime == null) {
            eventUI.displayTimePrompt();
            try {
                String[] hourAndMinute = scanner.nextLine().split(":");
                startTime = LocalTime.of(Integer.parseInt(hourAndMinute[0]), Integer.parseInt(hourAndMinute[1]));
            }
            catch (ArrayIndexOutOfBoundsException | NumberFormatException | DateTimeException e) {
                eventUI.displayInvalidTime();
            }
        }
        return startTime;
    }
}
