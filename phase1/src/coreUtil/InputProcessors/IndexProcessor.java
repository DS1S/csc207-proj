package coreUtil.InputProcessors;

import java.util.Scanner;

/**
 * A class which handles asking users for a list of numbers between two specific numbers. Used to handle choosing
 * options for all subsystems.
 * @param <T> The type of input to ask for.
 */
public abstract class IndexProcessor<T> {
    protected Scanner scanner;

    /**
     * Creates a new IndexProcessor which uses the provided Scanner for input.
     * @param scanner The Scanner to use for input.
     */
    public IndexProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract T processInput();
}
