package gui;

import alert.Alert;
import app.Jython;
import app.Rhino;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import converter.DoubleStringConverter;
import entity.Entity;
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
import org.mozilla.javascript.NativeArray;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Stage stage;

    @FXML
    private Button buttonFileChooser;

    @FXML
    private Button saveFile;

    @FXML
    private Button jsRun;

    @FXML
    private TextArea jsInput;

    @FXML
    private TextArea jsOutput;

    @FXML
    private Button pythonRun;

    @FXML
    private TextArea pythonInput;

    @FXML
    private TextArea pythonOutput;

    @FXML
    private TableView<Entity> tableView;

    private FileChooser fileChooser;

    private ObservableList<Entity> entities;

    private String csvPath;

    private Rhino rhino;

    private Jython jython;

    public Controller() {
        entities = FXCollections.observableArrayList();
        jython = new Jython();
        rhino = new Rhino();
    }

    public void chooseAndReadFile() {
        /*File csvFile = fileChooser.showOpenDialog(stage);
        if (csvFile == null) return;
        String csvPath = csvFile.toPath().toString();

        if (!csvFile.exists())
            return;*/
        csvPath = "E:\\Studia\\Semestr VI\\JFiK\\jfk\\JFK-4\\Zeszyt1.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvPath), ';', CSVWriter.NO_QUOTE_CHARACTER)) {

            String[] data;
            if (null != csvReader.readNext()) {

                TableColumn firstNameCol = new TableColumn("First Name");
                firstNameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("firstName"));
                firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                firstNameCol.setOnEditCommit(new StringEventHandler(Entity.class.getMethod("setFirstName", String.class)));

                TableColumn lastNameCol = new TableColumn("Last Name");
                lastNameCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("lastName"));
                lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                lastNameCol.setOnEditCommit(new StringEventHandler(Entity.class.getMethod("setLastName", String.class)));

                TableColumn salaryCol = new TableColumn("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("salary"));
                salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                salaryCol.setOnEditCommit(new DoubleEventHandler(Entity.class.getMethod("setSalary", Double.class)));


                TableColumn emailCol = new TableColumn("E-mail");
                emailCol.setCellValueFactory(new PropertyValueFactory<Entity, String>("email"));
                emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
                emailCol.setOnEditCommit(new StringEventHandler(Entity.class.getMethod("setEmail", String.class)));

                tableView.getColumns().addAll(firstNameCol, lastNameCol, salaryCol, emailCol);

                while (null != (data = csvReader.readNext())) {
                    this.entities.add(new Entity(data[0], data[1], Double.parseDouble(data[2]), data[3]));
                }
            }

            tableView.setItems(this.entities);
            tableView.setEditable(true);
            buttonFileChooser.setDisable(true);
            saveFile.setDisable(false);
            jsInput.setDisable(false);
            pythonInput.setDisable(false);
            jsOutput.setDisable(false);
            pythonOutput.setDisable(false);
            jsRun.setDisable(false);
            pythonRun.setDisable(false);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
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
    public void executeJS() {
        try {
            String result = rhino.execute(jsInput.getText(), tableView.getItems());
            jsOutput.setText(result);
            ArrayList o =  rhino.getRetEntities();
            tableView.getItems().clear();
            tableView.getItems().addAll(o);
            saveFile();
        } catch (Exception e) {
            jsOutput.setText(e.getMessage());
        }
    }

    @FXML
    public void executePython() {
        String result = jython.execute(pythonInput.getText());
    }

    @FXML
    public void initialize() {
        saveFile.setDisable(true);
        fileChooser = new FileChooser();
        fileChooser.setTitle("Please choose csv file:");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma Separated Values (*.csv)", "*.csv"));
        jsOutput.setEditable(false);
        pythonOutput.setEditable(false);
        jsOutput.setWrapText(true);
        pythonOutput.setWrapText(true);
        jsInput.setWrapText(true);
        pythonInput.setWrapText(true);
        jsInput.setDisable(true);
        pythonInput.setDisable(true);
        jsOutput.setDisable(true);
        pythonOutput.setDisable(true);
        jsRun.setDisable(true);
        pythonRun.setDisable(true);

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
