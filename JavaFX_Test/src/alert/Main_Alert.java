package alert;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * stackedBarChart Show
 * @author diyagea- Allen.Wang
 *
 */
public class Main_Alert extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
//    	infoDialogWithHeader();
//    	infoDialogWithOutHeader();
//    	warningDialog();
//    	errorDialog();
    	exceptionDialog();
//    	confirmDialog();
//    	actionConfirmDialog();
//    	inputDialog();
//    	choiceDialog();
//    	loginDialog();
    }
    
    void infoDialogWithHeader(){
    	 Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Information Dialog");
         alert.setHeaderText("Look, an Information Dialog");
         alert.setContentText("I have a great message for you!");

         alert.showAndWait();
    }
    
    void infoDialogWithOutHeader(){
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information Dialog");
    	alert.setHeaderText(null);
    	alert.setContentText("I have a great message for you!");

    	alert.showAndWait();
    }
    
    void warningDialog(){
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Warning Dialog");
    	alert.setHeaderText("Look, a Warning Dialog");
    	alert.setContentText("Careful with the next step!");

    	alert.showAndWait();
    }
    
    void errorDialog(){
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("Look, an Error Dialog");
    	alert.setContentText("Ooops, there was an error!");

    	alert.showAndWait();
    }
    
    void exceptionDialog(){
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Exception Dialog");
    	alert.setHeaderText("Look, an Exception Dialog");
    	alert.setContentText("Could not find file blabla.txt!");

    	Exception ex = new FileNotFoundException("Could not find file blabla.txt");

    	// Create expandable Exception.
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	ex.printStackTrace(pw);
    	String exceptionText = sw.toString();

    	Label label = new Label("The exception stacktrace was:");

    	TextArea textArea = new TextArea(exceptionText);
    	textArea.setEditable(false);
    	textArea.setWrapText(true);

    	textArea.setMaxWidth(Double.MAX_VALUE);
    	textArea.setMaxHeight(Double.MAX_VALUE);
    	GridPane.setVgrow(textArea, Priority.ALWAYS);
    	GridPane.setHgrow(textArea, Priority.ALWAYS);

    	GridPane expContent = new GridPane();
    	expContent.setMaxWidth(Double.MAX_VALUE);
    	expContent.add(label, 0, 0);
    	expContent.add(textArea, 0, 1);

    	// Set expandable Exception into the dialog pane.
    	alert.getDialogPane().setExpandableContent(expContent);

    	alert.showAndWait();
    }
    
    void confirmDialog(){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Look, a Confirmation Dialog");
    	alert.setContentText("Are you ok with this?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    // ... user chose OK
    		System.out.println("Confirm");
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    		System.out.println("CANCEL");
    	}
    }
    
    void actionConfirmDialog(){
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog with Custom Actions");
    	alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
    	alert.setContentText("Choose your option.");

    	ButtonType buttonTypeOne = new ButtonType("One");
    	ButtonType buttonTypeTwo = new ButtonType("Two");
    	ButtonType buttonTypeThree = new ButtonType("Three");
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

    	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == buttonTypeOne){
    	    // ... user chose "One"
    		System.out.println("One");
    	} else if (result.get() == buttonTypeTwo) {
    	    // ... user chose "Two"
    		System.out.println("Two");
    	} else if (result.get() == buttonTypeThree) {
    	    // ... user chose "Three"
    		System.out.println("Three");
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    		System.out.println("CANCEL or closed");
    	}
    }
    
    void inputDialog(){
    	TextInputDialog dialog = new TextInputDialog("walter");
    	dialog.setTitle("Text Input Dialog");
    	dialog.setHeaderText("Look, a Text Input Dialog");
    	dialog.setContentText("Please enter your name:");

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    System.out.println("Your name: " + result.get());
    	}

    	// The Java 8 way to get the response value (with lambda expression).
    	result.ifPresent(name -> System.out.println("Your name: " + name));
    }
    
    void choiceDialog(){
    	List<String> choices = new ArrayList<>();
    	choices.add("a");
    	choices.add("b");
    	choices.add("c");

    	ChoiceDialog<String> dialog = new ChoiceDialog<>("b", choices);
    	dialog.setTitle("Choice Dialog");
    	dialog.setHeaderText("Look, a Choice Dialog");
    	dialog.setContentText("Choose your letter:");

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    System.out.println("Your choice: " + result.get());
    	}

    	// The Java 8 way to get the response value (with lambda expression).
    	result.ifPresent(letter -> System.out.println("Your choice: " + letter));
    }
    
    void loginDialog(){
    	// Create the custom dialog.
    	Dialog<Pair<String, String>> dialog = new Dialog<>();
    	dialog.setTitle("Login Dialog");
    	dialog.setHeaderText("Look, a Custom Login Dialog");

    	// Set the icon (must be included in the project).
    	//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

    	// Set the button types.
    	ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    	// Create the username and password labels and fields.
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));

    	TextField username = new TextField();
    	username.setPromptText("Username");
    	PasswordField password = new PasswordField();
    	password.setPromptText("Password");

    	grid.add(new Label("Username:"), 0, 0);
    	grid.add(username, 1, 0);
    	grid.add(new Label("Password:"), 0, 1);
    	grid.add(password, 1, 1);

    	// Enable/Disable login button depending on whether a username was entered.
    	Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    	loginButton.setDisable(true);

    	// Do some validation (using the Java 8 lambda syntax).
    	username.textProperty().addListener((observable, oldValue, newValue) -> {
    	    loginButton.setDisable(newValue.trim().isEmpty());
    	});

    	dialog.getDialogPane().setContent(grid);

    	// Request focus on the username field by default.
    	Platform.runLater(() -> username.requestFocus());

    	// Convert the result to a username-password-pair when the login button is clicked.
    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == loginButtonType) {
    	        return new Pair<>(username.getText(), password.getText());
    	    }
    	    return null;
    	});

    	Optional<Pair<String, String>> result = dialog.showAndWait();

    	result.ifPresent(usernamePassword -> {
    	    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
    	});
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
