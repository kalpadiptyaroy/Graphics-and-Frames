import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

class ContactList
{
    JFrame window;   JPanel panel, detPanel;   JList list; 

    JLabel name, mobileNo, email, addr;

    public ContactList()
    {
        window = new JFrame("Contacts");
        window.setVisible(true);
        window.setSize(550, 550);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

        panel = new JPanel(new GridBagLayout());        
    }

    public void getContacts()throws IOException
    {
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);

        DefaultListModel lt = new DefaultListModel();        

        String s = "";

        while((s = br.readLine()) != null)
        {
            StringTokenizer st = new StringTokenizer(s, "\t");            
            String fullname = "   " + st.nextToken().trim() + "  " + st.nextToken().trim() + "  " + st.nextToken().trim() + "   ";
            lt.addElement(removeHash(fullname));
        }

        br.close();     fr.close();

        list = new JList(lt);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);
        list.setSize(400, 400);
    }

    public String removeHash(String x)
    {
        for(int i = 0; i < x.length(); i++)
        {
            if(x.charAt(i) == '#')
                return x.substring(0, i) + "  " + x.substring(i + 1);
        }
        return x;
    }

    public void showWindow()
    {
        JLabel msg1 = new JLabel("Contact List");
        JLabel msg2 = new JLabel("Contact Details");

        JSeparator sep = new JSeparator();
        sep.setOrientation(SwingConstants.VERTICAL);
        sep.setVisible(true);

        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;    c.gridy = 0;    panel.add(msg1, c);
        c.gridx = 1;    c.gridy = 0;    panel.add(sep);
        c.gridx = 1;    c.gridy = 0;    panel.add(msg2, c);

        JScrollPane jsp = new JScrollPane(list);
        c.weighty = -6;
        c.gridx = 0;    c.gridy = -1;    panel.add(jsp, c);

        window.getContentPane().add(panel);

                                                //Adding Selection Listener.
        HandlerClass h = new HandlerClass();
        list.addListSelectionListener(h);
    }      

    public void viewDetails(String x)throws IOException
    {
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);

        int k = 0;  String s = "";

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 2;    c.gridy = -1;   

        do
        { 
            s = br.readLine();
            StringTokenizer st = new StringTokenizer(s, "\t");
            String p = st.nextToken() + "  " + removeHash(st.nextToken()) + "  " + st.nextToken();
            
            if(x.trim().equals(p) == true)
            {               
                detPanel = new JPanel(new GridBagLayout());
                
                JLabel text = new JLabel(" Name : " + p);  
                c.weighty = -1;
                detPanel.add(text, c);      c.gridy--;

                p = st.nextToken();
                text = new JLabel(" Email : " + p);
                detPanel.add(text, c);      c.gridy--;

                p = st.nextToken();
                text = new JLabel(" Mobile : " + p);
                detPanel.add(text, c);      c.gridy--;

                p = st.nextToken();
                text = new JLabel(" Landline : " + p);
                detPanel.add(text, c);      c.gridy--;

                p = st.nextToken();
                text = new JLabel(" Notes : " + p);
                detPanel.add(text, c);      c.gridy--;

                p = st.nextToken()+ "  " + st.nextToken() + "  " + st.nextToken();
                text = new JLabel(" Address : " + p);
                detPanel.add(text, c);     
                
                c = new GridBagConstraints();
                c.gridx = 1;    c.gridy = -1;
                detPanel.revalidate();
                panel.add(detPanel, c);
                s = null;                  
            }
        }
        while(s != null);
    }

    public void callAllMethods()throws IOException
    {
        getContacts();
        showWindow();
    }

    class HandlerClass implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            if(e.getValueIsAdjusting() == true)
            {
                try
                {
                    viewDetails(list.getSelectedValue().toString());
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }    

    public static void main()throws IOException
    {
        ContactList ob = new ContactList();
        ob.getContacts();
        ob.showWindow();
    }
}
