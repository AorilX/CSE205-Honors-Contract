import java.io.FileInputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: It has methods for itemList;
//				1. compute total amount 	2. search an item by name/date	3. setItem info to a gridPane, for showing in LV in ItemsList class
//				4. order items by recency
public class ItemManagement implements Serializable{
	
	//instance variables 
	private String actionInfo;
	private double totalAmount = 0.0;

	public ItemManagement() {
		actionInfo = "";
		totalAmount = 0.0;
	}
	
	
	
	public double getTotal()
	{
		return totalAmount;
	}
	
	public void computeTotal(ArrayList<Items> itemList)
	{
		totalAmount = 0.00;
		for (int i = 0; i < itemList.size();i++ )
		{
			totalAmount += itemList.get(i).getAmount();
		}
	}
	


	public GridPane setItem(ArrayList<Items> itemList, int i) {
		GridPane gridpane1 = new GridPane();
		for (int j = 0; j < 3; ++j) {

	    	  ColumnConstraints cc = new ColumnConstraints();
	    	  cc.setPercentWidth(100/3);
	    	  gridpane1.getColumnConstraints().add(cc);
	    	}
		DecimalFormat amountFmt = new DecimalFormat("$0.00");
		
		Label iNameLb = new Label(itemList.get(i).getName());
	    Label amountLb = new Label(amountFmt.format(itemList.get(i).getAmount()));
	    Label dateLb = new Label(itemList.get(i).getDate());

	    gridpane1.add(iNameLb, 0, 0);
	    gridpane1.add(amountLb, 1, 0);
	    gridpane1.add(dateLb,2,0);
		
		return gridpane1;
	}
	
	//serach Methods
	public ArrayList<Items> searchName(ArrayList<Items> itemList, String name)
	{
		ArrayList<Items> newList = new ArrayList<Items>();
		for (int i = 0; i < itemList.size();i++)
		{
			if (name.equals(itemList.get(i).getName()))
			{
				newList.add(itemList.get(i)) ;
				newList.get(newList.size()-1).setTempIndex(i);
			}
		}
		
		return newList;
	}
	
	
	public ArrayList<Items> searchDateWithDay(ArrayList<Items> itemList, int yearSelected, int monthSelected, int daySelected)
	{
		ArrayList<Items> newList = new ArrayList<Items>();
		for (int i = 0; i < itemList.size();i++)
		{
			int month = Integer.parseInt(itemList.get(i).getDate().substring(0, 2));
			int  day = Integer.parseInt(itemList.get(i).getDate().substring(2, 4));
			int  year = Integer.parseInt(itemList.get(i).getDate().substring(4, 8));
			
			if (year == yearSelected && day == daySelected && month == monthSelected)
			{
				newList.add(itemList.get(i));
				newList.get(newList.size()-1).setTempIndex(i);
			}
		}
		
		return newList;
		
	}
	
	public ArrayList<Items> searchDateWithoutDay(ArrayList<Items> itemList, int yearSelected, int monthSelected)
	{
		ArrayList<Items> newList = new ArrayList<Items>();
		for (int i = 0; i < itemList.size();i++)
		{
			int month = Integer.parseInt(itemList.get(i).getDate().substring(0, 2));
			int  year = Integer.parseInt(itemList.get(i).getDate().substring(4, 8));
			
			if (year == yearSelected && month == monthSelected)
			{
				newList.add(itemList.get(i));
				newList.get(newList.size()-1).setTempIndex(i);
			}
		}
		
		return newList;
		
	}
	
	//order by recency
	public ArrayList<Items> orderByDate(ArrayList<Items> itemList)
	{
		Sort sort1 = new Sort();
		DateComparator xComparator1 = new DateComparator();
		sort1.sort(itemList, (Comparator<Items>) xComparator1);
		return itemList;
	}


}


