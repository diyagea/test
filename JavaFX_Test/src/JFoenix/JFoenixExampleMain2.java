package JFoenix;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSlider.IndicatorPosition;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleNode;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JFoenixExampleMain2 extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		//Group root = new Group();
		VBox root = new VBox();
		root	.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.setSpacing(20);
		//**************************************************
		JFXProgressBar bar = new JFXProgressBar();
		JFXProgressBar jfxBar = new JFXProgressBar();
		jfxBar.setPrefWidth(500);
		JFXProgressBar jfxBarInf = new JFXProgressBar();
		jfxBarInf.setPrefWidth(500);
		jfxBarInf.setProgress(-1.0f);
		Timeline timeline = new Timeline( 
				new KeyFrame(Duration.ZERO, new KeyValue(bar.progressProperty(), 0), new KeyValue(jfxBar.progressProperty(), 0)), 
				new KeyFrame(Duration.seconds(2), new KeyValue(bar.progressProperty(), 1), new KeyValue(jfxBar.progressProperty(), 1)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		root.getChildren().addAll(jfxBar);
		//**************************************************
		
		JFXSlider hor_left = new JFXSlider();
		hor_left.setMinWidth(500);
		JFXSlider hor_right = new JFXSlider();
		hor_left.setMinWidth(500);
		hor_left.setIndicatorPosition(IndicatorPosition.RIGHT);
		JFXSlider ver_left = new JFXSlider();
		ver_left.setMinHeight(500);
		ver_left.setOrientation(Orientation.HORIZONTAL);
		JFXSlider ver_right = new JFXSlider();
		ver_right.setMinHeight(500);
		ver_right.setOrientation(Orientation.VERTICAL);
		ver_right.setIndicatorPosition(IndicatorPosition.RIGHT);
		
		
		
		
		root.getChildren().addAll(hor_left, hor_right);
		//**************************************************
		JFXSpinner spinner = new JFXSpinner();
		
		root.getChildren().addAll(spinner);
		//**************************************************
		
		JFXToggleButton toggleButton = new JFXToggleButton();
		JFXToggleNode node = new JFXToggleNode();
		Text value = new Text("HEART");
		node.setGraphic(value);
		
		root.getChildren().addAll(node,toggleButton);
		//**************************************************
		
		JFXListView<Label> list = new JFXListView<Label>();
		for(int i = 0 ; i < 4 ; i++) list.getItems().add(new Label("Item " + i));
		list.getStyleClass().add("mylistview");
		
		root.getChildren().addAll(list);
		//**************************************************
		
		JFXDatePicker blueDatePicker = new JFXDatePicker();
		blueDatePicker.setDefaultColor(Color.valueOf("#3f51b5"));
		blueDatePicker.setOverLay(true);
		
		
		JFXColorPicker colorPicker = new JFXColorPicker();
		root.getChildren().addAll(colorPicker);
		
		/*StackPane pane = new StackPane();
		pane.getChildren().add(blueDatePicker);
		primaryStage.setScene(new Scene(pane, 600, 500));*/
		
		StackPane rootStackPane = new StackPane();
		Button button = new Button();
		button.setText("Dialog");
		JFXDialog dialog = new JFXDialog();
		dialog.setContent(new Label("Content"));
		button.setOnAction((action)->dialog.show(rootStackPane));
		
		rootStackPane.getChildren().addAll(button);
		//**************************************************
		Label content = new Label();
		JFXDecorator decorator = new JFXDecorator(primaryStage, content);
		decorator.setCustomMaximize(true);
		Scene scene = new Scene(decorator, 800, 850);
		
		
		JFXSnackbar snackbar = new JFXSnackbar(rootStackPane);
		snackbar.enqueue(new SnackbarEvent("Notification Msg"));
		
		
		root.getChildren().addAll(snackbar);
		//**************************************************
		
		
		
		primaryStage.setScene(new Scene(rootStackPane, 600, 500));
		//primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		
		
	}
}
