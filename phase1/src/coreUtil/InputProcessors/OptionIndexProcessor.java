package coreUtil.InputProcessors;

import Presenters.EventUI;
import Presenters.OptionUI;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class which asks a user for an integer between 0 and a provided number.
 */
public class OptionIndexProcessor extends IndexProcessor<Integer> {

    private final OptionUI optionUI;
    private final int max;

    /**
     * Constructs a OptionIndexProcessor to use scanner to ask a user for an integer between 0 and max.
     * @param scanner the Scanner to use for input.
     * @param max the max valid integer allowed.
     */
    public OptionIndexProcessor(Scanner scanner, int max){
        super(scanner);
        this.optionUI = new OptionUI();
        this.max = max;
    }

    /**
     * Asks the user for an integer until they return a valid between 0 and max.
     * @return the valid number between 0 and max input by the user.
     */
    @Override
    public Integer processInput() {
        int option = 0;
        do{
            optionUI.displayIndexPrompt();
            try{
                option = scanner.nextInt();
                if(option <= 0 || option > max){
                    optionUI.displayInvalidIndex();
                }
            }
            catch (InputMismatchException e){
                optionUI.displayInvalidIndex();
                scanner.nextLine();
            }

        }while(option <= 0 || option > max);
        scanner.nextLine();
        return option;
    }

}
