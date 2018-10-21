import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Pair;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

//Class for initializing population - Random initialization
public class InitializePopulation{
	

	ContestEvaluation evaluation_;
	Random rnd_;

	public InitializePopulation(ContestEvaluation evaluation,Random rnd_){

		evaluation_ = evaluation;
		this.rnd_ = rnd_;
	}
	final Comparator<Pair<ArrayList <Double>, Double >> customComparator = reverseOrder(comparing(Pair::getValue));
	public ArrayList <Pair<ArrayList <Double>, Double >> initpop (double initSigma , double initAlpha) 
    { 
        //Random initialisation of population
        
        ArrayList <Pair<ArrayList <Double>, Double >> totpop = new ArrayList<Pair<ArrayList <Double>, Double >>();

        Pair<ArrayList <Double>, Double > pair = new Pair<ArrayList<Double>,Double>(new ArrayList<Double>(),0.0);
        for (int j=0;j<100;j++)                                           //to be run population times
        {
        		double arr[]=new double[10]; 
        
        		ArrayList<Double> dimvec = new ArrayList<Double>();		
		        for(int i=0;i<10;i++)
		        {
		            double start = -5;
		            double end = 5;
		            double random = rnd_.nextDouble();
		            double result = start + (random * (end - start));
		            dimvec.add(result);
		            arr[i]=result;

		        }
        		//Initialisation of sigma values
		        for(int i=0;i<10;i++)
		        {
		            dimvec.add(initSigma); 
		        }
        		//Initialisation of alpha values
		        for(int i=0;i<45;i++)
		        {
		            dimvec.add(initAlpha); 
		        }
	        	Double fitness = (double) evaluation_.evaluate(arr);
	        	pair = new Pair<>(dimvec,fitness);
	        	totpop.add(pair);
        }
        Collections.sort(totpop,customComparator);
		return totpop; 
    }
}