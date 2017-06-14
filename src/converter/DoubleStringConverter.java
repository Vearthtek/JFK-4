package converter;


/**
 * Created by Vearthtek on 2017-06-14.
 */
public class DoubleStringConverter extends javafx.util.converter.DoubleStringConverter {
    @Override
    public Double fromString(String s) {
        try {
            return super.fromString(s);
        } catch (NumberFormatException e) {
            alert.Alert.showErrorAlert("Podana wartość nie jest liczbą!", "Wprowadź poprawną wartość.", "Wartość wprowadzona musi być liczbą.");
            return Double.NaN;
        }
    }
}