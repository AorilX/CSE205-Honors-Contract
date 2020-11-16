import java.text.DecimalFormat;
import java.util.InputMismatchException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;	//***
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: The pane shows interface for editing/adding items;  
public class AddNewPane extends StackPane
{
	//instance vairables
	private Label iNameLb, titleLb1, titleLb2;
	private Label amountLb ;
	private Label dateLb ;
	private TextField iNameField;
    private TextField amountField;
    private TextField dateField;
    private Button clearBtn;
    
    private boolean validAction;
    private String invalidInfo;
    
    private String newDate, newName;
    private double newAmount;
	
  //constructor
  public AddNewPane()
  {
	  		//initialization
		  validAction = true;
		  invalidInfo = "Invalid Action. ";
		  newDate ="";
		  newName ="";
		  newAmount =0.00;
	  
		 titleLb1 = new Label("Please enter your item information");
		 titleLb2 = new Label("Date in the form of: 03042029");
	     iNameLb = new Label("Item Name:");
	     amountLb = new Label("Amount($):");
	     dateLb = new Label("Date:");
		 iNameField = new TextField();
	     amountField = new TextField();
	     dateField = new TextField();
	     clearBtn = new Button("Clear");
	  
	  
    // Create a GridPane object and set its properties
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);	//always place the GridPane in the center of scene

    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    
    pane.setHgap(5.5);		//set horizontal gap between columns
    pane.setVgap(5.5);		//set vertical gap between rows
    
    
    
     iNameLb = new Label("Item Name:");
     amountLb = new Label("Amount($):");
     dateLb = new Label("Date:");
  
     iNameField = new TextField();
     amountField = new TextField();
     dateField = new TextField();

     //put nodes into the GridPane
     pane.add(titleLb1, 0, 0, 2, 1);
     pane.add(titleLb2, 0, 1, 2, 1);
     pane.add(iNameLb, 0, 2);
     pane.add(iNameField, 1, 2);

     pane.add(amountLb, 0, 3);
     pane.add(amountField , 1, 3);

   
     pane.add(dateLb, 0, 4);
     pane.add(dateField, 1, 4);
    
     pane.add(clearBtn, 0, 5);
    
     //regstration
     clearBtn.setOnAction(new ButtonHandler());
    
    //put gridPane on stackPane
    this.getChildren().addAll(pane);
    this.setWidth(300);
    this.setHeight(350);
  }
  
  //check if input is valid; if not, set an invalid info;
  public void inputCheck()
  {
	  validAction = true;
		 
		 //check if the fields are valid values
	     //amount should be a double; date should be in the form of 03032020; name is a string
	     //none item can be empty
	  
	     //at beginning, validAction is set to be true; when fail to pass any condition, it changes to false, indicating the check failed.
		 try {
			 //check if each field has values of certain type
			 newAmount = Double.parseDouble(amountField.getText());
			 newDate = dateField.getText();
			 newName = iNameField.getText();
		 }
			 catch(NumberFormatException e)
			 	{validAction = false;
			 	invalidInfo = "Invalid Action. Please enter a valid value for Amount or Date ";
			 	
			 	}
		 
		 if(validAction == true)
		 {
			 
			 int month = 0, date = 0, year = 0;
			 //check decimal places of amount, it can has at most 2 decimal places
			 String text = amountField.getText();
			 int integerPlaces = text.indexOf('.');
			 int decimalPlaces;
			 if(integerPlaces == -1)
			 {
				  decimalPlaces = 0;
			 }
			 else
				 decimalPlaces = text.length() - integerPlaces - 1;
			 //check date; it shuld be 
			 String text2 = dateField.getText();
				 int dateDigits= text2.length();
			 if (dateDigits == 8)
			 {
				  month = Integer.parseInt(text2.substring(0, 2));
				  date = Integer.parseInt(text2.substring(2, 4));
				  year = Integer.parseInt(text2.substring(4, 8));
			 }
			 
			 if (iNameField.getText().length()==0)
			 {
				 invalidInfo = "Invalid Action. Please enter a name for the item";
				 validAction = false;
				
			 }
			//check if the amount is at most 2 decimals
			 else if (decimalPlaces > 2)
			 {
				 invalidInfo = "Invalid Action. Amount can have at most 2 deciaml places. Please re-enter amount";
				 validAction = false;
				
			 }
			 //amount cannot be 0
			 else if (newAmount == 0)
			 {
				 invalidInfo = "Invalid Action. Please enter a nonzero amount";
				 validAction = false;
				 
			 }
			 //check if date is valid
			 else if (month<1 || month>12)
			 {
				 invalidInfo = "Invalid Action. Please enter a valid month ";
			 		validAction = false;
			 		
			 }
			 else if (year < 2020 || year > 2299 )
			 {
				 invalidInfo = "Invalid Action. Please enter a year between 2020 and 2299 ";
			 		validAction = false;
			 		
			 }
			 else if (date >31 ||date<1)
			 {
				 invalidInfo = "Invalid Action. Please enter a valid day";
			 		validAction = false;
			 		
			 }
			 else if ((month == 4||month == 6||month == 9||month == 11) && date > 30)
			 {
				 invalidInfo = "Invalid Action. The month you choose only have 30 days. Please re-enter date ";
			 		validAction = false;
			 		
			 }
			 else if (month == 2 && date > 29)
			 {
				 invalidInfo = "Invalid Action. Feburary can have at most 29 days. Please re-enter date";
				 		validAction = false;
				 		
			 }
			 else if (month == 2 && date == 29 && year%4 !=0)
			 {
				 invalidInfo = "Invalid Action. Feburary of " + year + "has only 28 days";
				 		validAction = false;			 		
			 }
			 
		 }
  }
  
  //it creates and returns an Items object when input is valid
  public Items addItem()
  {
	  	if (validAction == false)
	  	{
	  		return null;
	  	}
	  	else if(validAction == true)
		 {
	  		 newAmount = Double.parseDouble(amountField.getText());
			 newDate = dateField.getText();
			 newName = iNameField.getText(); 
		     Items newItem = new Items(newName, newDate, newAmount);
		     //iNameField.clear();
			 //amountField.clear();
			 //dateField.clear();
		     return newItem;
		 }	 
	 
	 return null;
  }
  
  //set interface to show info of selected item; 
  public void editItemPane(String name, double amount, String date)
  {
	  iNameField.setText(name);
	  amountField.setText(String.valueOf(amount));
	  dateField.setText(date); 		 
	 
  }
  
  //returns invalid info
  public String getInvalidInfo()
  	{
  		return invalidInfo;
  	}
  
  //it controls button event
  private class ButtonHandler implements EventHandler<ActionEvent>
	 {
	    
	    public void handle(ActionEvent e)
     {
			Object source = e.getSource();
			
			//when clear button is clicked, clear all TextFields
			if (source == clearBtn)
			{
				iNameField.clear();
				  amountField.clear();
				  dateField.clear();		 
				 
			}
     }
	    
  
	}


}
  

  


