package mp3Cataloguer;


import com.mpatric.mp3agic.Mp3File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mp3Cataloguer.service.SongsService;

import java.io.IOException;
import java.nio.file.*;

public class App extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root =
                FXMLLoader.load(
                        getClass()
                                .getResource("/root.fxml"));

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(new Scene(root));

        primaryStage.show();


    }
}
