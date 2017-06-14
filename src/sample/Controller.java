package sample;

import alert.Alert;
import entity.Entity;
import com.opencsv.CSVWriter;
import converter.DoubleStringConverter;
import event.handler.DoubleEventHandler;
import event.handler.StringEventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Controller {

    private Stage stage;

    @FXML
    private Button buttonFileChooser;

    @FXML
    private Button saveFile;

    @FXML
    private Button jsButton;

    @FXML
    private TextArea jsInput;

    @FXML
    private TextArea jsOutput;

    @FXML
    private Button pythonButton;

    @FXML
    private TextArea pythonInput;

    @FXML
    private TextArea pythonOutput;

    @FXML
    private TableView<Entity> tableView;

    private FileChooser fileChooser;

    private ObservableList<Entity> entities;

    private String csvPath;

    public Controller() {
        entities = FXCollections.observableArrayList();
    }

    public void chooseAndReadFile() {
        /*File csvFile = fileChooser.showOpenDialog(stage);
        if (csvFile == null) return;
        String csvPath = csvFile.toPath().toString();

        if (!csvFile.exists())
            return;*/
        csvPath = "E:\\Studia\\Semestr VI\\JFiK\\jfk\\JFK-4\\Zeszyt1.csv";
        String line;
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {

            String[] data;
            if (null != (line = br.readLine())) {

                TableColumn firstNameCol = new TableColumn("First Name");
                firstNameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("firstName"));
                firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                firstNameCol.setOnEditCommit(new StringEventHandler());

                TableColumn lastNameCol = new TableColumn("Last Name");
                lastNameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("lastName"));
                lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                lastNameCol.setOnEditCommit(new StringEventHandler());

                TableColumn salaryCol = new TableColumn("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("salary"));
                salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                salaryCol.setOnEditCommit(new DoubleEventHandler());


                TableColumn emailCol = new TableColumn("E-mail");
                emailCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("email"));
                emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
                emailCol.setOnEditCommit(new StringEventHandler());

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
    void saveFile() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvPath), ';', CSVWriter.NO_QUOTE_CHARACTER);
            writer.writeNext(new String[]{"firstName", "lastName", "salary", "email"});
            tableView.getItems().stream().forEach(e -> writer.writeNext(e.toArrayString()));
            writer.close();
            Alert.showInfoAlert("Udalo sie zapisac do pliku!", "Zapis sie powiódł.", "Zapisano do pliku " + csvPath + ".");
        } catch (IOException e) {
            Alert.showErrorAlert("Nie udało się zapisać do pliku!", "Wprowadź poprawną wartość.", "Coś się stało z plikiem " + csvPath + ".");
        }
    }

    @FXML
    public void initialize() {
        saveFile.setDisable(true);
        fileChooser = new FileChooser();
        fileChooser.setTitle("Please choose csv file:");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma Separated Values (*.csv)", "*.csv"));
        jsOutput.setEditable(false);
        pythonOutput.setEditable(false);

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow row;
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                        if (null == row.getItem()) {
                            tableView.getItems().add(new Entity("enter name...", "enter last name...", 0., "enter email..."));
                        }
                    }
                }
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
