package utility.inputprocessors;

import frontend.MenuUI;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class which asks a user for an integer between 0 and a provided number.
 */
public class OptionInputProcessor extends InputProcessor<Integer> {
    private final MenuUI optionUI;
    private int max;

    /**
     * Constructs a OptionInputProcessor to use scanner to ask a user for an integer between 0 and max.
     * @param scanner The Scanner to use for input.
     * @param max The max valid integer allowed.
     */
    public OptionInputProcessor(Scanner scanner, int max) {
        super(scanner);
        this.optionUI = new MenuUI();
        this.max = max;
    }

    /**
     * Asks the user for an integer until they return a valid between 0 and max.
     * @return A valid number between 0 and max input by the user.
     */
    @Override
    public Integer processInput() {
        int option = 0;
        do{
            optionUI.displayIndexPrompt();
            try{
                option = scanner.nextInt();
                if(option <= 0 || option > max) {
                    optionUI.displayInvalidIndex();
                }
            }
            catch (InputMismatchException e) {
                optionUI.displayInvalidIndex();
                scanner.nextLine();
            }

        }while(option <= 0 || option > max);
        scanner.nextLine();
        return option;
    }
}
