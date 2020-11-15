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
        return duration;
    }
}
