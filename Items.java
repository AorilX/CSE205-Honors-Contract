import java.io.Serializable;
import java.util.ArrayList;
//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: It keeps track of info of each items, as well as its relative position
public class Items implements Serializable{
	
	//instance variables
	private String date;
	private String name;
	private double amount;
	private int tempIndex, dateInfo;
	
	//constructor
	public Items (String name, String date, double amount)
	{
		this.name = name;
		this.date = date;
		this.amount = amount;
		tempIndex = 0;
		 
	}
	
	//methods
	//accessor
	public String getName()
	{
		return name;
	}
	
	
	public double getAmount()
	{
		return amount;
	}
	
	//it is the index of an item in the "itemList"(the main list) 
	public int getTempIndex()
	{
		return tempIndex;
	}
	
	//it returns date info as an integer, it is convenient for comparator and sort method
	public int getDateInfo()
	{
		return dateInfo;
	}
	
	//it returns date as a string, it is for display, in case for instance 0303 will be shown as 303 if it is an int
	public String getDate()
	{
		return date;
	}
	
	
	//mutator
	// it saves date info as an integer, it is convenient for comparator and sort method
	//it puts year in front of month and day, and the whole is transformed to an integer
	public void setDateInfo()
	{
		dateInfo = Integer.parseInt(date.substring(4, 8) + date.substring(0, 4));
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setTempIndex(int i)
	{
		this.tempIndex = i;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	

}
