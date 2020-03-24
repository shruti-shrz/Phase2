package Simulator;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PreParser{
    ArrayList<String> all = new ArrayList<>();
    HashMap<String,Integer> base = new HashMap<>();
    Memory memory = Memory.getInstance();
    HashMap<String,Integer> labels = new HashMap<>();
    int lineNum = 0;
    PreParser(BufferedReader file){
        String line;
        try{
            while((line = file.readLine()) != null){
                line = line.trim();
                all.add(line);
                if (line.length() != 0) {
                    if(line.charAt(line.length() - 1) == ':') {

                        line = line.substring(0, line.length() - 1);

                        labels.put(line, lineNum);
                    }

                    lineNum++;
                    // System.out.println(labels);

                }
            }
            this.storeMem(all);
          //  System.out.println(base);
            //System.out.println(labels);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    void storeMem(ArrayList<String> val){
       // System.out.println("recheck");
        String[] set;
        int mc = 0;
        for(int i =1;i<textIndex(val);i++){
            if(val.get(i).charAt(val.get(i).length()-1)==':') {
              //  System.out.println("check");
                set = val.get(i + 1).split("[ ,]+");
                base.put(val.get(i).substring(0,val.get(i).length()-1),mc);
                for (int j = 1; j < set.length; j++) {
                    memory.insert(Integer.parseInt(set[j]));
                    mc++;
                }
            }
        }
    }
    int textIndex(ArrayList<String> values){
        for(int i=0;i<values.size();i++)
            if(values.get(i).equals(".text")){
                return i;
            }
        return -1;
    }
    public ArrayList<String> getList(){
        return all;
    }
}



class Parser{
    Dictionary<String,List<String>> opcodes;
    ArrayList<String> allLines;
    Memory m;
    int[] Registers;
    String[] arr;
  //  final Lock pipelineLock = new ReentrantLock();
    PreParser q;
    static int c=0;
    String[] currInstr;
   String[] decodeinst(String line){
//       if (line.charAt(line.length() - 1) == ':') {
//           line = line.substring(0, line.length() - 1);
//           arr[0] = "-2";
//       }
       String[] set = line.split("[ ,]+");
//           for(int j=0;j<set.length;j++){
//               System.out.print(set[j]+" ");
//           }
       List<String> l = opcodes.get(set[0].toUpperCase());
    //System.out.println(l);
//       System.out.println(l.get(1));
    if(l==null){
        arr[0] = "-2";
    }else
    if(l.get(1)=="r")
    {
        arr[0] = l.get(0);
      arr[1] = set[1].substring(1);
      arr[2] = set[2].substring(1);
      arr[3] = set[3].substring(1);
    }
    else
        if((l.get(1)=="i" && l.get(0) == "2") || (l.get(1)=="i" && l.get(0) == "3"))
        {
            int t = set[2].indexOf('(');
            arr[0] = l.get(0);
            arr[1] = set[1].substring(1);
            arr[2] = set[2].substring(3,set[2].length()-1);
            arr[3] = set[2].substring(0,t);
        }else
        if((l.get(1)=="i" && l.get(0)=="9") || (l.get(1)=="i" && l.get(0)=="11"))
        {
//            System.out.println(set);
//            System.out.println(n);
//            System.out.println(l.get(0));
            arr[0] = l.get(0);
            arr[1] = set[1].substring(1);
            arr[2] = set[2];
//            for(int i=0;i<3;i++)
//            {
//                System.out.println(arr[i]);
//            }
        }else
        if(l.get(1)=="i")
        {
            System.out.print("its beq");
//            for(int i=0;i<set.length;i++)
//            {
//                System.out.println(set[i]);
//            }
            arr[0] = l.get(0);
            arr[1] = set[1].substring(1);
            arr[2] = set[2].substring(1);
            arr[3] = set[3];

        }else
        if(l.get(1)=="j"){
            arr[0] = l.get(0);
            arr[1] = set[1];
        }else
        if(l.get(1)=="s"){
           arr[0] = l.get(0);
        }

        return arr;
    }
    boolean areDependent(String[] curr, String[] prv)
    {
//        for(int j=0;j<curr.length;j++) {
//            System.out.println(curr[j] + " ");
//        }
        if(prv[0].equals("-1"))
        {
            return false;
        }
//        for(int j=0;j<prv.length;j++) {
//            System.out.println(prv[j] + " ");
//        }
        if(prv[1].equals(curr[2]) || prv[1].equals(curr[3]))
        {
            System.out.println("pagal aadmi");
            return true;
        }
        else
            return false;
    }
   String[] prevInstr;
//    String[] currInstr;
    public void startSimulation()
    {
        String line;
        int stall=0;
        for(int i=0;i<prevInstr.length;i++)
        prevInstr[i] = "-1";
        while (alu.counter < (allLines.size())){
//                System.out.println("1st Stall " + stall);
            line = allLines.get(alu.counter);
            //System.out.println(alu.counter +" "+ line);
//                int flag=0;
            currInstr = decodeinst(line);
            if(currInstr[0].equals("-2"))
            {
                alu.counter++;
//                continue;
            }
            if(currInstr[0].equals("8")){
                break;
            }
//            if (line.charAt(line.length() - 1) == ':') {
//                line = line.substring(0, line.length() - 1);
//            }
//            for(int i=0;i<currInstr.length;i++)




//            if (q.labels.containsKey(currInstr[0])) {
//                System.out.println("c2");
//                alu.counter++;
//                continue;
//            }
            for(int j=0;j<currInstr.length;j++)
            {
                System.out.print(currInstr[j] + " ");
            }
//            if(prevInstr[0]!="-1") {
//                for (int j = 0; j < prevInstr.length; j++) {
//                    System.out.println(prevInstr[j] + " ");
//                }
//            }
            System.out.println("heyy");
            if (currInstr[0] == "5" || currInstr[0] == "6") {
                stall = stall + 2;// pipelineLock.lock();
            }else
            if((prevInstr[0].equals("11")&&currInstr[0].equals("2"))||(prevInstr[0].equals("11")&&currInstr[0].equals("3")))
                stall++;
            if (areDependent(currInstr, prevInstr) == true) {
                stall++;
                //  pipelineLock.lock();
            }
//                for(int i=0;i<currInstr.length;i++)
//                    prevInstr[i] = currInstr[i];
//  alu.executer(currInstr);
                for(int i=0;i<currInstr.length;i++)
               prevInstr[i] = currInstr[i];

//                for(int i=0;i<prevInstr.length;i++)
//                System.out.println(prevInstr[i]);
               // String[] finalCurrInstr = currInstr;
            int k;
                k = alu.executer(currInstr,Registers);
            if(Integer.parseInt(currInstr[0])==3)
            {
                mem(k,currInstr);
            }
            if(Integer.parseInt(currInstr[0])==2)
            {
                k =  mem(k,currInstr);
            }
                wb(k,currInstr);
//                new Thread(() -> {
//                    try {
//                        Thread.sleep(50);
//                    } catch (Exception e) {  }
//                    alu.executer(finalCurrInstr);
////                    alu.counter.getAndIncrement();
//                    try {
//                        //pipelineLock.notify();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                   // for(int i=0;i<alu.getReg().length;i++)
//                   // System.out.println(alu.getReg());
//                }).start();
//                try {
//                    Thread.sleep(50);
//                } catch (Exception e) {  }
        }
       // }
        System.out.println("Stall " + stall);
    }
    ALU alu;
//    final Lock queueLock = new ReentrantLock();
    Parser(BufferedReader file){
        q = new PreParser(file);
        allLines = q.getList();
//        System.out.println(allLines);
//        System.out.println(q.labels);
        alu = ALU.getInstance(file,allLines,q.base,q.labels);
        Opcodes pt = Opcodes.getInstance();
        opcodes = pt.opt();
        arr = new String[4];
        currInstr = new String[4];
        prevInstr = new String[4];
        Registers = new int[32];
        m = Memory.getInstance();
//        prevInstr = new String[4];
//        currInstr = new String[4];
//        System.out.println(base);
    }
    int mem(int v,String[] g)
    {
        if(Integer.parseInt(g[0])==2)
        return m.getMem().get(v);
        else
            if(Integer.parseInt(g[0])==3)
            {
                m.getMem().set(v,Registers[Integer.parseInt(g[1])]);
            }
            return 0;
    }
    ALU getalu(){
        return alu;
    }
    int[] getReg()
    {
        return Registers;
    }
    void wb(int val,String[] g)
    {
        if(val!=-1)
        {

            Registers[Integer.parseInt(g[1])] = val;
        }


    }

}


