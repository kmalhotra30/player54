int evals = 0;
        int minP = -100,maxP = 100;
        List <Integer> populationList = new ArrayList<Integer>();
        // init population
        
        for(int i = 0;i < 100;i++){

        	populationList.add(ThreadLocalRandom.current().nextInt(minP, maxP + 1));
            

        }
        while(evals<evaluations_limit_){

        	int max = Collections.max(populationList);
        	int min = Collections.min(populationList);
        	int fit = (min + max)/2;
        	

            // Select parents

        	Iterator <Integer> iter = populationList.iterator();

        	while(iter.hasNext())
        	{
        		Integer ele = iter.next();
        		if(ele < fit)
        			iter.remove();
        	}
        	List <Integer> parentsForXover = new ArrayList<Integer> ();
        	for(int i = 0 ; i < 10; i++){

        		int index = ThreadLocalRandom.current().nextInt(0, populationList.size());
        		parentsForXover.add(populationList.get(index));
        	}
            // Apply crossover / mutation operators
            

            List <String> parentsForXoverBits = new ArrayList<String> ();
            Iterator <Integer> it = parentsForXover.iterator();
            while(it.hasNext())
            {
            	Integer ele = it.next();
            	parentsForXoverBits.add(Integer.toBinaryString(0x100 | (int)ele).substring(1));
            }
            
            double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
          
            for(int i = 0 ; i < parentsForXoverBits.size() - 1; i++)
            {
            	String p1 = parentsForXoverBits.get(i);
            	String p2 = parentsForXoverBits.get(i+1);
            	int xoverPoint = ThreadLocalRandom.current().nextInt(1, 7);
            	String c1 = p1.substring(0,xoverPoint) + p2.substring(xoverPoint);
            	String c2 = p2.substring(0,xoverPoint) + p1.substring(xoverPoint);
            	int randomVal = ThreadLocalRandom.current().nextInt(1, 11);
            	if(randomVal > 8)
            	{
            		int randomBitPos = ThreadLocalRandom.current().nextInt(1, c2.length());
            		char charArray []= c2.toCharArray();
            		if(c2.charAt(randomBitPos) == '0')
            			charArray[randomBitPos] = '1';
            		else
            			charArray[randomBitPos] = '0';
            		c2 = new String(charArray);
            	}
            	child[i] = (double)Integer.parseInt(c1,2);
            	child[i+1] = (double)Integer.parseInt(c2,2);
            	Double cI1 = new Double(child[i]);
            	Double cI2 = new Double(child[i+1]);
            	populationList.add(cI1.intValue());
            	populationList.add(cI2.intValue());
            }

            //System.out.println(randomNum);
            // Check fitness of unknown fuction
            Double fitness = (double) evaluation_.evaluate(child);
            evals++;
        