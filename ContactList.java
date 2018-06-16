import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
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
    JFrame window;   JPanel panel;   JList list;
    public ContactList()
    {
        window = new JFrame("Contacts");
        window.setVisible(true);
        window.setSize(400, 400);
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
        list.setSize(400, 400);
    }
    
    public String removeHash(String x)
    {
        for(int i = 0; i < x.length(); i++)
        {
            if(x.charAt(i) == '#')
            return x.substring(0, i) + x.substring(i + 1);
        }
        return x;
    }
    public void showWindow()
    {
        JLabel msg = new JLabel("Contact List");
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        
        c.gridx = 0;    c.gridy = 0;
        panel.add(msg, c);
        
        c.gridx = 0;    c.gridy = -8;
        JScrollPane jsp = new JScrollPane(list);
        panel.add(jsp);
        
        window.add(panel);
    }
    
    public void callAllMethods()throws IOException
    {
        getContacts();
        showWindow();
    }
    public static void main()throws IOException
    {
        ContactList ob = new ContactList();
        ob.getContacts();
        ob.showWindow();
    }
}
