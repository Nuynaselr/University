package guiFinal;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SavesChange {
       private boolean saves = false;

       public boolean winSavesChange(Stage stage){


              AnchorPane win = new AnchorPane();

              Text valueText = new Text("Do you want to save the file?");
              Button buttonYes = new Button("Yes");
              Button buttonNo = new Button("No");

              AnchorPane.setLeftAnchor(valueText, 27.0);
              AnchorPane.setTopAnchor(valueText, 50.0);

              AnchorPane.setLeftAnchor(buttonYes, 77.0);
              AnchorPane.setTopAnchor(buttonYes, 114.0);

              AnchorPane.setLeftAnchor(buttonNo, 260.0);
              AnchorPane.setTopAnchor(buttonNo, 114.0);

              valueText.setFont(new Font("System Bold",22));
              buttonYes.setFont(new Font("System Bold",16));
              buttonNo.setFont(new Font("System Bold",16));

              win.getChildren().addAll(valueText, buttonYes, buttonNo);

              buttonYes.setOnAction(event -> {
                     saves = true;
                     stage.hide();
              });
              buttonNo.setOnAction(event -> {
                     stage.hide();
              });

              Scene winCreateFunction = new Scene(win,416, 170);
              stage.setTitle("Functions");
              stage.setScene(winCreateFunction);
              stage.setResizable(false);
              stage.showAndWait();
              return saves;
       }
}
