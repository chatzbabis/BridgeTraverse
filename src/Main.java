import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main
{


    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);

        System.out.println("How many members you want to play with? (2 to 10)\n");  //SOS - prepei na mpei se comment an theloume thn prokat ylopoihsh
        State initialState = new State(sc.nextInt());                               //SOS - prepei na mpei se comment an theloume thn prokat ylopoihsh
        System.out.println();
        //State initialState = new State();                         //SOS - prepei na mpei se comment an theloume thn ylopoihsh me tyxaio n
        System.out.println("Initial State:\n");
        initialState.print();
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("************************************************************************************************************************");
        AlgorithmStar algorithmStar = new AlgorithmStar();
        State terminalState;
        long start = System.currentTimeMillis();
        terminalState = algorithmStar.algoStar(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null)
        {
            System.out.println("Could not find solution");
        }
        else
        {
            State temp = terminalState;
            ArrayList<State> path = new ArrayList<State>();
            path.add(terminalState);
            while(temp.getFather()!=null)
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            Collections.reverse(path);
            System.out.println("\t\t\t\t\t\t\t\t\t\tFinished in "+path.size()+" steps!");
            System.out.println("************************************************************************************************************************");
            for(State item : path)
            {
                item.print();
            }
        }
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("Finished with total score of: " + terminalState.getTotalScore());
        System.out.println("\nA* search time: " + (double)(end - start) / 1000 + " sec.");
    }

}
