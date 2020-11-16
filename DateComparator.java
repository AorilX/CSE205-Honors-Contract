import java.util.Comparator;
//ASU Fall 2020 Honors Project
//Name: Xiaoyi He
//StudentID:  1218112584
//Lecture: MWF 8:35-9:25
//Description: It compares date of two Items, viewing the date as an integer
public class DateComparator implements Comparator<Items>{
	
		public int compare(Items first, Items second)
		{
			int x;
			int date1 = first.getDateInfo();
			int year2 = second.getDateInfo();
			
				x = date1-year2;
			
			return x;
		}

}
