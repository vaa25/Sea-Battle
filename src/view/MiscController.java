package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alexander Vlasov
 */
public class MiscController implements Initializable {
    private final File file = new File("resources" + File.separatorChar + "name.txt");
    private String name;
    private Person person;
    @FXML
    private AnchorPane micsAnchorPane;

    @FXML
    private TextField nameTextField;

    @FXML
    void nameEntered(ActionEvent event) {
        name = nameTextField.getText();
        person.setName(name);
        saveName();
    }

    private void saveName() {
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(name);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loadName() {
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                char[] chars = new char[255];
                int length = fileReader.read(chars);
                name = String.copyValueOf(chars, 0, length);
                nameTextField.setText(name);
                fileReader.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!loadName()) {
            name = System.getProperty("user.name");
            if (name == null) {
                name = nameTextField.getText();
            } else {
                nameTextField.setText(name);
            }
        }
        person = new Person(name);
    }

    public String getName() {
        return name;
    }

    public Person getPerson() {
        return person;
    }
}
