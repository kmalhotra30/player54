import java.util.*;
import java.util.Random;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Pair;
import java.lang.*;
import java.text.DecimalFormat;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class Utility{

    Random rnd_;
    Utility(){

        rnd_ = new Random();
    }
    public double randDoubleInRange(double start, double end){

        double random = rnd_.nextDouble();
        return start + (random * (end - start));
    }
	

    public void printMatrix(double M[][]){

        for(int i = 0; i < M.length;i++){

            for(int j = 0 ; j < M[i].length ; j++)
                System.out.print(M[i][j] + " ");
        }
        System.out.println("");
    } 
    public void printArray(double array[]){

        for(int i = 0 ; i < array.length;i++)
            System.out.println(array[i]);
    }

}