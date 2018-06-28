import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.StringTokenizer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class EditContact
{    
    JFrame window;    JPanel panel, btnPanel;    JButton saveBtn, dontSaveBtn;
    JTextField f[];   JLabel labels[];   int idn;      String contactLine;

    public EditContact(String contactLine)
    {
        idn = 0;    this.contactLine = contactLine;

        window = new JFrame("Edit Contact");
        panel = new JPanel(new GridBagLayout());
        btnPanel = new JPanel(new GridBagLayout());

        window.setSize(500, 500);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f = new JTextField[11];
        for(int i = 0; i < f.length; i++)
            f[i] = new JTextField(18);

        saveBtn = new JButton("Save Changes");
        dontSaveBtn = new JButton("Don't Save");
    }

    public void addComponents()
    {        
        labels = new JLabel[11];
        labels[0] = new JLabel(" First Name ");
        labels[1] = new JLabel(" Middle Name ");
        labels[2] = new JLabel(" Last Name ");
        labels[3] = new JLabel(" Email Address ");
        labels[4] = new JLabel(" Mobile Number ");
        labels[5] = new JLabel(" Home / Landline Number ");
        labels[6] = new JLabel(" Notes ");
        labels[7] = new JLabel(" Address");
        labels[8] = new JLabel(" Pin-Code ");
        labels[9] = new JLabel(" City");
        labels[10] = new JLabel(" State");
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0; c.gridy = 0;

        for(int i = 0; i < f.length; i++,c.gridy--)
            panel.add(labels[i], c);  
            
        c.gridx = 1; c.gridy = 0;

        for(int i = 0; i < f.length; i++, c.gridy--)
            panel.add(f[i], c);
        panel.add(btnPanel, c);

        c.gridx = 0;    c.gridy = 0;        btnPanel.add(saveBtn, c);
        c.gridx = 1;                        btnPanel.add(dontSaveBtn, c);
        
        window.add(panel);       
    }

    public String removeHash(String x)
    {
        if(x.trim().equals("#") == true)
            return "";
        else
            return x.trim();
    }
    public void putDetailsInField()
    {        
        StringTokenizer st = new StringTokenizer(contactLine,"\t");
        idn = Integer.parseInt(st.nextToken());
        int k = 0;

        while(st.hasMoreTokens())
            f[k++].setText(removeHash(st.nextToken()));

    }

    public String getChangedDetails(String x)
    {
        String p = x + "\t";
        for(int i = 0; i < f.length; i++)
            p = p + f[i].getText() + "\t";
        return p;    
    }

    public void saveNewDetails()throws IOException
    {
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter("ContactRegister1.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        StringTokenizer st = new StringTokenizer(contactLine, "\t");
        int idn2 = Integer.parseInt(st.nextToken());
        
        String s = "";

        while((s = br.readLine()) != null)
        {
            st = new StringTokenizer(s, "\t");
            int idn1 = Integer.parseInt(st.nextToken());
            
            if(idn1 != idn2)
                pw.println(s);
            else
                pw.println(getChangedDetails(Integer.toString(idn1)));

        }
        
        pw.close();     bw.close();     fw.close();     br.close();     fr.close();
    }

    public void updateOrigninalDetails()throws IOException
    {
        FileReader fr = new FileReader("ContactRegister1.txt");
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter("ContactRegister.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        String s = "";

        while((s = br.readLine()) != null)
            pw.println(s);

        pw.close();     bw.close();     fw.close();     br.close();     fr.close();
    }

    public void handleEvents()
    {
        EditionHandlerClass h = new EditionHandlerClass();
        saveBtn.addActionListener(h);
        dontSaveBtn.addActionListener(h);
    }

    class EditionHandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == saveBtn)            
            {
                try
                {    
                    saveNewDetails();                    
                    updateOrigninalDetails();
                    ContactList ob = new ContactList();
                    ob.callAllMethods();
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
                finally
                {
                    window.dispose();
                }
            }
            else if(e.getSource() == dontSaveBtn)
            {
                window.dispose();
                ContactList ob = new ContactList();
                try
                {
                    ob.callAllMethods();
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }            
        }
    }

    public void callAllMethods()throws IOException
    {
        addComponents();        putDetailsInField();        handleEvents();
    }
    public static void main(String args[])
    {
        //EditContact ob = new EditContact("");
       // ob.addComponents();       // ob.putDetailsInField();//ob.handleEvents();
    }
}