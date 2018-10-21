import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;
import java.io.*;
import java.util.*;
import java.util.Random;
import java.util.Properties;
import javafx.util.Pair;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import java.util.concurrent.CountDownLatch; 
import java.util.Arrays;

public class player54 implements ContestSubmission
{
	Random rnd_;
	int evals_island;
    int evaluations_limit_island;	
    int evaluations_limit_;
	ArrayList<Double> dimvec = new ArrayList<Double>();         				//dimvec contains dimensions of an individual,sigma values, alpha values
    Pair<ArrayList <Double>, Double > pair = new Pair<ArrayList <Double>, Double >(dimvec,0.0);			//pair of dimevec and fitness
    ArrayList <Pair<ArrayList <Double>, Double> > totpop = new ArrayList<Pair<ArrayList <Double>, Double>>();		
    ArrayList <Pair<ArrayList <Double>, Double >> childpop = new ArrayList<Pair<ArrayList <Double>, Double >>(); 		
    static int noofislands=30;
    static int noofmigrants=2;
    static int epoch = 25;
    static ArrayList<player54> iobj = new ArrayList<player54>();
    static ArrayList <Pair> migrants = new ArrayList<Pair>();
    static InitializePopulation initpopObj;
   	static ContestEvaluation evaluation_;
    			
    player54()
    {
    	rnd_ = new Random();
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
        
    }
    public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

    public static player54 getInstance(int i)
    {
    	if(i<noofislands)
    		return(iobj.get(i));
    	return(null);
    }
    
    public void run()
    {
    	
    	initpopObj = new InitializePopulation(evaluation_,rnd_);
    	//Making noofislands objects of player54
    	//these objects act as islands
    	for(int i=1;i<=noofislands;i++)
    	{
    		iobj.add(new player54());
    		iobj.get(i-1).totpop=initpopObj.initpop(1,1);
    		iobj.get(i-1).evals_island= iobj.get(i-1).totpop.size();
       	}

       	int looplimit = evaluations_limit_ - noofislands*100;
       	looplimit = looplimit/noofislands;
       	looplimit = looplimit/120;
       	looplimit = looplimit/epoch;
       	for(int i=1; i<=looplimit;i++)
		{ 
			CountDownLatch latch= new CountDownLatch(noofislands); 		
			ArrayList<Ithread> ithr = new ArrayList<Ithread>();
			int j;
			ithr.clear();
			System.gc();
			//Each thread will handle 1 island
			for(j=0;j<noofislands;j++)
			{
				ithr.add(new Ithread(0,latch," "+j));
			}
			//starting all threads
			for(j=0;j<noofislands;j++)
			{
				ithr.get(j).start();
			}
    
    		// The main task waits for four threads 
    		try
    		{
    			Thread.sleep(1000);
    			latch.await();
    			 
    		}
    		catch(Exception e)
    		{

    		}		
    		//To ensure release of threads
    		for(j=0;j<noofislands;j++)
			{
				ithr.get(j).interrupt();
			}
    		// Main thread has started, exchange will happen here 
    		int max = 100, min = 0;
    		Random r = new Random();
    		ArrayList <Integer> k = new ArrayList <Integer>();
    		migrants.clear();
    		k.clear();
            //Choosing indices of migrants
    		for(int a = 0; a<noofmigrants*noofislands;a++)					
    		{
    			int r1= r.nextInt((max - min) + 1) + min;
    			while(k.contains(r1))
    				{
    					r1= r.nextInt((max - min) + 1) + min;
    				}
    			k.add(r1);
    		}
            //sorting the indices of the migrants
    		Collections.sort(k);							
    		int b=0;
    		for(int a = 0; a<noofislands;a++)
    		{
    			//maintaining copies of migrants
                for(int c=0;c<noofmigrants;c++)						
    			{
    				player54 ob;
    				ob = iobj.get(a);
    				migrants.add(ob.totpop.get(k.get(b++)));
    			}
    			b=0;
                //removing migrants from the main totpop
    			for(int c=0;c<noofmigrants;c++)							
    			{
    				iobj.get(a).totpop.remove(k.get(b++)-c);
    			}
    			
    		}
    		
    		b=0;
    		for(int a = 1; a<noofislands;a++)
    		{
    			//Updating totpops for all islands
                for(int c=0;c<noofmigrants;c++)						
    			{
    				iobj.get(a).totpop.add(migrants.get(b++));
    			}
    		}
    		//Updating totpops for the 1st island
            for(int c=0;c<noofmigrants;c++)						
    		{
    				iobj.get(0).totpop.add(migrants.get(b++));
    		}
    		    		   		
	    }
		
    }
}

class Ithread extends Thread 
{ 
	Crossover crossoverObj; 
	ParentSelection parentSelectionObj;
	SurvivorSelection survivorSelectionObj;
	Mutation mutationObj;
	private int parArr[];
	private int delay; 
	private CountDownLatch latch; 
	private int counter;
	private int pos;							//stores which island to handle
	public static int counterforobj = -1;
	player54 i = new player54();
    public Ithread(int delay,CountDownLatch latch, String name) 
	{ 
		super(name); 
		this.delay = delay; 
		this.latch = latch; 
		this.counterforobj=-1;
	} 
	synchronized void increment()
	{
		counterforobj++;
		pos = counterforobj;
	}
	synchronized void xover(player54 obj)
	{
		crossoverObj = new Crossover(obj.rnd_);
		parentSelectionObj = new ParentSelection();
		mutationObj = new Mutation(obj.evaluation_,obj.rnd_);
		parArr= new int[100];
		obj.evals_island += 120;
		parArr=parentSelectionObj.parentSelectionTournament(obj.totpop,50);
		for(int i = 0 ; i < parArr.length - 1; i+=2)
		{
        	//Parent selection
            int p1i = parArr[i];
        	int p2i = parArr[i+1];
        	children childrenObj = crossoverObj.blendcrossover2(obj.totpop,p1i,p2i,0.5);
	       	ArrayList<Double> child1 = childrenObj.child1;
	       	ArrayList<Double> child2 = childrenObj.child2;
	       	//Mutation 
      		Pair<ArrayList <Double>, Double > cpair = mutationObj.mutationUncorrelated(new Pair<>(child1,0.0),obj.evals_island,obj.evaluations_limit_island);
        	obj.childpop.add(cpair);
        	cpair = mutationObj.mutationUncorrelated(new Pair<>(child2,0.0),obj.evals_island,obj.evaluations_limit_island);
	       	obj.childpop.add(cpair);
	    }

	}
	synchronized void survivorselec(player54 obj)
	{
		survivorSelectionObj = new SurvivorSelection();
		obj.totpop = survivorSelectionObj.muLambda(obj.totpop,obj.childpop);
	}
	
	@Override
	public void run() 
	{ 
		this.increment();
		System.out.println("Thread "+Thread.currentThread().getName()+" started");
		player54 obj = new player54();
		obj=i.getInstance(pos);
		obj.evaluations_limit_island = (obj.evaluations_limit_/obj.noofislands);
		parArr= new int[100];
		crossoverObj = new Crossover(obj.rnd_);
		parentSelectionObj = new ParentSelection();
		survivorSelectionObj = new SurvivorSelection();
		mutationObj = new Mutation(obj.evaluation_,obj.rnd_);
		for(int v=1;v<=obj.epoch;v++)
			this.xover(obj);  		
		//call survivor selection
		this.survivorselec(obj);
		latch.countDown();

	}
		 
} 
