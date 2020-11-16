
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: It shows interface for name searching info input
public class SearchNamePane extends StackPane{
		private Label iNameLb;
		private TextField iNameField;

	    
	    private boolean validAction;
	    private String invalidInfo;
	    
	     
		
	  public SearchNamePane()
	  {
		  validAction = false;
		  invalidInfo = "Invalid Action. ";
		  
		  
	    // Create a GridPane object and set its properties
	    GridPane pane = new GridPane();
	    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
	    

	     iNameLb = new Label("Please enter a name to search: ");
	     iNameField = new TextField();
	     

	    pane.add(iNameLb, 0, 0);
	    pane.add(iNameField, 1, 0);
	  

	    
	    this.getChildren().addAll(pane);
	    this.setWidth(400);
	    this.setHeight(50);
	  }
	  
	  
	  public String getName()
	  {
		  return iNameField.getText();
	  }
	  
	  public boolean getAction()
	  {
		  return validAction;
	  }
	  

}
