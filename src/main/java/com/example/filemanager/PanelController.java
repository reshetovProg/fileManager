package com.example.filemanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelController implements Initializable {


    @FXML
    TableView<FileInfo> tableView;

    @FXML
    ComboBox<String> diskBox;

    @FXML
    TextField filePath;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
        fileTypeColumn.setCellValueFactory(param->new SimpleStringProperty(param.getValue().getType()));
        fileTypeColumn.setPrefWidth(24);

        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("name");
        fileNameColumn.setCellValueFactory(param->new SimpleStringProperty(param.getValue().getName()));
        fileNameColumn.setPrefWidth(180);

        TableColumn<FileInfo, String> fileSizeColumn = new TableColumn<>("size");
        fileSizeColumn.setCellValueFactory(param->{
            String s = String.valueOf(param.getValue().getSize());
            if (s.equals("0")) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                    String.format("%s bytes",s));
        });
        fileSizeColumn.setPrefWidth(180);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TableColumn<FileInfo, String> fileDateColumn = new TableColumn<>("date");
        fileDateColumn.setCellValueFactory(param->new SimpleStringProperty(param.getValue().getDateOfChange().format(dtf)));
        fileDateColumn.setPrefWidth(180);

        tableView.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, fileDateColumn);
        tableView.getSortOrder().add(fileTypeColumn);

        diskBox.getItems().clear();
        for(Path path: FileSystems.getDefault().getRootDirectories()){
            diskBox.getItems().add(path.toString());
        }
        diskBox.getSelectionModel().select(0);


        updateList(Paths.get("."));
    }

    private void updateList(Path path) {

        try {
            filePath.setText(path.normalize().toAbsolutePath().toString());

            tableView.getItems().clear();
            tableView.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            tableView.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,"error when try get list of files", ButtonType.OK);
            alert.showAndWait();
        }

    }

}
