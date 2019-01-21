import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * @author aguo36
 * @version 1.0.1
 * Class for javafx queue
 */
public class OfficeHourQueue extends Application {

    private LinkedQueue<String> students = new LinkedQueue<>();
    private int maxStudentNumber = 0;
    private Label currentSizeText =
            new Label("Current Number Of Students In Queue");
    private Label currentSize = new Label("");
    private Label maxSize = new Label("");

    @Override
    public void start(Stage stage) {
        ListView<String> listView = new ListView<>(students);

        Button addButton = new Button();
        addButton.setText("Add Student");

        Button dequeueButton = new Button();
        dequeueButton.setText("Dequeue Student");

        TextField inputField = new TextField();

        addButton.setOnAction(e -> {
                students.enqueue(inputField.getText());
                inputField.setText("");
                inputField.requestFocus();
                updateMax();
            });

        dequeueButton.setOnAction(e -> {
                TextInputDialog password = new TextInputDialog("");
                password.setTitle("Privilege Check");
                password.setHeaderText("Confirmation");
                password.setContentText("Please Enter Password:");
                Optional<String> result = password.showAndWait();
                if (result.get().equals("CS1321R0X")) {
                    students.dequeue();
                }
            });

        dequeueButton.disableProperty()
                .bind(Bindings.isEmpty(students));

        addButton.disableProperty()
                .bind(Bindings.isEmpty(inputField.textProperty()));

        currentSize.textProperty().bind(Bindings
                .concat(currentSizeText
                        .textProperty(), ": ", Bindings.size(students)
                        .asString()));


        maxSize.textProperty().setValue("Max Number Of Students In Queue: "
                + 0);

        HBox entryBox = new HBox();
        entryBox.getChildren().addAll(inputField, addButton, dequeueButton);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(currentSize, maxSize, listView, entryBox);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("CS 1321 Office Hours Queue");
        stage.show();
    }

    /**
     * Updates the label to produce maximum numstudents
     */
    public void updateMax() {
        if (students.size() >= maxStudentNumber) {
            maxSize.textProperty()
                    .setValue("Max Number Of Students In Queue: "
                            + students.size());
            maxStudentNumber = students.size();
        }
    }
}
