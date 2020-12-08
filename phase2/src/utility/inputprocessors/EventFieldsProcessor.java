package utility.inputprocessors;

import frontend.EventUI;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents an Input Processor for multiple types of inputs.
 */
public class EventFieldsProcessor {

    private EventUI eventUI;
    private Scanner scanner;

    public EventFieldsProcessor(Scanner scanner, EventUI eventUI) {
        this.scanner = scanner;
        this.eventUI = eventUI;
    }

    /**
     * Asks the user for input until they return a number between 0 and 180.
     * @return A valid number between 0 and 180 inputted by the user.
     */
    public Integer processDurationInput() {
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

    /**
     * Asks the user for input until they input a valid positive integer.
     * @return A valid integer input.
     */
    public Integer processCapacityInput() {
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

    /**
     * Asks the user for a time string until they return a valid time code.
     * @return The valid time input by the user.
     */
    public LocalTime processTimeInput() {
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
