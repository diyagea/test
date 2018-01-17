package TableExample;

/**
 * 实现表格的序号列的计算填充，
 * 实现按钮操作列的正常显示以及事件绑定
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("FXTable.fxml"));
            // 为表格设置控制器
            loader.setController(new TableController());
            AnchorPane rootPane = loader.load();
            Scene scene = new Scene(rootPane,506,460);
            primaryStage.sizeToScene();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}