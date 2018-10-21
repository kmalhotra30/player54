import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
public class SurvivorSelection{

	//(mu,Lambda) selection
    public Individual [] muLambda(Individual totpop[], Individual childpop[])
    {
    	Arrays.sort(childpop, new SortByFitness());
    	for(int i = 0; i < 100; i++){

    		totpop[i]=childpop[i];
    	}
    	return totpop;

    }
}