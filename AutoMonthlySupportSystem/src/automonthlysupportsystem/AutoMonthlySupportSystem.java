/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automonthlysupportsystem;

import core.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.OraDbConnection;
import view.MainView;

/**
 *
 * @author Anik
 */
public class AutoMonthlySupportSystem extends Application {
    
    @Override
    public void start(Stage primaryStage) {
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                //new Controller().updateTableSpace(OraDbConnection.connection());
//                for (String table: new Controller().getTableList(OraDbConnection.connection())) {
//                    System.out.println("> "+table);
//                }
//                System.out.println("Update.... !");
//                
//                new Controller().updateTableIndex(OraDbConnection.connection());
//                System.out.println(" < ================================ >\n");
//                new Controller().rebuildTableScript(OraDbConnection.connection());
//                
//            }
//        });
//        
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        
//        Scene scene = new Scene(root, 300, 250);
//        
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();

new MainView().setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
