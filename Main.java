package Simulator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Main {
    JFrame f;
    JTextArea tarea;
    Registers r;
    JScrollPane scrollPane = new JScrollPane(tarea);
    private JButton button1;
    Main(BufferedReader file){
        // does not help much btw
       tarea = new JTextArea(10, 10);
       // tarea.setBounds(80,100,200,200);
        f=new JFrame();//creating instance of JFrame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Memory m = Memory.getInstance(); //

        Parser p0 = new Parser(file);
        JButton b = new JButton("Simulate");//creating instance of JButton
        b.setBounds(70,400,90, 20);
        StringBuilder sb = new StringBuilder();
        for (Integer i : m.getMem()) {
            sb.append(i == null ? "" : i.toString()+", ");
        }
        JLabel l = new JLabel();
        l.setText(sb.toString());
        l.setBounds(130,350,200, 40);

        JLabel l2 = new JLabel("Memory = ");
        l2.setBounds(70,350,100, 40);

        JLabel l3 = new JLabel("Registers");
        l3.setBounds(50,5,120, 20);

        JPanel panel = new JPanel();
        panel.setBounds(40,30,30, 400);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panel2 = new JPanel();
        panel2.setBounds(100,30,20, 400);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        ArrayList<JLabel> lbw0 = new ArrayList<>();
        ArrayList<JLabel> lbw1 = new ArrayList<>();
        for(int i=0;i<=19;i++)
        {
            JLabel p = new JLabel();
            lbw0.add(p);
            lbw0.get(i).setText("t" + i + " = ");
            panel.add(lbw0.get(i));
        }
        for(int i=0;i<=19;i++)
        {
            JLabel p = new JLabel();
            lbw1.add(p);
            lbw1.get(i).setText(String.valueOf(p0.getalu().getReg()[i]));
            panel2.add(lbw1.get(i));
        }
                try {
            tarea.read(file,"Reading");// leta try with some small file which fits our bound
        } catch (IOException e) {
            e.printStackTrace();
        }
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p0.startSimulation();
                StringBuilder sb = new StringBuilder();
                for (Integer i : m.getMem()) {
                    sb.append(i == null ? "" : i.toString()+", ");
                }
                l.setText(sb.toString());
                for(int i=0;i<=19;i++)
                {
                    lbw1.get(i).setText(String.valueOf(r.getC()[i]));
                    panel2.add(lbw1.get(i));
                }
            }
        });

        f.add(b);//adding button in JFrame
        f.add(l2);
        f.add(l3);
        f.add(l);
        f.add(panel);
        f.add(panel2);
        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);
        f.add(tarea);
       //f.pack();//using no layout managers
        f.setVisible(true);
       //making the frame visible
    }

    public static void main(String[] args) {
        BufferedReader file;
        try {
            file = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/Shruti priya/Downloads/bubblesort.asm")));
//                PreParser q = new PreParser(file);
//            Parser p = new Parser(file);

            new Main(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}