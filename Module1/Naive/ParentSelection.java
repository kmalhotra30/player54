import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Pair;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
//Parent Selection class consists of 2 methods:
public class ParentSelection
{
    //Method for random selection of parents
    //Param 1 : totpop - Consists of total population
	public int[] parentSelection(ArrayList <Pair<ArrayList <Double>, Double >> totpop)
    {
    	int parArr[] = new int[totpop.size() + 20];
    	int counterParents = 0;

    	while(counterParents<totpop.size() + 20){

    		int randomIndex = (int )(Math.random() * totpop.size());
    		if(randomIndex<0 || randomIndex >=totpop.size())
    			randomIndex = 0;
            parArr[counterParents++] = randomIndex;
    	}
    	return parArr;
    }
    //Method for performing tournament selection
    //Param 1 : totpop - Consists of total population
    //Param 2 : k - tournament size
    public int[] parentSelectionTournament(ArrayList <Pair<ArrayList <Double>, Double >> totpop,int k)
    {
        int parArr[] = new int[totpop.size() + 20];
        int counterParents = 0;

        while(counterParents<totpop.size() + 20){

            int min = 101;
            for(int i = 0 ; i < k ; i++){

                int rndIndex = (int)(Math.random()*totpop.size());
                min= Math.min(rndIndex,min);
            }
            parArr[counterParents++] = min;
        }
        return parArr;
    }
}