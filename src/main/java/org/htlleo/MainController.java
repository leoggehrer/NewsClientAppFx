package org.htlleo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.htlleo.models.Message;

public class MainController implements Initializable  {

    @FXML
    public TextArea txtBody;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtServer;
    @FXML
    public TextField txtPort;
    @FXML
    public Button btnSend;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtBody.setDisable(false);
        txtBody.setEditable(true);

        txtServer.setText("localhost");
        txtServer.setDisable(false);

        txtPort.setText("3333");
        txtPort.setDisable(false);

        txtName.setText("");
        txtName.setDisable(false);

        btnSend.setDisable(false);
    }

    public void onSend(ActionEvent actionEvent) {
        int port = Integer.parseInt(txtPort.getText());

        try (Socket socket = new Socket(txtServer.getText(), port)) {
            Message message = new Message();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            message.setDate(new Date());
            message.setFrom(txtName.getText());
            message.setBody(txtBody.getText());
            oos.writeObject(message);
            oos.flush();
            oos.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
