import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.util.*;
import java.util.Random;
import java.util.Properties;
import javafx.util.Pair;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

//Main Class
public class player54 implements ContestSubmission
{
	Random rnd_; 
	ContestEvaluation evaluation_;


	InitializePopulation initializePopulationObj;
	DifferentialEvolution differentialEvolutionObj;

	boolean Bc;
	boolean Kat;
	boolean Sc;
	
	
    private int evaluations_limit_;
	final Comparator<Pair<ArrayList <Double>, Double >> customComparator = reverseOrder(comparing(Pair::getValue));

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
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal==true && hasStructure==true){

        	Sc= true;
        }
        else if(isMultimodal==true){
        	Kat = true;
        }
        else
        	Bc = true;
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

		//totpop is the population
		ArrayList <Pair<ArrayList <Double>, Double >> totpop = initializePopulationObj.initpop(1.0,0.2); //Initializing Population - params (initial Sigma, initial alpha)
           
    
        int evals1 = totpop.size();
        
        while(evals1 <evaluations_limit_)
        {
        	//Method for Differential Evolution
        	totpop = differentialEvolutionObj.diffentialEvolutionExecution(totpop);
        	evals1 += totpop.size();
        	
        } 
        
        

   
	}
	
	
}
