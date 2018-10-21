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

public class Mutation{
	
	ContestEvaluation evaluation_;
	Random rnd_;
	Utility utilityObj;

	public Mutation(ContestEvaluation evaluation,Random rnd_){

		evaluation_ = evaluation;
		this.rnd_ = rnd_;
		utilityObj = new Utility();
	}

	
	
	
	public Pair<ArrayList <Double>, Double > mutationUncorrelated(Pair<ArrayList <Double>, Double > child,int evals,int evaluations_limit_){



		ArrayList <Double> chromosome = child.getKey();
		double individual[] = new double[10];
		double sigmaVals[] = new double[10];
		double aplhaVals[] = new double[45];

		for(int i = 0 ; i < individual.length; i++){

			individual[i] = chromosome.get(i);
			sigmaVals[i] = chromosome.get(i + 10);
		}

		for(int i = 20; i < 65 ; i ++){

			aplhaVals[i-20] = chromosome.get(i);
		}
		
		Double initSigma;
		//totpop is the population
		if(System.getProperty("sigma") == null)
			initSigma = 0.1 ;
		else
			initSigma = Double.parseDouble(System.getProperty("sigma"));
		
		


		double sigma = initSigma - ((double) evals / (double) evaluations_limit_);
       // double sigma = sigmaVals[0];
        double tou = (double)(1.0/Math.pow(10,0.5)); 
        double r1 = rnd_.nextGaussian();   
        sigma *= Math.exp(tou * r1);

		for (int i = 0; i < individual.length; i++) {
            

			if(rnd_.nextDouble() <= 0.1){

				sigmaVals[i]  = Math.max(sigma,1e-10) ;         
            	individual[i] = individual[i] + sigmaVals[i] * rnd_.nextGaussian();
	            if(individual[i] <-5.0 || individual[i] > 5.0)
				{
					//System.out.println("Search space exceeded");
		            	//individual[i] = 10.0 * (individual[i] + 6.2)/(12.4) - 5;
				}
			}
       		
		}
		
		Double fitness = new Double((double) evaluation_.evaluate(individual));
		

	
		chromosome = new ArrayList <Double>();
		for(int i = 0 ; i < individual.length; i ++)
			chromosome.add(individual[i]);
		for(int i = 0 ; i < sigmaVals.length; i ++)
			chromosome.add(sigmaVals[i]);
		for(int i = 0 ; i < aplhaVals.length; i ++)
			chromosome.add(aplhaVals[i]);
		

		return new Pair<ArrayList <Double>, Double >(chromosome, fitness);

	}
	


}