package Simulator;
import java.util.ArrayList;

public class Memory {
    static Memory memory;
     ArrayList<Integer> mem;
    Memory(){
        mem = new ArrayList<Integer>();
    }
    public static  synchronized Memory getInstance()
    {
        if(memory==null)
        {
            memory = new Memory();
        }
        return memory;
    }
    public ArrayList<Integer> getMem(){
        return mem;
    }
   public void insert(int k)
    {
        mem.add(k);
    }

}
