package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txt_usuario;
    @FXML
    private PasswordField fmt_txt_senha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void logar(ActionEvent event) {
        if (txt_usuario.getText().equals("") && fmt_txt_senha.getText().equals("")) {

            try {
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
                Scene scene = new Scene(root);

                stage.setTitle("Tela Principal");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exit(ActionEvent event) {
        // Encerra a aplicação
        Platform.exit();
    }
}
