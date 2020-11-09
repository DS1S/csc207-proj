package FileHandleSystem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TerminationWorker extends Thread{

    private Map<String, Manager> filePathForManagers;

    public TerminationWorker(Map<String, Manager> filePathForManagers){
        this.filePathForManagers = filePathForManagers;
    }

    @Override
    public void run() {
        for (String filePath : filePathForManagers){
            FileSerializer<Manager> managerFileSerializer = new FileSerializer<Manager>(filePath);
            managerFileSerializer.saveObject(filePathForManagers.get(filePath));
        }
    }
}
