import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;	
import javafx.event.EventHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: It defines the main interface; it has two button handler; it reads items from a file and saves itemList to a file when closing;
//			   
public class ItemsList extends Application
{
	   //variables and Gui components
	    ArrayList<GridPane> originalList;
	    ObservableList<GridPane> budgetData;
	    ListView budgetLV;
	    int selectedIndex, tempIndex;
	    AddNewPane addNew1;
	    SearchNamePane searchNamePane;
	    SearchDatePane searchDatePane;
	    
	    Label actionInfo, totalAmount;
	    Button removeButton, editBtn, add1Btn, orderBtn, searchNameBtn, searchDateBtn, showAll, orderByRecency;
	    Button addNewOkBtn, confirmRemove, editOkBtn, searchNameOk, searchDateOk;
	    
	    
	    ArrayList<Items> itemList,tempList; // itemList saves whole list; templist is a partial of itemList when searched out
	    GridPane selectedItem; 
	    GridPane gridpane1; 
	    GridPane titlePane;   
	    
	    ItemManagement manage1 = new ItemManagement();
	    DecimalFormat fmt = new DecimalFormat("$0.00");

	   public void start(Stage primaryStage)
	   {
	      //Step #1: initialize every instance variable and set up the layout
	      actionInfo = new Label("This is Your List");
	      totalAmount = new Label("");
	      totalAmount.setFont(new Font("Arial", 20));
	      totalAmount.setStyle("-fx-font-weight: bold");
	      
	      add1Btn = new Button("Add");
	      removeButton = new Button("Remove");
	      editBtn = new Button("Edit"); 
	      searchNameBtn = new Button("Search by Name");
	      searchDateBtn = new Button("Search by Date");
	      showAll = new Button("ShowAll");
	      orderByRecency = new Button("OrderByRecency");
	      
	      addNew1 = new AddNewPane();	      
	      itemList = new ArrayList<Items>();
	      gridpane1 = new GridPane();
	      titlePane = new GridPane();
	      
	      tempList = new ArrayList<Items>();
	      
	      
	      //set style for two gridpane (one for title, one for items info)
	      for (int j = 0; j < 3; ++j) {

	    	  ColumnConstraints cc = new ColumnConstraints();
	    	  cc.setPercentWidth(100/3);
	    	  gridpane1.getColumnConstraints().add(cc);
	    	  titlePane.getColumnConstraints().add(cc);
	    	}
	      
	      //create labels for title pane and put it within the gridpane
	      Label name1 = new Label("ItemName");
	      Label amount1= new Label("Amount");
	      Label date1= new Label("Date");
	      
	      titlePane.add(actionInfo, 0,0, 3,1);
	      titlePane.add(name1, 0, 1);
	      titlePane.add(amount1, 1, 1);
	      titlePane.add(date1, 2, 1);
	      
	      //originalList is an ArrayList data structure, we will use it
	      //to create an ObservableList first, then from ObservableList, to
	      //create a ListView object
	      originalList = new ArrayList<GridPane>();
	      originalList.add(gridpane1);

	      //(1)create an ObservableList from above ArrayList
	      budgetData = FXCollections.observableArrayList(originalList);

	      //(2)create a ListView from above ObservableList
	      budgetLV = new ListView<>(budgetData);

	      //(3)set up ListView's selection mode
		  budgetLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		  
		  //initialize the selected index and selected country
		  selectedIndex = 0;
		  selectedItem = originalList.get(selectedIndex);
		  
		  //read itemList from saved files
		  try {
				FileInputStream fis = new FileInputStream("Items");
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object obj1 = ois.readObject();
				if (obj1 instanceof ArrayList)
				{
					itemList = (ArrayList<Items>) obj1;
					totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
					
					System.out.print("Items was read\n");
				}
				
		  }
			catch(ClassNotFoundException e) {
				System.out.print("Class not found exception\n");
			}

			catch(NotSerializableException e) {
				System.out.print("Not serializable exception\n");
			}
			catch(IOException e) {
				System.out.print("Data file read exception\n");
			}
		  //clear LV
		  originalList.clear();
		  budgetData.clear();
		  
		  //add each item info to the LV as a gridpane
		  if (itemList.size() > 0)
		  {
			  for (int i = 0; i <itemList.size(); i++)
			  {
				  	GridPane itemPane = manage1.setItem(itemList, i);
	 			 	originalList.add(itemPane);
					budgetData.add(itemPane);
			  }
		  }
		//if the file is empty, show the following information; else, compute total amount 
		if (itemList.size() < 1)
		      {
		    	  Label firstInfo = new Label("Please click \"Add\" to start");
		    	  gridpane1 = new GridPane();
		    	  gridpane1.add(firstInfo, 0, 1);
		    	  originalList.add(0,gridpane1);
				  budgetData.add(0,gridpane1);
		      }
			  else
			  {
				  manage1.computeTotal(itemList);
				  totalAmount.setText("Total Amount: "+ fmt.format(manage1.getTotal()));
			  }
			  
		  //select the first item as default
		  budgetLV.getSelectionModel().select(0);
		  

		  //set up the GUI layout
	      GridPane bottomPane = new GridPane();
	      bottomPane.setPadding(new Insets(12, 12, 12, 12));
	      bottomPane.setAlignment(Pos.BASELINE_CENTER);
	      bottomPane.setHgap(6);
	      bottomPane.setVgap(6);
	      
	      bottomPane.add(totalAmount, 0, 0, 1,4);	      
	      bottomPane.add(removeButton, 1, 3);
	      bottomPane.add(editBtn, 2, 3);
	      bottomPane.add(add1Btn, 3,3);
	      bottomPane.add(showAll, 4,3);
	      bottomPane.add(searchNameBtn, 1,1,2,1);
	      bottomPane.add(searchDateBtn, 3, 1, 2, 1);
	      bottomPane.add(orderByRecency, 1, 0, 2, 1);

	      BorderPane pane = new BorderPane();
	      pane.setMaxSize(200, 250);
	      pane.setTop(titlePane);
	      pane.setCenter(budgetLV);
	      pane.setBottom(bottomPane);

	      // Register the buttons with its handler
	      removeButton.setOnAction(new ButtonHandler());
	      editBtn.setOnAction(new ButtonHandler());
	      add1Btn.setOnAction(new ButtonHandler());
	      searchNameBtn.setOnAction(new ButtonHandler());
	      searchDateBtn.setOnAction(new ButtonHandler());
	      showAll.setOnAction(new ButtonHandler());
	      orderByRecency.setOnAction(new ButtonHandler());

	     // Create a scene and place it in the stage
		 Scene scene = new Scene(pane, 500, 570);
		 primaryStage.setTitle("ListView Event Demo");
		 primaryStage.setScene(scene); // Place the scene in the stage
	     primaryStage.show(); // Display the stage
	     
	     //when it is closing, save items info
	     primaryStage.setOnCloseRequest(event -> {
	    	    
	    	    // Save file
	    	    
	    	    try {
	    	    	File file = new File("Items");
	    	    	FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(itemList);
					
	    	    }
	    	    catch(NotSerializableException e) 
				{
	    	    	System.out.print("Not serializable exception\n");
				}
	    	    catch(IOException e) {
	    	    	System.out.print("Data file written exception\n");
	    	    }
	    	});

	   }	   
	   
	  
		//Create a ButtonHandler class
	    private class ButtonHandler implements EventHandler<ActionEvent>
	   	 {
	   	    //Override the abstact method handle()
	   	    public void handle(ActionEvent e)
	        {
				Object source = e.getSource();
				
	 			//If one item is selected and the remove button is pressed
				 if (budgetData.size() >=1 && source == removeButton && selectedItem != null )
	 			{
					//check which item is selected from the ListView
	 					selectedIndex = budgetLV.getSelectionModel().getSelectedIndex();
						selectedItem = originalList.get(selectedIndex);
						
						if (itemList.size()>0) //check if there is an item in the list
						{
							
							//the window asks if remove will be confirmed or canceled
							Dialog<ButtonType> confirmRemoveWindow = new Dialog<ButtonType>();
							confirmRemoveWindow.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
							confirmRemoveWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
			 				confirmRemove = (Button)confirmRemoveWindow.getDialogPane().lookupButton(ButtonType.OK);
			 			
			 				confirmRemoveWindow.setWidth(1000);
			 				confirmRemoveWindow.setHeight(100);
			 				confirmRemoveWindow.setTitle("Confirm");
			 				if (itemList.size() == budgetData.size())//when the whole list is shown
			 				{
			 					confirmRemoveWindow.setContentText("Are you sure you want to remove " + itemList.get(selectedIndex).getName() + " ?");
			 				}
			 				else //when the list showing is not the whole list(It happens when it only shows searched items)
			 				{
			 					confirmRemoveWindow.setContentText("Are you sure you want to remove " + tempList.get(selectedIndex).getName() + " ?");
			 					tempIndex = tempList.get(selectedIndex).getTempIndex();
			 				}
			 				
			 				confirmRemove.setOnAction(new OkBtnHandler());
			 				confirmRemoveWindow.show();
						}
						else //any other invalid cases
			 			{
			 				actionInfo.setTextFill(Color.RED);
			 				actionInfo.setText("Invalid Action. There is no item to remove");
			 			}
						
		 				
	 			}
				//If one item is selected and the edit button is pressed
	 			else if (source == editBtn && selectedItem != null )
	 			{
	 				//get index
	 				selectedIndex = budgetLV.getSelectionModel().getSelectedIndex();
					selectedItem = originalList.get(selectedIndex);
					
					//prompt a window with a "editpane"
					if(itemList.size()>0)
					{
						actionInfo.setTextFill(Color.BLUE);
		 				actionInfo.setText(itemList.get(0).getName() + " is updated successfully.");
						
						Dialog<ButtonType> editWindow = new Dialog<ButtonType>();
						editWindow.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		 				editWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
		 				editOkBtn = (Button)editWindow.getDialogPane().lookupButton(ButtonType.OK);
		 				
		 				editWindow.getDialogPane().getChildren().add(addNew1);
		 				editWindow.setWidth(400);
		 				editWindow.setHeight(500);
		 				editWindow.setTitle("Edit Item");
		 			
		 				editOkBtn.setOnAction(new OkBtnHandler());
		 				
		 				if (itemList.size() == budgetData.size()) // when whole list is showing
		 				{
		 					String date = itemList.get(selectedIndex).getDate();
			 				String name = itemList.get(selectedIndex).getName();
			 				double amount = itemList.get(selectedIndex).getAmount();
			 				addNew1.editItemPane(name, amount, date);
		 				}
		 				else //when the list showing is not the whole list(It happens when it only shows searched items)
		 				{
		 					tempIndex = tempList.get(selectedIndex).getTempIndex();
		 					String date = itemList.get(tempIndex).getDate();
			 				String name = itemList.get(tempIndex).getName();
			 				double amount = itemList.get(tempIndex).getAmount();
			 				addNew1.editItemPane(name, amount, date);
		 				}
		 					
		 				editWindow.show();
					}
					else //if the list is empty
					{
						actionInfo.setText("Please add an item first");
	  					actionInfo.setTextFill(Color.RED);
					}
					
					
					
	 			}
				//if add item btn is clicked
	 			else if (source == add1Btn)
	 			{
	 				//show the window of adding items
	 				Dialog<ButtonType> addNewWindow = new Dialog<ButtonType>();
	 				addNewWindow.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
	 				addNewWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
	 				addNewOkBtn = (Button)addNewWindow.getDialogPane().lookupButton(ButtonType.OK);
	 				
	 				addNewWindow.getDialogPane().getChildren().add(addNew1);
	 				addNewWindow.setWidth(400);
	 				addNewWindow.setHeight(500);
	 				addNewWindow.setTitle("Add New Item");
	 				
	 				addNewOkBtn.setOnAction(new OkBtnHandler());
	 				
	 				addNewWindow.show();
	 				
	 			}
				//if choose to search by name
	 			else if (source == searchNameBtn)
				{
					if (itemList.size()>0)
					{
						//show a window where name can be entered 
						Dialog<ButtonType> searchNameWindow = new Dialog<ButtonType>();
						searchNameWindow.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
						searchNameWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
						searchNameOk = (Button)searchNameWindow.getDialogPane().lookupButton(ButtonType.OK);
		 				searchNamePane = new SearchNamePane();
		 				
		 				
		 				searchNameWindow.getDialogPane().getChildren().add(searchNamePane);
		 				searchNameWindow.setWidth(700);
		 				searchNameWindow.setHeight(100);
		 				searchNameWindow.setTitle("Search by Name");
		 				
		 				searchNameOk.setOnAction(new OkBtnHandler());
		 				searchNameWindow.show();
					}
					else //when list is empty
					{
						actionInfo.setText("There is no item to search. Please add an item first");
	  					actionInfo.setTextFill(Color.RED);
					}
				}
				 
				//when choose to search by date
	 			else if (source ==searchDateBtn)
	 			{
	 				if (itemList.size()>0)
					{
	 					//show a window where date can be entered
						Dialog<ButtonType> searchDateWindow = new Dialog<ButtonType>();
						searchDateWindow.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
						searchDateWindow.getDialogPane().getButtonTypes().add(ButtonType.OK);
						searchDateOk = (Button)searchDateWindow.getDialogPane().lookupButton(ButtonType.OK);
		 				searchDatePane = new SearchDatePane();
		 				
		 				
		 				searchDateWindow.getDialogPane().getChildren().add(searchDatePane);
		 				searchDateWindow.setWidth(260);
		 				searchDateWindow.setHeight(250);
		 				searchDateWindow.setTitle("Search by Date");
		 				
		 				searchDateOk.setOnAction(new OkBtnHandler());
		 				searchDateWindow.show();
					}
					else // if list is empty
					{
						actionInfo.setText("There is no item to search. Please add an item first");
	  					actionInfo.setTextFill(Color.RED);
					}
	 			}
				 
				//show the whole list
	 			else if (source == showAll) 
	 			{
	 				if (itemList.size() == 0)
	 				{
	 					actionInfo.setText("Please add an item first");
	  					actionInfo.setTextFill(Color.RED);
	 				}
	 				else if (itemList.size() == budgetData.size())
	 				{
	 					actionInfo.setText("This is your whole list");
	  					actionInfo.setTextFill(Color.RED);
	 				}
	 				else if (itemList.size() > budgetData.size())
	 				{
	 					//add whole list to LV and counts its total amount
	 					originalList.clear();
  					    budgetData.clear();
  					  for (int i = 0; i < itemList.size(); i++)
  					  {
  						  GridPane newPane = manage1.setItem(itemList, i);
  						  originalList.add(newPane);
	    					  budgetData.add(newPane);
  					  }
  					  manage1.computeTotal(itemList);
  					  totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
  					  budgetLV.getSelectionModel().select(0);
  					  actionInfo.setTextFill(Color.BLUE);
			 				actionInfo.setText("This is your whole list");
	 				}
	 			}
				// choose to order by Recency
	 			else if (source == orderByRecency)
	 			{
	 				if (itemList.size()>0) 
	 				{
	 					// if only part of list is shown
	 					if (itemList.size() > budgetData.size() && tempList.size()>0)
	 					{
	 						for (int i =0; i< tempList.size();i++)
	 						{
	 							tempList.get(i).setDateInfo();
	 						}
	 						//the method reorder the partial list;
	 						manage1.orderByDate(tempList);
	 						//set the ordered info to the interface
	 						originalList.clear();
	  					    budgetData.clear();
			  					  for (int i = 0; i < itemList.size(); i++)
			  					  {
			  						  GridPane newPane = manage1.setItem(itemList, i);
			  						  originalList.add(newPane);
				    					  budgetData.add(newPane);
			  					  }
	 					}
	 					else // if whole list is shown; reorder the whole list
	 					{
	 						for (int i =0; i< itemList.size();i++)
	 						{
	 							itemList.get(i).setDateInfo();
	 						}
	 						manage1.orderByDate(itemList);
	 						
	 						originalList.clear();
	  					    budgetData.clear();
			  					  for (int i = 0; i < itemList.size(); i++)
			  					  {
			  						  GridPane newPane = manage1.setItem(itemList, i);
			  						  originalList.add(newPane);
				    					  budgetData.add(newPane);
			  					  }
	 					}
	 					
	 					
	 				}	
	 				else // if on items in list
	 				{
	 					actionInfo.setText("Please add an item first");
	  					actionInfo.setTextFill(Color.RED);
	 				}
	 			}
	 			else   //all other invalid actions
	 			{
	 				actionInfo.setTextFill(Color.RED);
	 				actionInfo.setText("Invalid Action");
	 			}
		 	 } //end of handle()
	     }//end of ButtonHandler class
	    
