package mp3Cataloguer.controller;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import lombok.Data;
import mp3Cataloguer.service.SongsService;
import sun.plugin2.util.ColorUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static javafx.scene.paint.Color.TRANSPARENT;

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
    @FXML
    HBox windowBarCustom;
    @FXML
    AnchorPane anchorPane;

    private double startMoveX = -1;
    private double startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;

    private Path libraryPath;
    private Path catalogPath;

    @FXML
    public void chooseLibraryDirectory(ActionEvent actionEvent){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Resource File");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory!=null){
            libraryPathTextField.setText(selectedDirectory.getAbsolutePath());
        }
    }
    @FXML
    public void chooseCatalogueDirectory(ActionEvent actionEvent){
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Resource File");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory!=null){
            catalogPathTextField.setText(selectedDirectory.getAbsolutePath());
        }
    }
    @FXML
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
    public void close(MouseEvent evt){
        ((Label)evt.getSource()).getScene().getWindow().hide();
    }
    @FXML
    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;

        moveTrackingRect = new Rectangle();
        moveTrackingRect.setWidth(anchorPane.getWidth());
        moveTrackingRect.setHeight(anchorPane.getHeight());
        moveTrackingRect.setStroke(Color.rgb(0, 255, 65));
        moveTrackingRect.setStrokeWidth(3);
        moveTrackingRect.setFill(TRANSPARENT);

        moveTrackingRect.getStyleClass().add( "tracking-rect" );

        moveTrackingPopup = new Popup();
        moveTrackingPopup.getContent().add(moveTrackingRect);
        moveTrackingPopup.show(windowBarCustom.getScene().getWindow());
        moveTrackingPopup.setOnHidden( (e) -> resetMoveOperation());
    }

    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }
    @FXML
    public void moveWindow(MouseEvent evt) {
        if (dragging) {

            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = windowBarCustom.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
            moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
        }
    }
    @FXML
    public void endMoveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = windowBarCustom.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));

            if (moveTrackingPopup != null) {
                moveTrackingPopup.hide();
                moveTrackingPopup = null;
            }
        }

        resetMoveOperation();
    }



}
