import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class player54 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;

	Crossover crossoverObj; 
	ParentSelection parentSelectionObj;
	SurvivorSelection survivorSelectionObj;
	InitializePopulation initializePopulationObj;
	Mutation mutationObj;
	
	boolean isMultimodal;
	boolean hasStructure;
	boolean isSeparable;

	boolean Bc;
	boolean Kat;
	boolean Sc;
	
    private int evaluations_limit_;
	//final Comparator<Pair<ArrayList <Double>, Double >> customComparator = reverseOrder(comparing(Pair::getValue));

	public player54()
	{
		rnd_ = new Random();
		Bc = false;
		Kat = false;
		Sc= false;
		
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	

    
	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;
		
		// Get evaluation properties
		Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		if(isMultimodal==true && hasStructure==true){

        	Sc= true;
        }
        else if(isMultimodal==true){
        	Kat = true;
        }
        else
        	Bc = true;;
        
    }
    
	public void run()
	{
		// Run your algorithm here
      


      	//Instantiating Class objects

      	crossoverObj = new Crossover(rnd_);
		parentSelectionObj = new ParentSelection();
		survivorSelectionObj = new SurvivorSelection();
		initializePopulationObj = new InitializePopulation(evaluation_,rnd_);
		mutationObj = new Mutation(evaluation_,rnd_);



		//Variable for initial value of sigma , can also be passed as a CLI parameter.
		Double initSigma;
		if(System.getProperty("sigma") == null)
		{
			if(Kat == true)
				initSigma = 0.1;
			else
				initSigma = 1.0;
		}
		else
			initSigma = Double.parseDouble(System.getProperty("sigma"));
		
		//totpop is the population
		Individual totpop[] = initializePopulationObj.initpop(); //Initializing Population - params (initial Sigma, initial alpha)
           

        int evals2 = totpop.length;
        //Loop for Naive EA
        while(evals2<evaluations_limit_ - 120)
        {

        	//parArr consists of parent indices in totpop
        	int parArr[] = parentSelectionObj.parentSelection(totpop);

        	//Childpop consists of all children that are created via recombination
        	Individual childpop[] = new Individual[parArr.length]; 

        	
       		for(int i = 0 ; i < childpop.length; i++)
       			childpop[i] = new Individual();

       		for(int i = 0 ; i < parArr.length - 1; i+=2){

	        	int p1i = parArr[i];
	        	int p2i = parArr[i+1];
	        	double alpha = rnd_.nextDouble();// alpha is for whiole arth crossover
	        	children childrenObj;
	        	if(Kat == true)
	        		childrenObj = crossoverObj.blendcrossover2(totpop,p1i,p2i,0.5);
	     		else
	     			childrenObj = crossoverObj.uniformCrossover(totpop,p1i,p2i);

	        	Individual child1 = childrenObj.child1;
	        	Individual child2 = childrenObj.child2;
	
				
	        	//Mutation from here 

	        	child1 = mutationObj.mutationUncorrelated(child1,evals2,evaluations_limit_,initSigma);
	       
	        	child2 = mutationObj.mutationUncorrelated(child2,evals2,evaluations_limit_,initSigma);
	       
	        	childpop[i] = child1;
	        	childpop[i+1] = child2; 
	        	
	        }
	        //Survivor Selection
	        totpop = survivorSelectionObj.muLambda(totpop,childpop);
	        evals2 += childpop.length;
	        
        	

	    	    
        }
        
        

   
	}
	
	
}
