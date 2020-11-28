package utility.filehandling;

import utility.filehandling.FileSerializer;

/**
 * A class to handle the saving of classes of type T when a shutdown is detected.
 * @param <T>
 */
public class TerminationWorker<T> extends Thread{
    private T manager;
    private String filePath;

    /**
     * Constructs a new TerminationWorker to save manager to filepath.
     * @param manager The type to save.
     * @param filePath The file path to save to.
     */
    public TerminationWorker(T manager, String filePath) {
        this.manager = manager;
        this.filePath = filePath;
    }

    /**
     * Automatically runs when a shutdown is detected. When detected, creates a FileSerializer to save the input object to the input filepath.
     */
    @Override
    public void run() {
        FileSerializer<T> managerFileSerializer = new FileSerializer<>(filePath);
        managerFileSerializer.saveObject(manager);
    }
}
