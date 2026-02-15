package veigar;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import veigar.ui.MainWindow;

/**
 * A GUI for Veigar using FXML.
 */
public class Main extends Application {
    private final Veigar veigar = new Veigar("data/tasks.json");


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setVeigar(veigar);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
