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

public class Mutation{
	
	ContestEvaluation evaluation_;
	Random rnd_;
	Utility utilityObj;

	public Mutation(ContestEvaluation evaluation,Random rnd_){

		evaluation_ = evaluation;
		this.rnd_ = rnd_;
		utilityObj = new Utility();
	}

	//Method for Uncorrelated mutation with N step sizes 
	public Individual mutationUncorrelated(Individual child,int evals,int evaluations_limit_,Double initSigma){


		double individual[] = child.geneVals;
		double sigmaVals[] = child.sigmaVals;
		double aplhaVals[] = child.alphaVals;
		
	
		double sigma = initSigma - ((double) evals / (double) evaluations_limit_);
       
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
		
		

	
		child.geneVals = individual;
		child.sigmaVals = sigmaVals;
		child.alphaVals = aplhaVals;

		child.fitness = (Double) evaluation_.evaluate(child.geneVals);;

		return child;

	}
}	
