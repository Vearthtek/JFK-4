package alert;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class Alert {

    public static void showInfoAlert(String title, String header, String content) {
        showAlert(title, header, content, javafx.scene.control.Alert.AlertType.INFORMATION);
    }

    public static void showErrorAlert(String title, String header, String content) {
        showAlert(title, header, content, javafx.scene.control.Alert.AlertType.ERROR);
    }

    private static void showAlert(String title, String header, String content, javafx.scene.control.Alert.AlertType type) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
