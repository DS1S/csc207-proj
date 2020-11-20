package FileHandleSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class which handles serializing of classes into .ser files.
 * @param <T> the type to serialize.
 */
public class FileSerializer<T> {
    private String filePath;

    /**
     * Constructs a FileSerializer with type T and filepath to write/read
     * @param filePath the filepath to write to or read from
     */
    public FileSerializer(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Serializes and then saves a collection of objects of type T.
     * @param objects the list of objects to serialize and save.
     */
    public void saveCollection(List<T> objects) {
        try{
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // serialize the object
            output.writeObject(objects);
            output.close();
        }catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could Not Save Data");
        }
    }

    /**
     * Same as saveCollection, but does so for a single object.
     * @param object The objects to serialize and save.
     */
    public void saveObject(T object) {
        saveCollection(new ArrayList<>(Arrays.asList(object)));
    }

    /**
     * Deserialize and load an object from the filepath of type T.
     * @return The loaded object.
     */
    public T loadObject() {
        return loadCollection().get(0);
    }

    /**
     * Same as loadObject, but does so with a collection.
     * @return The list of loaded objects.
     */
    public List<T> loadCollection() {
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the collection
            List<T> objects = (List<T>)input.readObject();
            input.close();
            return objects;

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }

    }
}
