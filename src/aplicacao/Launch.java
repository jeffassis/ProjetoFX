package aplicacao;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe que contém o método main da Aplicação
 *
 * @author jeff-
 */
public class Launch extends Application {

    /**
     * Sobrecarga do método start na Class Launch, passando um param
     * primaryStage para ser carregado o FXML Login. Lançando uma IOException
     *
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Identificação");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Método main passando os argumentos da Class Launch
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
