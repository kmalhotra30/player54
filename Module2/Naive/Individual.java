import java.util.*;
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
public class Individual{

	static public int noOfGenes = 10;
    public double[] geneVals = new double[noOfGenes];
    public double[] sigmaVals = new double[noOfGenes];
    public double[] alphaVals = new double[(noOfGenes*(noOfGenes-1))/2];
    public Double fitness = 0.0;
    static public double minSigma = 1e-10; 

    static Utility utilityObj = new Utility();

    //For new individual with default vals
	public Individual()
	{
		
	}    
    //For random Individual
    public Individual(ContestEvaluation evaluation_) {

		double initSigma = 1.0; 
        double initAlpha = 1.0;
        
    	if(System.getProperty("sigma") != null)
    		initSigma = Double.parseDouble(System.getProperty("sigma"));
    	for (int i = 0; i < noOfGenes; i++) {
            
            geneVals[i] = utilityObj.randDoubleInRange(-5,5);
            sigmaVals[i] = initSigma;
        }
        for(int i = 0 ; i < alphaVals.length; i++){

            alphaVals[i] = initAlpha;
        }
        
        fitness = (Double) evaluation_.evaluate(geneVals);
	}
   



}