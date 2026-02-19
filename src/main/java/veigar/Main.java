package veigar;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
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
            ImageView bgView = new ImageView(getImage("/images/Background.png"));
            setbgImage(ap, bgView);
            // Attach application stylesheet
            scene.getStylesheets().add(Main.class.getResource("/view/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Veigar");
            //Disable fullscreen
            stage.setResizable(false);
            // Load application icon
            stage.getIcons().add(getImage("/images/icon.png"));
            fxmlLoader.<MainWindow>getController().setVeigar(veigar);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setbgImage(AnchorPane ap, ImageView bgView) {
        bgView.setPreserveRatio(false);
        bgView.setSmooth(true);
        // bind to root pane size so it matches exactly the window content area
        bgView.fitWidthProperty().bind(ap.widthProperty());
        bgView.fitHeightProperty().bind(ap.heightProperty());
        bgView.setMouseTransparent(true);
        bgView.setManaged(false);
        ap.getChildren().add(bgView);
        bgView.toBack();
    }

    private Image getImage(String path) {
        String url = Main.class.getResource(path).toExternalForm();
        return new Image(url);
    }
}
