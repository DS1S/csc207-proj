package coreUtil.InputProcessors;

import Presenters.EventUI;
import Presenters.OptionUI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OptionIndexProcessor extends IndexProcessor<Integer> {

    private final OptionUI optionUI;
    private final int max;

    public OptionIndexProcessor(Scanner scanner, int max){
        super(scanner);
        this.optionUI = new OptionUI();
        this.max = max;
    }

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
