package coreUtil.InputProcessors;

import java.util.Scanner;

public abstract class IndexProcessor<T> {

    protected Scanner scanner;

    public IndexProcessor(Scanner scanner){
        this.scanner = scanner;
    }

    public abstract T processInput();
}
