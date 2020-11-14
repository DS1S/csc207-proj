package coreUtil.InputProcessors;

import Presenters.EventUI;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Scanner;

public class TimeIndexProcessor extends IndexProcessor<LocalTime> {

    EventUI eventUI;

    public TimeIndexProcessor(Scanner scanner, EventUI eventUI){
        super(scanner);
        this.eventUI = eventUI;
    }


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
