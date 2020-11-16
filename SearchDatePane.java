//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: It shows interface for date searching info input

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

	public class SearchDatePane extends StackPane
	{
		private Label yearLb, titleLb1, titleLb2; 
		private Label monthLb; 
		private Label dayLb; 
		private TextField yearField; 
	    private TextField monthField; 
	    private TextField dayField; 
	    private Button clearBtn;
	    
	    private boolean validAction;
	    private String invalidInfo;
	    
	    private int year, month, day;//int version for input checking or order method
	    private String yearText, monthText, dayText; //string version for display
	    
		
	  public SearchDatePane()
	  {
		  validAction = true;
		  invalidInfo = "Invalid Action. ";
		  year = 0;
		  month =0;
		  day = 0;
		  
		  yearText ="0000";
		  monthText ="00";
		  dayText = "00";
		  
		  
	    // Create a GridPane object and set its properties
	    GridPane pane = new GridPane();

	    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
	    
	    pane.setHgap(5.5);		//set horizontal gap between columns
	    pane.setVgap(5.5);		//set vertical gap between rows
	    
	      titleLb1 = new Label("Please enter the date to search (with day or without)");
	      titleLb2 = new Label("In following forms: Year: 2029; Month: 04; Day: 03");
	      yearLb = new Label("Year: ");
		  monthLb = new Label("Month: ");
		  dayLb = new Label("Day: ");
		  yearField = new TextField();
	      monthField = new TextField();
	      dayField = new TextField();
	      clearBtn = new Button("Clear");
	      
	    pane.add(titleLb1, 0, 0, 3, 1);
	    pane.add(titleLb2, 0, 1, 3, 1);
	    
	    pane.add(yearLb, 0, 2);
	    pane.add(yearField, 1, 2);

	    pane.add(monthLb, 0, 3);
	    pane.add(monthField , 1, 3);

	   
	    pane.add(dayLb, 0, 4);
	    pane.add(dayField, 1, 4);
	    
	    pane.add(clearBtn, 0, 5);
	    
	    clearBtn.setOnAction(new ButtonHandler());
	    
	    this.getChildren().addAll(pane);
	    this.setWidth(320);
	    this.setHeight(170);
	  }
	  
	  public void inputCheck()
	  {
		  validAction = true;
		  year = 0;
		  month =0;
		  day = 0;
		  
		  yearText ="0000";
		  monthText ="00";
		  dayText = "00";
			 
			 //check if the fields are valid values
		  if (dayField.getText().length() == 0 )
		  {
			  try {
					 year = Integer.parseInt(yearField.getText());
					 month = Integer.parseInt(monthField.getText()); 
				 }
			  catch(NumberFormatException e)
			 	{validAction = false;
			 	invalidInfo = "Invalid Action. Please enter a valid date to search";
			 	
			 	}
		  }	 
		  
		  else 
		  {
			  try {
					 year = Integer.parseInt(yearField.getText());
					 month = Integer.parseInt(monthField.getText()); 
					 day = Integer.parseInt(dayField.getText()); 
					 
				 }
			  catch(NumberFormatException e)
			 	{validAction = false;
			 	invalidInfo = "Invalid Action. Please enter a valid date to search";
			 	
			 	}
			  
		  }
			 
			 if(validAction == true )
			 {
				 
				 yearText = yearField.getText();
				 monthText = monthField.getText();
				 dayText = dayField.getText();
				 
				 if(yearText.length()!=4 || monthText.length()!=2 || (dayText.length()!=0 && dayText.length()!=2 ))
				 {
					 invalidInfo = "Invalid Action. Please enter a valid date to search";
					 validAction = false;
				 }
				 
				 if (yearText.length()!=4)
				 {
					 invalidInfo = "Invalid Action. Please enter a valid year to search";
					 validAction = false;
					
				 }
				 else if (year < 2020 || year > 2299 )
				 {
					 invalidInfo = "Invalid Action. Please enter a year between 2020 and 2299 ";
				 		validAction = false;
				 		
				 }
				 
				 else if (month<1 || month>12)
				 {
					 invalidInfo = "Invalid Action. Please enter a valid month ";
				 		validAction = false;
				 		
				 }
				if (dayText.length()!=0) //if day is not empty
				{
					 if (day >31 || day< 0)
					 {
						 invalidInfo = "Invalid Action. Please enter a valid day";
					 		validAction = false;
					 		
					 }
					 else if ((month == 4||month == 6||month == 9||month == 11) && day > 30)
					 {
						 invalidInfo = "Invalid Action. The month you choose only have 30 days. Please re-enter date ";
					 		validAction = false;
					 		
					 }
					 else if (month == 2 && day > 29)
					 {
						 invalidInfo = "Invalid Action. Feburary can have at most 29 days. Please re-enter date";
						 		validAction = false;
						 		
					 }
					 else if (month == 2 && day == 29 && year%4 !=0)
					 {
						 invalidInfo = "Invalid Action. Feburary of " + year + "has only 28 days";
						 		validAction = false;			 		
					 }
				}
				
			 }
			 
	  }
	  
	  public String getInvalidInfo()
	  	{
	  		return invalidInfo;
	  	}
	  
	  public boolean getValidStatus()
	  {
		  return validAction;
	  }
	  
	  public int getMonth()
	  {
		  return month;
	  }
	  
	  public int getYear()
	  {
		  return year;
	  }
	  
	  public int getDay()
	  {
		  return day;
	  }
	  
	  public String getMonthT()
	  {
		  return monthText;
	  }
	  
	  public String getYearT()
	  {
		  return yearText;
	  }
	  
	  public String getDayT()
	  {
		  return dayText;
	  }
	  
	  private class ButtonHandler implements EventHandler<ActionEvent>
		 {
		    
			    public void handle(ActionEvent e)
		     {
					Object source = e.getSource();
					
					if (source == clearBtn)
					{
						yearField.clear();
						  monthField.clear();
						  dayField.clear();		 
						 
					}
		     }
		    
	  
		}


	}
	  

	  




