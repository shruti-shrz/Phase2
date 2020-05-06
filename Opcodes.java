package Simulator;
import java.lang.reflect.Array;
import java.util.*;

public class Opcodes {
    String line;
    String[] sl;
    static Opcodes op;
    Dictionary<String,List<String>> opt(){
        Dictionary<String,List<String>> dict = new Hashtable<>();
        ArrayList<String> a =new ArrayList<>(Arrays.asList("0","r","1","r","2","i","3","i","4","r","5","i","6","i","7","j","8","s","9","i","10","i","11","i"));
        dict.put("ADD",a.subList(0,2));// add r1,r2,r3
        dict.put("SUB",a.subList(2,4));// sub r1,r2,r3
        dict.put("LW",a.subList(4,6));// lw r1, 0(r12)
        dict.put("SW",a.subList(6,8));//sw r1, 0(r12)
        dict.put("SLT",a.subList(8,10));//slt r1, r2, r3
        dict.put("BNE",a.subList(10,12));// bne r1, r2, label
        dict.put("BEQ",a.subList(12,14));// beq r1, r2, label
        dict.put("J",a.subList(14,16));// j label
        dict.put("SYSCALL",a.subList(16,18));// syscall
        dict.put("LI",a.subList(18,20));// li r1, 2
        dict.put("ADDI",a.subList(20,22));// addi r1, r2, 12
        dict.put("LA",a.subList(22,24));// la r1, arr i won't d that (play video), enough naache ke pagal if i want i will listen som e other song as you wish
        return dict;
    }
    public static synchronized Opcodes getInstance(){
        if(op==null)
        {
            op = new Opcodes();
        }
        return op;
    }

}
