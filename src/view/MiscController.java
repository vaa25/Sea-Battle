package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alexander Vlasov
 */
public class MiscController implements Initializable {
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Misc init");
        name = nameTextField.getText();
        person = new Person(name);
    }

    public String getName() {
        return name;
    }

    public Person getPerson() {
        return person;
    }
}
