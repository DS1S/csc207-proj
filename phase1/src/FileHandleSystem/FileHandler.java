package FileHandleSystem;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler<T> {

    private String filePath;

    public FileHandler(String filePath){
        this.filePath = filePath;
    }

    public void saveCollection(List<T> objects) throws IOException{
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(objects);
        output.close();
    }

    public void saveObject(T object) throws IOException{
        saveCollection(new ArrayList<>(Arrays.asList(object)));
    }

    public T loadObject() throws ClassNotFoundException{
        return loadCollection().get(0);
    }

    public List<T> loadCollection() throws ClassNotFoundException{
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the collection
            List<T> objects = (List<T>)input.readObject();
            input.close();
            return objects;

        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }

}
