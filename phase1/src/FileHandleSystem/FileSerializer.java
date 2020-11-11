package FileHandleSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSerializer<T> {

    private String filePath;

    public FileSerializer(String filePath){
        this.filePath = filePath;
    }

    public void saveCollection(List<T> objects){
        try{
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // serialize the object
            output.writeObject(objects);
            output.close();
        }catch (IOException e){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could Not Save Data");
        }
    }

    public void saveObject(T object){
        saveCollection(new ArrayList<>(Arrays.asList(object)));
    }

    public T loadObject(){
        return loadCollection().get(0);
    }

    public List<T> loadCollection(){
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the collection
            List<T> objects = (List<T>)input.readObject();
            input.close();
            return objects;

        } catch (IOException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e){
            return new ArrayList<>();
        }

    }
}