	    private class OkBtnHandler implements EventHandler<ActionEvent> //for Ok buttons of prompt windows
	    {
	    	public void handle(ActionEvent e)
	    	{
	    		Object source = e.getSource();
	    		//when info of new item is entered and ok is clicked
	    		//for each invalid/successful action, there is a warning at the top
	    		if (source == addNewOkBtn)
	    		{
	    			addNew1.inputCheck();//check if input is valid
	    			Items tempItem = addNew1.addItem(); // if inout is not valid, it returns null
	  				 //check if the item is added
	  				 if (tempItem == null)
	  				 {
	  					actionInfo.setText(addNew1.getInvalidInfo());
	  					actionInfo.setTextFill(Color.RED);
	  				 }
	  				 else //input is valid and items object is created
	  				 {
	  					//add it to LV and itemlist
	  					itemList.add(0,addNew1.addItem());
	  					manage1.computeTotal(itemList);
		 				totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
		    			 gridpane1 = manage1.setItem(itemList, 0);
		    			 originalList.add(0,gridpane1);
		  				 budgetData.add(0,gridpane1);
		  				 //removed default info
	  					if(itemList.size() ==1)
		  				 {
		  					originalList.remove(1);
			 				budgetData.remove(1);
		  				 }
		  				 
		  				actionInfo.setTextFill(Color.BLUE);
		 				actionInfo.setText(itemList.get(0).getName() + " is added successfully.");
	  				 }	 
	    		}
	    		//if ok is pressed, it is confirmed that 
	    		if (source == confirmRemove)
	    		{	
	    			if (itemList.size()==budgetData.size())// when whole list is shown
	    			{
	    				String itemName = itemList.get(selectedIndex).getName();
		 				originalList.remove(selectedIndex);
		 				budgetData.remove(selectedIndex);
		 				itemList.remove(selectedIndex);
		 				manage1.computeTotal(itemList);
		 				totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
		 				actionInfo.setTextFill(Color.BLUE);
		 				actionInfo.setText(itemName + " is removed successfully.");
	    			}
	    			
	    			else //when partial list is shown.(since index of items in LV != index in whole list; however, when partial list is created
	    				 //it saves the index of the item as tempIndex variable, indicating its position in the whole list)
	    			{
	    				String itemName = tempList.get(selectedIndex).getName();
	    				tempIndex = tempList.get(selectedIndex).getTempIndex();
		 				originalList.remove(selectedIndex);
		 				budgetData.remove(selectedIndex);
		 				tempList.remove(selectedIndex);
		 				itemList.remove(tempIndex);
		 				manage1.computeTotal(tempList);
		 				totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
		 				actionInfo.setTextFill(Color.BLUE);
		 				actionInfo.setText(itemName + " is removed successfully.");
	    			}
	 				
	 				if(budgetData.size()==0) //if the last item is removed, show default info
	 				{
	 					Label firstInfo = new Label("Please click \"Add\" to start");
	 			    	  gridpane1 = new GridPane();
	 			    	  gridpane1.add(firstInfo, 0, 1);
	 			    	  originalList.add(0,gridpane1);
	 					  budgetData.add(0,gridpane1);
	 					 budgetLV.getSelectionModel().select(0);
	 				}
	 				
	    		}
	    		//when searchOk is pressed in that window, it shows searching result
	    		if (source == searchNameOk)
	    		{
	    			String newName = searchNamePane.getName();
	    			if (newName.length()>0)
	    			{
	    				tempList = manage1.searchName(itemList, newName);
	    				if (tempList.size() > 0 && tempList.size() < itemList.size())
	    				{
	    					originalList.clear();
	    					  budgetData.clear();
	    					  for (int i = 0; i < tempList.size(); i++)
	    					  {
	    						  GridPane newPane = manage1.setItem(tempList, i);
	    						  originalList.add(newPane);
		    					  budgetData.add(newPane);
	    					  }
	    					  manage1.computeTotal(tempList);
	    					  totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
	    					  budgetLV.getSelectionModel().select(0);
	    					  actionInfo.setTextFill(Color.BLUE);
				 				actionInfo.setText("This is the list of items named " + newName);
	    					  
	    				}
	    				
	    				else if (tempList.size() == 0)
	    				{
	    					actionInfo.setTextFill(Color.RED);
			 				actionInfo.setText("This is NO item named " + newName);
	    				}
	    				
	    				else if (itemList.size()!=0)
	    				{
	    					actionInfo.setTextFill(Color.BLUE);
			 				actionInfo.setText("This is the list of items named " + newName);
	    				}
	    				
	    			}
	    			
	    			else 
	    			{
	    				actionInfo.setText("Invalid Action");
	  					actionInfo.setTextFill(Color.RED);
	    			}
	    		}
	    		//when searchOk is pressed in that window, it shows searching result
	    		if (source == searchDateOk)
	    		{
	    			searchDatePane.inputCheck();
	    			if(searchDatePane.getValidStatus())
	    			{
	    				int year = searchDatePane.getYear();
	    				int month = searchDatePane.getMonth();
	    				int day = searchDatePane.getDay();
	    				
	    				String yeartext = searchDatePane.getYearT();
	    				String monthtext = searchDatePane.getMonthT();
	    				String daytext = searchDatePane.getDayT();
	    				
	    				if (day == 0)//check if day is empty
	    				{
	    					tempList = manage1.searchDateWithoutDay(itemList, year, month);
	    				}
	    				else 
	    				{
	    					tempList = manage1.searchDateWithDay(itemList, year, month, day);
	    				}
	    				
	    				if (tempList.size() > 0 && tempList.size() < itemList.size())
	    				{
	    					originalList.clear();
	    					  budgetData.clear();
	    					  for (int i = 0; i < tempList.size(); i++)
	    					  {
	    						  GridPane newPane = manage1.setItem(tempList, i);
	    						  originalList.add(newPane);
		    					  budgetData.add(newPane);
	    					  }
	    					  manage1.computeTotal(tempList);
	    					  totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
	    					  budgetLV.getSelectionModel().select(0);
	    					  actionInfo.setTextFill(Color.BLUE);
	    					  if (day == 0)
				 				actionInfo.setText("This is the list of items on " + monthtext + "##" + yeartext);
	    					  else
	    						  actionInfo.setText("This is the list of items on " + monthtext + daytext + yeartext);
	    					  
	    				}
	    				
	    				else if (tempList.size() == 0)
	    				{
	    					actionInfo.setTextFill(Color.RED);
	    					if (day == 0)
	    						actionInfo.setText("This is NO item on " + monthtext + "##" + yeartext);
	    					else
	    						actionInfo.setText("This is NO item on " + monthtext + daytext + yeartext);
	    				}
	    				
	    				else if (tempList.size()==itemList.size() && itemList.size()!=0)
	    				{
	    					actionInfo.setTextFill(Color.BLUE);
	    					if (day == 0)
				 				actionInfo.setText("This is the list of items on " + monthtext + "##" + yeartext);
	    					else
	    						  actionInfo.setText("This is the list of items on " + monthtext + daytext + yeartext);
	    					if(budgetData.size()!= itemList.size())
	    					{
	    						originalList.clear();
		    					  budgetData.clear();
		    					  for (int i = 0; i < itemList.size(); i++)
		    					  {
		    						  GridPane newPane = manage1.setItem(itemList, i);
		    						  originalList.add(newPane);
			    					  budgetData.add(newPane);
		    					  }
		    					  manage1.computeTotal(itemList);
		    					  totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
		    					  budgetLV.getSelectionModel().select(0);
	    					}
	    				}
	    			}
	    			
	    			else 
	    			{
	    				actionInfo.setTextFill(Color.RED);
		 				actionInfo.setText(searchDatePane.getInvalidInfo());
	    			}
	    		}
	    		
	    		//check edit info, if valid, saved it to itemList/tempList, LV
	    		if (source == editOkBtn)
	    		{
	    			addNew1.inputCheck();
	    			Items tempItem = addNew1.addItem();
	  				 //check if the item is added
	  				 if (tempItem == null)
	  				 {
	  					actionInfo.setText(addNew1.getInvalidInfo());
	  					actionInfo.setTextFill(Color.RED);
	  				 }
	  				 else
	  				 {
	  					if (itemList.size() == budgetData.size())
		 				{
	  						itemList.set(selectedIndex,addNew1.addItem());
		  					manage1.computeTotal(itemList);
			 				totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
			    			 gridpane1 = manage1.setItem(itemList, selectedIndex);
			    			 originalList.set(selectedIndex,gridpane1);
			  				 budgetData.set(selectedIndex,gridpane1);
			  				 
			  				actionInfo.setTextFill(Color.BLUE);
			 				actionInfo.setText(itemList.get(0).getName() + " is updated successfully.");
		 				}
	  					else
	  					{
	  						Items newItem = addNew1.addItem();
	  						itemList.set(tempIndex,newItem);
	  						tempList.set(selectedIndex, newItem);
		  					manage1.computeTotal(tempList);
			 				totalAmount.setText("Total Amount: " + fmt.format(manage1.getTotal()));
			    			 gridpane1 = manage1.setItem(itemList, tempIndex);
			    			 originalList.set(selectedIndex,gridpane1);
			  				 budgetData.set(selectedIndex,gridpane1);
			  				 
			  				actionInfo.setTextFill(Color.BLUE);
			 				actionInfo.setText(itemList.get(0).getName() + " is updated successfully.");
	  					}
	  					
	  				 }
	    		}
	    	}
	    }
	    
	    public static void main(String[] args)
		{
			launch(args);
		}

	}


