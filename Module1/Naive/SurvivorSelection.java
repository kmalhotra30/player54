import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Pair;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

//(mu,lamda) selection
public class SurvivorSelection{

	final Comparator<Pair<ArrayList <Double>, Double >> customComparator = reverseOrder(comparing(Pair::getValue));
	public ArrayList <Pair<ArrayList <Double>, Double >> muLambda(ArrayList <Pair<ArrayList <Double>, Double >> totpop, ArrayList <Pair<ArrayList <Double>, Double >> childpop)
    {
    	totpop.clear();
    	Collections.sort(childpop,customComparator);
    	for(int i = 0; i < 100; i++){

    		totpop.add(childpop.get(i));
    	}
    	return totpop;

    }
}