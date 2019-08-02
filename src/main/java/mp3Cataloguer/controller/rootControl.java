package mp3Cataloguer.controller;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;
import mp3Cataloguer.service.SongsService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class rootControl {

    @FXML
    private TextField libraryPathTextField;
    @FXML
    private TextField catalogPathTextField;
    @FXML
    private Button chooseLibraryDirectory;
    @FXML
    private Button chooseCatalogueDirectory;
    @FXML
    private Button submit;

    private Path libraryPath;
    private Path catalogPath;

    public void getWeather(ActionEvent actionEvent) {

    }
    public void chooseLibraryDirectory(ActionEvent actionEvent){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Resource File");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory!=null){
            libraryPathTextField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    public void chooseCatalogueDirectory(ActionEvent actionEvent){
        catalogPathTextField.setText("Clicked!");

        System.out.println("Klik2");
    }
    public void createCatalogue(ActionEvent actionEvent){
        SongsService songsService = new SongsService();
        Path libraryPath = Paths.get(libraryPathTextField.getText());
        Path catalogPath = Paths.get(catalogPathTextField.getText());

        try {
            songsService.createCatalog(libraryPath,catalogPath);
        } catch (InvalidDataException | UnsupportedTagException e) {
            e.printStackTrace();
        }
        System.out.println("Catalogue created");

    }



}
