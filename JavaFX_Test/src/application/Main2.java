package application;


import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * stackedBarChart Show
 * @author diyagea- Allen.Wang
 *
 */
public class Main2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
    	
        Group root = new Group();
           
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
           
        xAxis.setLabel("TDM Tools");
        xAxis.setCategories(FXCollections.<String> observableArrayList(Arrays.asList(
                "Tool-001", 
                "Tool-002",
                "Tool-003", 
                "Tool-004",
                "Tool-005")));
        yAxis.setLabel("Tool Life Value");
           
        final StackedBarChart<String,Number> stackedBarChart = new StackedBarChart<String,Number>(xAxis,yAxis);
        stackedBarChart.setTitle("Statistics TOP5");
          
        XYChart.Series<String,Number> series1 = new XYChart.Series();
        series1.setName("Tool Left Life");
           
        series1.getData().add(new XYChart.Data("Tool-001", 80));
        series1.getData().add(new XYChart.Data("Tool-002", 69));
        series1.getData().add(new XYChart.Data("Tool-003", 63));
        series1.getData().add(new XYChart.Data("Tool-004", 52));
        series1.getData().add(new XYChart.Data("Tool-005", 45));
          
        XYChart.Series<String,Number> series2 = new XYChart.Series();
        series2.setName("Tool Usage Life");
           
        series2.getData().add(new XYChart.Data("Tool-001", 20));
        series2.getData().add(new XYChart.Data("Tool-002", 31));
        series2.getData().add(new XYChart.Data("Tool-003", 37));
        series2.getData().add(new XYChart.Data("Tool-004", 48));
        series2.getData().add(new XYChart.Data("Tool-005", 55));
          
        stackedBarChart.getData().addAll(series1, series2);
               
        root.getChildren().addAll(stackedBarChart);
        
        primaryStage.setTitle("TDM Tool Waste Statistics");
        
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }
}
