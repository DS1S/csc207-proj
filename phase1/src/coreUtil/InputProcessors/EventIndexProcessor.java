package coreUtil.InputProcessors;

import Presenters.EventUI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EventIndexProcessor extends IndexProcessor<Integer> {

    private final EventUI eventUI;
    private final int max;

    public EventIndexProcessor(Scanner scanner, EventUI eventUI, int max){
        super(scanner);
        this.eventUI = eventUI;
        this.max = max;
    }

    @Override
    public Integer processInput() {
        int option = 0;
        do{
            eventUI.displayIndexPrompt();
            try{
                option = scanner.nextInt();
                if(option <= 0 || option > max){
                    eventUI.displayInvalidIndex();
                }
            }
            catch (InputMismatchException e){
                eventUI.displayInvalidIndex();
                scanner.nextLine();
            }

        }while(option <= 0 || option > max);

        return option;
    }

}
