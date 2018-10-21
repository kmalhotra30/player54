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

//Class for executing Differential Evolution
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

	public ArrayList <Pair<ArrayList <Double>, Double >> diffentialEvolutionExecution(ArrayList <Pair<ArrayList <Double>, Double >> totpop)
	{

	
		for(int i = 0 ; i < totpop.size(); i++){

			
			int randValV, randValY , randValZ;

			ArrayList <Double> x,v,y,z;
		
			x = totpop.get(i).getKey();
			double dummyMutant [] = new double[10];
			ArrayList <Double> mutant = new ArrayList <Double>();
			boolean boundSatisfaction;

			//Do while loop comment can be removed if the solutions which are going out of the search space are not to ignored.
			//do{ 

				mutant.clear();
				boundSatisfaction = true;

				int randVal = (int)(Math.random() * totpop.size());
				while(randVal == i)
					randVal = (int)(Math.random() * totpop.size());

				randValV = randVal;
				v = totpop.get(randVal).getKey();

				randVal = (int)(Math.random() * totpop.size());
				while(randVal == randValV || randVal == i)
					randVal = (int)(Math.random() * totpop.size());

				randValY = randVal;
				y = totpop.get(randVal).getKey();


				randVal = (int)(Math.random() * totpop.size());
				while(randVal == randValV || randVal == i || randVal == randValY)
					randVal = (int)(Math.random() * totpop.size());

				randValZ = randVal;
				z = totpop.get(randVal).getKey();

				

				ArrayList <Double> p = new ArrayList<Double>();

				for(int k = 0 ; k < 10; k++){

					p.add(F * (y.get(k) -z.get(k)));
				}

				


				int rndIndex = (int)(Math.random() * 10);	
				for(int k = 0 ; k <10; k++){

					double r = rnd_.nextGaussian();
					double CR = utilityObj.randDoubleInRange(0,1);
					CR = 0.3;
					if(System.getProperty("CR") != null)
						CR = Double.parseDouble(System.getProperty("CR"));
					if(r <= CR || k == rndIndex){

						mutant.add(v.get(k) + p.get(k));
					}
					else
						mutant.add(x.get(k));
				}
				
				for(int k = 0 ; k < 10; k++){
					dummyMutant[k] = mutant.get(k);
					
				}

				

				
			//}while(boundSatisfaction == false); 

			if(boundSatisfaction){

				Double mutantFitness = (double) evaluation_.evaluate(dummyMutant);
				
				for(int k = 10; k <64; k ++)
					mutant.add(x.get(k));

				Double xFitness = totpop.get(i).getValue();
				if(mutantFitness > xFitness){

					totpop.remove(i);
					totpop.add(new Pair<>(mutant,mutantFitness));
				}	
			}
			
			
		}
		return totpop;

	}
}