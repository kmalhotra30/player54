import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Pair;
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
    children uniformCrossover(ArrayList <Pair<ArrayList <Double>, Double >> totpop,int p1i,int p2i)
    {
        
        ArrayList<Double> child1 = new ArrayList<Double>();
        ArrayList<Double> child2 = new ArrayList<Double>();
        ArrayList<Double> par1 = new ArrayList<Double>();
        ArrayList<Double> par2 = new ArrayList<Double>();


 
        //extract dimensions of parent1 and parent2 from pairs to arraylists to arrays
        Pair<ArrayList <Double>, Double > px= totpop.get(p1i);
        par1=px.getKey();
        px=totpop.get(p2i);
        par2=px.getKey();
        
        double child1Sigma[] = new double[10];
        double child2Sigma[] = new double[10];
        
        for(int i=0;i<10;i++)
        {
            if(rnd_.nextBoolean()){

                child1.add(par1.get(i));
                child1Sigma[i] = par1.get(i+10);
                child2.add(par2.get(i));
                child2Sigma[i] = (par2.get(i+10));
            }
            else
            {
                child1.add(par2.get(i));
                child1Sigma[i] = par2.get(i+10);
                child2.add(par1.get(i)) ;
                child2Sigma[i] = (par1.get(i+10));  
            }
        }
        //Adding alpha values to children
        for(int i = 0 ; i <10; i++){
            child1.add(child1Sigma[i]);
            child2.add(child2Sigma[i]);
        }
        for(int i=20;i<65;i++)
        {
            child1.add(par1.get(i));
            child2.add(par2.get(i));
        }
        children c = new children(); 
        c.child1=child1;
        c.child2=child2;
        
        return(c);
        //after being returned, they need to be added to a separate children pair arraylist, after calculating fitness
    }

    //Method for Blend Crossover - Params - Total Population , Parent 1 index , parent 2 index , alpha parameter
    children blendcrossover2(ArrayList <Pair<ArrayList <Double>, Double >> totpop,int p1i,int p2i,double alpha)
    {
        double childGene;

        ArrayList<Double> child1 = new ArrayList<Double>();
        ArrayList<Double> child2 = new ArrayList<Double>();
        ArrayList<Double> par1 = new ArrayList<Double>();
        ArrayList<Double> par2 = new ArrayList<Double>();

        Pair<ArrayList <Double>, Double > px= totpop.get(p1i);
        par1=px.getKey();
        px=totpop.get(p2i);
        par2=px.getKey();

        for(int i = 0 ; i < 10; i++){

            double x = par1.get(i);
            double y = par2.get(i);

            double d = Math.abs(x - y);
            double start = Math.min(x,y) - alpha * d;
            double end = Math.max(x,y) + alpha * d;

            double u = utilityObj.randDoubleInRange(start,end);

            child1.add(u);
            

            u = utilityObj.randDoubleInRange(start,end);

            child2.add(u);
            

        }

        //Sigma Vals
        for(int i = 10; i < 20 ; i ++){

            child1.add(par1.get(i));
            child2.add(par2.get(i));
        }

        //Alpha vals

        for(int i = 20 ; i < 65; i ++){

            child1.add(par1.get(i));
            child2.add(par2.get(i));   
        }

        children childrenObj = new children(); 
        childrenObj.child1=child1;
        childrenObj.child2=child2;
        
        return(childrenObj);

    }
}