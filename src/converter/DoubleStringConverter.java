package converter;

import javafx.scene.control.Alert;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class DoubleStringConverter extends javafx.util.converter.DoubleStringConverter {
    @Override
    public Double fromString(String s) {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Podana wartość nie jest liczbą!");
            alert.setHeaderText("Wprowadź poprawną wartość.");
            alert.setContentText("Wartość wprowadzona musi być liczbą.");
            alert.showAndWait();
            return Double.NaN;
        }
    }
}