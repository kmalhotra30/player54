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

	InitializePopulation initializePopulationObj;
	DifferentialEvolution differentialEvolutionObj;

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

      	initializePopulationObj = new InitializePopulation(evaluation_,rnd_);
		differentialEvolutionObj = new DifferentialEvolution(evaluation_,rnd_);



		if(Kat == true)
			differentialEvolutionObj.F = 0.0019;
		else
			differentialEvolutionObj.F = 0.3;

		Individual totpop[] = initializePopulationObj.initpop(); //Initializing Population - params (initial Sigma, initial alpha)
           

    
		int evals1 = totpop.length;
        
        while(evals1 <evaluations_limit_)
        {
        	//Method for Differential Evolution
        	totpop = differentialEvolutionObj.differentialEvolutionExecution(totpop);
        	evals1 += totpop.length;
        	
        }    
        

   
	}
	
	
}
