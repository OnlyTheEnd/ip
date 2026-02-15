package veigar;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

            // Background ImageView: bind to root pane size and send it to back
            String bgUrl = Main.class.getResource("/images/Background.png").toExternalForm();
            Image bgImage = new Image(bgUrl);
            ImageView bgView = new ImageView(bgImage);
            bgView.setPreserveRatio(false);
            bgView.setSmooth(true);
            // bind to root pane size so it matches exactly the window content area
            bgView.fitWidthProperty().bind(ap.widthProperty());
            bgView.fitHeightProperty().bind(ap.heightProperty());
            bgView.setMouseTransparent(true);
            bgView.setManaged(false);
            ap.getChildren().add(bgView);
            bgView.toBack();
            // Attach application stylesheet
            scene.getStylesheets().add(Main.class.getResource("/view/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Veigar");
            // Load application icon (assume resource exists)
            String iconUrl = Main.class.getResource("/images/icon.png").toExternalForm();
            Image icon = new Image(iconUrl);
            stage.getIcons().add(icon);
            fxmlLoader.<MainWindow>getController().setVeigar(veigar);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
