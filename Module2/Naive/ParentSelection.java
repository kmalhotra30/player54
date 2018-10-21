import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
public class ParentSelection
{

    //Method for random selection of parents
    //Param 1 : totpop - Consists of total population
	public int[] parentSelection(Individual totpop[])
    {
    	int parArr[] = new int[totpop.length + 20];
    	int counterParents = 0;

    	while(counterParents<totpop.length + 20){

    		int randomIndex = (int )(Math.random() * totpop.length);
    		if(randomIndex<0 || randomIndex >=totpop.length)
    			randomIndex = 0;
            parArr[counterParents++] = randomIndex;
    	}
    	return parArr;
    }
    //Method for performing tournament selection
    //Param 1 : totpop - Consists of total population
    //Param 2 : k - tournament size
    public int[] parentSelectionTournament(Individual totpop[] ,int k)
    {
        int parArr[] = new int[totpop.length + 20];
        int counterParents = 0;

        while(counterParents<totpop.length + 20){

            int min = 101;
            for(int i = 0 ; i < k ; i++){

                int rndIndex = (int)(Math.random()*totpop.length);
                min= Math.min(rndIndex,min);
            }
            parArr[counterParents++] = min;
        }
        return parArr;
    }
}