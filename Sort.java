import java.util.ArrayList;
import java.util.Comparator;
//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: A class to sort the order of the date; the resulting ArrayList would 
//				be in decreasing  order by date

public class Sort {

	public static void sort(ArrayList<Items> itemList, Comparator<Items> xComparator)
	{	
		int i;
		for (i = 0; i < itemList.size() - 1; i++)
		{
			for(int tempi = i+1; tempi < itemList.size(); tempi++)
			{
				int relativeValue = xComparator.compare(itemList.get(i), itemList.get(tempi));				
				if ( relativeValue < 0)
				{
					Items tempItem = itemList.get(tempi);
					itemList.set(tempi, itemList.get(i));
					itemList.set(i, tempItem);	
				}
				
			}
			
		}
		
	}
	
	
}


