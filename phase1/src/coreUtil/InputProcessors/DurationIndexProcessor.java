package coreUtil.InputProcessors;

import Presenters.EventUI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DurationIndexProcessor extends IndexProcessor<Integer> {

    EventUI eventUI;

    public DurationIndexProcessor(Scanner scanner, EventUI eventUI) {
        super(scanner);
        this.eventUI = eventUI;
    }

    @Override
    public Integer processInput() {
        Integer duration = null;
        while (duration == null) {
            eventUI.displayDurationPrompt();
            try {
                duration = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                eventUI.displayInvalidDuration();
            }
        }
        return duration;
    }
}
