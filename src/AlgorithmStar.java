
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AlgorithmStar
{
    private ArrayList<State> states;

    AlgorithmStar()
    {
        this.states = null;
    }

    public State algoStar(State initialState)
    {
        this.states = new ArrayList<State>();
        this.states.add(initialState);
        while(this.states.size() > 0)
        {
            State currentState = this.states.remove(0);
            if(currentState.isTerminal())
            {
                return currentState;
            }
            //We generate the children and calculate their total scores
            this.states.addAll(currentState.getChildren());
            //We sort all the children according to their total scores
            Collections.sort(this.states);
        }
        return null;
    }

}

