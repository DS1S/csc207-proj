package FileHandleSystem;

public class TerminationWorker<T> extends Thread{

    private T manager;
    private String filePath;

    public TerminationWorker(T manager, String filePath){
        this.manager = manager;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        FileSerializer<T> managerFileSerializer = new FileSerializer<T>(filePath);
        managerFileSerializer.saveObject(manager);
    }
}
