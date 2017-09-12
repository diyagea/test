package JFoenix;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class JFoenixController {

	@FXML
	private Label label;
	@FXML
	private JFXComboBox<Label> jfxCombo;
	@FXML
	private JFXHamburger h1;
	@FXML
	private JFXTextField input1;
	@FXML
	private JFXTextField input2;
	@FXML
	private JFXTextField input3;
	@FXML
	JFXRadioButton radio1;
	@FXML
	JFXRadioButton radio2;
	@FXML
	JFXListView<Label> listView;
	
	

	public JFoenixController() {
	}

	@FXML
	private void initialize() {
		label.setStyle("-fx-background-color:GREEN; -fx-padding:20");
		
		jfxCombo.getItems().add(new Label("Java 1.8"));
		jfxCombo.getItems().add(new Label("Java 1.7"));
		jfxCombo.getItems().add(new Label("Java 1.6"));
		jfxCombo.getItems().add(new Label("Java 1.5"));
		 
		jfxCombo.setPromptText("Select Java Version");
		
		
		HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(h1);
		burgerTask.setRate(-1);
		h1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
		    burgerTask.setRate(burgerTask.getRate()*-1);
		    burgerTask.play();
		});
		
		
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required");
		//validator.setAwsomeIcon(new Icon(FontAwesomeIcon.WARNING,"2em",";","error"));
		FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.WARNING);
		validator.setIcon(iconView);
		input2.getValidators().add(validator);
		input2.focusedProperty().addListener((o,oldVal,newVal)->{
		    if(!newVal) input2.validate();
		});
		
		NumberValidator validator2 = new NumberValidator();
		validator2.setMessage("Number Required");
		validator2.setIcon(iconView);
		input3.getValidators().add(validator2);
		input3.focusedProperty().addListener((o,oldVal,newVal)->{
		    if(!newVal) input3.validate();
		});
	
		
		final ToggleGroup group = new ToggleGroup();
        
		radio1.setPadding(new Insets(10));
		radio1.setToggleGroup(group);
		radio2.setPadding(new Insets(10));
		radio2.setToggleGroup(group);
		
		
		for(int i = 0 ; i < 4 ; i++) listView.getItems().add(new Label("Item " + i));
		listView.getStyleClass().add("mylistview");

		
	}
}
