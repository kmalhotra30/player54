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

public class DifferentialEvolution{

	ContestEvaluation evaluation_;
	Random rnd_;
	Utility utilityObj;
	double F;

	public DifferentialEvolution(ContestEvaluation evaluation,Random rnd_){

		evaluation_ = evaluation;
		this.rnd_ = rnd_;
		utilityObj = new Utility();
	}

	
	public Individual[] differentialEvolutionExecution(Individual[] totpop)
	{

		for(int i = 0 ; i < totpop.length; i++){

			int randValV, randValY, randValZ;

			Individual x = new Individual();
			Individual v = new Individual();
			Individual y = new Individual();
			Individual z = new Individual();

			
			x.geneVals = totpop[i].geneVals;
			x.sigmaVals = totpop[i].sigmaVals;
			x.alphaVals = totpop[i].alphaVals;
			
			
			double dummyMutant [] = new double[10];
			Individual mutant = new Individual();
			//double mutant [] = new double[65];
			boolean boundSatisfaction;

			//Do while loop can be used if the individuals which exceed search space are not to be ignored.
			//do{
				boundSatisfaction = true;

				int randVal = (int)(Math.random() * totpop.length);
				while(randVal == i)
					randVal = (int)(Math.random() * totpop.length);

				randValV = randVal;
				v.geneVals = totpop[randValV].geneVals;

				
				randVal = (int)(Math.random() * totpop.length);
				while(randVal == randValV || randVal == i)
					randVal = (int)(Math.random() * totpop.length);

				randValY = randVal;
				y.geneVals = totpop[randValY].geneVals;
				

				randVal = (int)(Math.random() * totpop.length);
				while(randVal == randValV || randVal == i || randVal == randValY)
					randVal = (int)(Math.random() * totpop.length);

				randValZ = randVal;
				z.geneVals = totpop[randValZ].geneVals;
				
				double p[] = new double[10];

				for(int k = 0 ; k < 10; k++){

					p[k] = F * (y.geneVals[k] -z.geneVals[k]);
				}

				


				int rndIndex = (int)(Math.random() * 10);	
				for(int k = 0 ; k <10; k++){

					double r = rnd_.nextGaussian();
					double CR = utilityObj.randDoubleInRange(0,1);
					CR = 0.3;
					if(System.getProperty("CR") != null)
						CR = Double.parseDouble(System.getProperty("CR"));
					if(r <= CR || k == rndIndex){

						mutant.geneVals[k] = v.geneVals[k] + p[k];
					}
					else
						mutant.geneVals[k] = x.geneVals[k];
				}
				
				dummyMutant = mutant.geneVals;
				
				

				
			//}while(boundSatisfaction == false);

			if(boundSatisfaction){

				Double mutantFitness = (double) evaluation_.evaluate(dummyMutant);
				
				mutant.sigmaVals = x.sigmaVals;
				mutant.alphaVals = x.alphaVals;
				mutant.fitness=mutantFitness;
				Double xFitness = totpop[i].fitness;
				if(mutantFitness > xFitness){

					totpop = removeFromPopulation(totpop, i);
					totpop[99] = mutant;
				}	
			}
			
			
		}
		return totpop;

	}
	public Individual[] removeFromPopulation(Individual[] totpop, int index)
	{
	    
	    for(int l = 0; l < totpop.length; l++){
		        if(l == index){
		            for(int m = l; m < totpop.length - 1; m++){
		                totpop[m] = totpop[m+1];
		            }
		            break;
		        }
		    
			}
		return totpop;
	}

}

