import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
public class Crossover{
	
	Random rnd_;
    Utility utilityObj;
    public Crossover(Random rnd_){

        this.rnd_ = rnd_;
        utilityObj = new Utility();
    }
    
    //Method for uniform crossover - Params - Total Population , Parent 1 index , parent 2 index
    children uniformCrossover(Individual[] totpop,int p1i,int p2i)
    {
        
        Individual child1 = new Individual();
        Individual child2 = new Individual();
        Individual par1 = new Individual();
        Individual par2 = new Individual();

        par1=totpop[p1i];
        par2=totpop[p2i];


        double p1GeneVals[] = par1.geneVals; //stores dim values from parent1
        double p2GeneVals[] = par2.geneVals; //stores dim values from parent2
        double c1GeneVals[] = new double[10]; //stores dim values for child1 
        double c2GeneVals[] = new double[10]; //stores dim values for child2
        

        for(int i=0;i<10;i++)
        {
            if(rnd_.nextBoolean()){

                c1GeneVals[i]=p1GeneVals[i];
                c2GeneVals[i]=p2GeneVals[i];
            }
            else
            {
                c1GeneVals[i]=p2GeneVals[i];
                c2GeneVals[i]=p1GeneVals[i];  
            }
        }
        //Adding sigma & alpha values to children
        
        child1.geneVals = c1GeneVals;
        child2.geneVals = c2GeneVals;

        child1.sigmaVals = par1.sigmaVals;
        child2.sigmaVals = par2.sigmaVals;

        child1.alphaVals = par1.alphaVals;
        child2.alphaVals = par2.alphaVals;
        
        children childrenObj = new children(); 
        childrenObj.child1=child1;
        childrenObj.child2=child2;
        
        return(childrenObj);
        //after being returned, they need to be added to a separate children pair arraylist, after calculating fitness
    }

    //Method for Blend Crossover - Params - Total Population , Parent 1 index , parent 2 index , alpha parameter
    children blendcrossover2(Individual totpop[],int p1i,int p2i,double alpha)
    {
        double childGene;

        Individual child1 = new Individual();
        Individual child2 = new Individual();
        Individual par1 = new Individual();
        Individual par2 = new Individual();

        par1=totpop[p1i];
        par2=totpop[p2i];


        double p1GeneVals[] = par1.geneVals; //stores dim values from parent1
        double p2GeneVals[] = par2.geneVals; //stores dim values from parent2
        double c1GeneVals[] = new double[10]; //stores dim values for child1 
        double c2GeneVals[] = new double[10];


        for(int i = 0 ; i < 10; i++){

            double x = p1GeneVals[i];
            double y = p2GeneVals[i];

            double d = Math.abs(x - y);
            double start = Math.min(x,y) - alpha * d;
            double end = Math.max(x,y) + alpha * d;

            double u = utilityObj.randDoubleInRange(start,end);

            c1GeneVals[i] = u;
            

            u = utilityObj.randDoubleInRange(start,end);

            c2GeneVals[i] = u;
            

        }

        //Sigma Vals & alpha Vals
        
        child1.geneVals = c1GeneVals;
        child2.geneVals = c2GeneVals;

        child1.sigmaVals = par1.sigmaVals;
        child2.sigmaVals = par2.sigmaVals;

        child1.alphaVals = par1.alphaVals;
        child2.alphaVals = par2.alphaVals;
        
        children childrenObj = new children(); 
        childrenObj.child1=child1;
        childrenObj.child2=child2;
        
        return(childrenObj);

    }
}