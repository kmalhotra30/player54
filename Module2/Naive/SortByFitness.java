import java.util.*; 
import java.lang.*; 
import java.io.*; 
//Class which implements Comparator interface for sorting Individuals
public class SortByFitness implements Comparator<Individual>
{
	public int compare(Individual a, Individual b)
	{
		if(b.fitness > a.fitness)
			return 1;
		return -1;
	}
}