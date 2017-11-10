package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class MyControll implements Initializable {
	@FXML
	private Button myButton;

	@FXML
	private TextField myTextField;
	@FXML
	private TextArea logBox;
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;
	//单选框
	private ToggleGroup group;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO (don't really need to do anything here).
		
		logBox.setMaxHeight(Double.MAX_VALUE);
		logBox.setMaxWidth(Double.MAX_VALUE);
		logBox.setEditable(false);
		logBox.setWrapText(true);
		
		ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
		comboBox.setItems(options);
		
		
		//单选框
		group = new ToggleGroup();
		radio1.setToggleGroup(group);
		radio2.setToggleGroup(group);
		radio1.setUserData("1");
		radio2.setUserData("2");

	}

	// When user click on myButton
	// this method will be called.
	public void showDateTime(ActionEvent event) {
		System.out.println("Button Clicked!");

		Date now = new Date();

		DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		String dateTimeString = df.format(now);
		// Show in VIEW
		myTextField.setText(dateTimeString);
		
		logBox.setText(logBox.getText()+ dateTimeString + "\n");
		
		System.out.println(group.getSelectedToggle().getUserData().toString());

	}
	
	public void selectOption(){
		System.out.println(comboBox.getSelectionModel().getSelectedIndex());
		System.out.println(comboBox.getSelectionModel().getSelectedItem());
		System.out.println(comboBox.getValue());
	}
	
}
