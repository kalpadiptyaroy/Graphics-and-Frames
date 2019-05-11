import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.StringTokenizer;

class NewContact
{
    JFrame window;    JTextField f[];    JButton save, clear;
    public NewContact()
    {
        window = new JFrame("New Contact");
        window.setVisible(true);
        window.setResizable(true);
        window.setSize(450, 550);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f = new JTextField[11];
        save = new JButton("Save Contact");
        clear = new JButton("Clear");
        
        for(int i = 0;i < 11; i++)
            f[i] = new JTextField(18);
    }

    public void showForm()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel btnPanel = new JPanel(new GridBagLayout());
        
        JLabel label[] = new JLabel[11];
        label[0] = new JLabel(" First Name ");
        label[1] = new JLabel(" Middle Name ");
        label[2] = new JLabel(" Last Name ");
        label[3] = new JLabel(" Email Address ");
        label[4] = new JLabel(" Mobile Number ");
        label[5] = new JLabel(" Home / Landline Number ");
        label[6] = new JLabel(" Notes ");
        label[7] = new JLabel(" Address");
        label[8] = new JLabel(" Pin-Code ");
        label[9] = new JLabel(" City");
        label[10] = new JLabel(" State");        

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 5);       
        c.anchor = GridBagConstraints.LINE_END;        

        for(int i = 0; i < 11; i++)
        {
            c.gridx = 0;
            c.gridy = -i;   panel.add(label[i], c);
            c.gridx = 1;    panel.add(f[i], c);
        }       
        
        c.gridy--;      panel.add(btnPanel, c);
        c.gridx = 0;    c.gridy = 0;      btnPanel.add(save, c);
        c.gridx = 1;    c.gridy = 0;      btnPanel.add(clear, c);      

        window.getContentPane().add(panel);
    }
    
    public boolean isEmptyField(JTextField f)
    {
        if(f.getText().equals("") == true)
            return true;
        return false;
    }
    public boolean checkFields()
    {
        for(int i = 0; i < 11; i++)        
            if(isEmptyField(f[i]) == true && i != 1 )
                return true;        
        return false;
    }
    public void saveData()throws IOException
    {
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);
        
        String s = "", p = ""; 
        while((s = br.readLine()) != null)
        {
            if(s.equals("") == false)
            {
                StringTokenizer st = new StringTokenizer(s, "\t");
                p = st.nextToken();                
            }            
        }
        
        int idn = 0;        
        if(p.equals("") == true)        
            idn = 1;         
        else       
            idn = Integer.parseInt(p) + 1;        
        
        FileWriter fw = new FileWriter("ContactRegister.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);        
        
        pw.print(idn + "\t");
        for(int i = 0; i < 11; i++)        
            if(f[i].getText().equals("") == true)
                pw.print("#" + "\t");
            else
                pw.print(f[i].getText().trim() + "\t");        
        pw.println();

        pw.close();        bw.close();        fw.close();

    }

    public void handle()
    {
        HandlerClass h = new HandlerClass();
        save.addActionListener(h);
        clear.addActionListener(h);
    }

    public void callAllMethods()
    {
        showForm();      handle();
    }

    class HandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == save && checkFields() == false)
            {
                try 
                {               
                    saveData();   
                }            
                catch(Exception ex)                
                {
                    System.out.println(ex.getMessage());
                }                 
                finally
                {
                    window.dispose();
                    AfterSignIn win  = new AfterSignIn();
                    win.callAllMethods();
                }
            }
            else if(e.getSource() == save && checkFields() == true)
                JOptionPane.showMessageDialog(null, "There are Empty Fields. Please Fill them");
            else if(e.getSource() == clear)
            {                
                window.dispose(); 
                NewContact ob = new NewContact();
                ob.callAllMethods();
            }
        }
    }

    public static void main(String args[])
    {
        NewContact ob = new NewContact();
        ob.showForm();        ob.handle();
    }
}
