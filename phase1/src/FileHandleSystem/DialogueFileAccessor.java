package FileHandleSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DialogueFileAccessor {

    private final String dialoguePath = "dialogue.csv";
    private String prompt;

    public DialogueFileAccessor(String prompt){
        this.prompt = prompt;
    }

    public List<String> loadPrompts() throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileInputStream(dialoguePath));
        String[] dialogues;

        while (scanner.hasNextLine()) {
            dialogues = scanner.nextLine().split(",");
            if (dialogues[0] == prompt){
                List<String> prompts = new ArrayList<>(Arrays.asList(dialogues));
                prompts.remove(0);
                return prompts;
            }
        }
        scanner.close();
        return new ArrayList<>();
    }

}
