package TEST;

import javafx.application.Application;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        //Application.launch(args);
    	String stockInfo = "222";
    	String stockID;
    	String[] stock = stockInfo.split("\\|");
		if(stock.length > 1){
			stockID = stock[1];
		}else{
			stockID = stock[0];
		}
		System.out.println(stockID);
    }

    @Override
    public void start(Stage primaryStage) {
    	//禁用全部按钮（最大化，最小化，关闭）
        primaryStage.initStyle(StageStyle.UNDECORATED);
    	
    	//禁用最大化，最小化
    	//primaryStage.initStyle(StageStyle.UTILITY);
    	
    	//禁用最大化
    	//primaryStage.resizableProperty().setValue(Boolean.FALSE);

        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}