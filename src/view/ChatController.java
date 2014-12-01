package view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Alexander Vlasov
 */
public class ChatController implements Initializable {
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private TextArea editChatTextArea;
    @FXML
    private SplitPane chatSplitPane;
    @FXML
    private TextArea chatTextArea;

    SimpleObjectProperty sendChatMessage;

    @FXML
    void editChatKeyTyped(KeyEvent event) {
    }

    @FXML
    void editChatKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            sendChatMessage.set(editChatTextArea.getText());
            chatTextArea.appendText(" Я: " + sendChatMessage.getValue() + "\n");
            editChatTextArea.setText("");
            sendChatMessage.set(null);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Chat init");
        disactivate();
    }

    public void activate() {
        editChatTextArea.setDisable(false);
    }

    public void disactivate() {
        editChatTextArea.setDisable(true);
    }
    public void print(String t1) {
        chatTextArea.appendText(t1);
    }
}
