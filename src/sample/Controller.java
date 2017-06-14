package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Controller {

    private Stage stage;

    @FXML
    private Button buttonFileChooser;

    @FXML
    private Button saveFile;

    @FXML
    private TableView<Entity> tableView;

    private FileChooser fileChooser;

    private ObservableList<Entity> entities;

    public Controller() {
        entities = FXCollections.observableArrayList();
    }

    public void chooseAndReadFile() {
        File csvFile = fileChooser.showOpenDialog(stage);
        if (csvFile == null) return;
        String csvPath = csvFile.toPath().toString();

        if (!csvFile.exists())
            return;

        String line;
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

            String[] data;
            if (null != (line = br.readLine())) {

                TableColumn firstNameCol = new TableColumn("First Name");
                firstNameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("firstName"));
                firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                firstNameCol.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<Entity, String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<Entity, String> t) {
                                ((Entity) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                ).setFirstName(t.getNewValue());
                            }
                        }
                );

                TableColumn lastNameCol = new TableColumn("Last Name");
                lastNameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("lastName"));
                lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                lastNameCol.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<Entity, String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<Entity, String> t) {
                                ((Entity) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                ).setFirstName(t.getNewValue());
                            }
                        }
                );

                TableColumn salaryCol = new TableColumn("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("salary"));
                salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                salaryCol.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<Entity, Double>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<Entity, Double> t) {
                                ((Entity) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                ).setSalary(t.getNewValue());
                            }
                        }
                );


                TableColumn emailCol = new TableColumn("E-mail");
                emailCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("email"));
                emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
                emailCol.setOnEditCommit(
                        new EventHandler<TableColumn.CellEditEvent<Entity, String>>() {
                            @Override
                            public void handle(TableColumn.CellEditEvent<Entity, String> t) {
                                ((Entity) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                ).setEmail(t.getNewValue());
                            }
                        }
                );

                tableView.getColumns().addAll(firstNameCol, lastNameCol, salaryCol, emailCol);
            }

            while (null != (line = br.readLine())) {
                data = line.split(cvsSplitBy);
                this.entities.add(new Entity(data[0], data[1], Double.parseDouble(data[2]), data[3]));
            }

            tableView.setItems(this.entities);
            tableView.setEditable(true);
            buttonFileChooser.setDisable(true);
            saveFile.setDisable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        saveFile.setDisable(true);
        fileChooser = new FileChooser();
        fileChooser.setTitle("Please choose csv file:");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma Separated Values (*.csv)", "*.csv"));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
