import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
public class InitializePopulation{
	

	ContestEvaluation evaluation_;
	Random rnd_;

	public InitializePopulation(ContestEvaluation evaluation,Random rnd_){

		evaluation_ = evaluation;
		this.rnd_ = rnd_;
	}
	//final Comparator<Pair<ArrayList <Double>, Double >> customComparator = reverseOrder(comparing(Pair::getValue));
	public Individual[] initpop () 
    { 
        //Random initialisation of population
        
        Individual totpop [] = new Individual[100];

        for (int j=0;j<100;j++)                                           //to be run population times
        {
        		totpop[j] = new Individual(evaluation_);
        		
        }
        Arrays.sort(totpop, new SortByFitness());
		return totpop; 
    }
}