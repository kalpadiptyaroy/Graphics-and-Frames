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
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

class ContactList 
{
    JFrame window;    JPanel panel, listPanel, detPanel, btnPanel;    JList list;
    JButton delButton, editButton;    JLabel text[] = new JLabel[6];
    String n, contactLine;

    public ContactList() 
    {
        window = new JFrame("Contacts");
        window.setVisible(true);
        window.setSize(670, 390);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        detPanel = new JPanel(new GridBagLayout());
        btnPanel = new JPanel(new GridBagLayout());
        listPanel = new JPanel(new GridBagLayout());

        n = ""; contactLine = "";
    }

    public void getContacts() throws IOException 
    {
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);
        DefaultListModel lt = new DefaultListModel();

        String s = "";

        while ((s = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s, "\t");
            int idn = Integer.parseInt(st.nextToken());
            String fullname = "   " + st.nextToken().trim() + " " + st.nextToken().trim() + " " + st.nextToken().trim()
                + "   ";
            lt.addElement(removeHash(fullname));
        }

        br.close();        fr.close();

        list = new JList(lt);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(13);
        list.setSize(400, 400);
    }

    public String removeHash(String x) 
    {
        for (int i = 0; i < x.length(); i++)         
            if (x.charAt(i) == '#')
                return "   " + x.substring(0, i).trim() + " " + x.substring(i + 1).trim() + "   ";        
        return x;
    }

    public void showWindow() 
    {
        JLabel msg1 = new JLabel("Contact List");
        JLabel msg2 = new JLabel("Contact Details");
        JScrollPane jsp = new JScrollPane(list);      
        JPanel sepPanel = new JPanel(new GridBagLayout());        

        JSeparator sep = new JSeparator();
        sep.setOrientation(SwingConstants.VERTICAL);
        sep.setVisible(true);
        sep.setPreferredSize(new Dimension(1, 300));    
        sepPanel.add(sep);    

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 5, 5);
        c.gridheight = 9;
        c.gridx = 0;       c.gridy = 0;       panel.add(listPanel, c);      c.gridheight = 10;
        c.gridx = 1;       c.gridy = 0;       panel.add(sepPanel, c);
        c.gridx = 2;       c.gridy = 0;       panel.add(detPanel, c);

        c = new GridBagConstraints();                c.insets = new Insets(10, 5, 5, 5);
        c.gridx = 0;       c.gridy = 0;              listPanel.add(msg1, c);  
        c.gridx = 0;       c.gridy = -1;             listPanel.add(jsp, c);  
        c.anchor = GridBagConstraints.CENTER; 
        c.insets = new Insets(1, 5, 5, 5);       
        c.gridx = 0;       c.gridy = 0;              detPanel.add(msg2, c);
        

        window.add(panel);

        addLabelsAndButtons(); // Note Here is a Function Call.
        handleEvents(); // Note Here is a Function Call.
    }

    public void handleEvents()
    {
        // Adding Selection Listener.
        SelectionHandlerClass h = new SelectionHandlerClass();
        list.addListSelectionListener(h);

        DeletionHandlerClass d = new DeletionHandlerClass();
        delButton.addActionListener(d);

        EditionHandlerClass E = new EditionHandlerClass();
        editButton.addActionListener(E);
    }

    public void addLabelsAndButtons() 
    {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 9, 9, 9);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;     c.gridy = -1;        int k = 0;

        do 
        {
            text[k] = new JLabel();
            detPanel.add(text[k++], c);
            c.gridy--;
        } while (k < 6);

        detPanel.add(btnPanel, c);

        delButton = new JButton("Delete Contact");
        editButton = new JButton("Edit Contact");
        delButton.setVisible(false);
        editButton.setVisible(false);

        c.gridy--;      btnPanel.add(delButton, c);
        c.gridx++;      btnPanel.add(editButton, c);        
    }

    public void viewDetails(String x)throws IOException 
    {
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);

        delButton.setVisible(true);     editButton.setVisible(true);

        String s = "";
        do 
        {
            s = br.readLine();      contactLine = s;
            StringTokenizer st = new StringTokenizer(s, "\t");

            int idn = Integer.parseInt(st.nextToken());
            String p = removeHash(st.nextToken() + " " + st.nextToken() + " " + st.nextToken());           

            if (x.trim().equals(p.trim()) == true) 
            {
                text[0].setText("Name : " + p);

                p = st.nextToken();
                text[1].setText("Email : " + p);

                p = st.nextToken();
                text[2].setText("Mobile : " + p);

                p = st.nextToken();
                text[3].setText("Landline : " + p);

                p = st.nextToken();
                text[4].setText("Notes : " + p);

                p = st.nextToken() + "  " + st.nextToken() + "  " + st.nextToken();
                text[5].setText("Address : " + p);

                s = null;
            }
        } 
        while (s != null);
        br.close();    fr.close();
    }

    public void callAllMethods() throws IOException 
    {
        getContacts();       showWindow();
    }
    // Note Here is a Sub Class Defined!
    class SelectionHandlerClass implements ListSelectionListener 
    {
        public void valueChanged(ListSelectionEvent e) 
        {
            if (e.getValueIsAdjusting() == true)
                try 
                {
                    n = list.getSelectedValue().toString();
                    viewDetails(n);
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex.getMessage());
                }
        }
    }
    // Note Here is a Sub Class Defined!
    class DeletionHandlerClass implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == delButton) 
            {
                DeleteContact ob = new DeleteContact(n);
                try 
                {
                    ob.deleteContact();    window.dispose();
                    ContactList obj = new ContactList();
                    obj.callAllMethods();
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex.getMessage());
                }

            }
        }
    }
    // Note Here is a Sub Class Defined!
    class EditionHandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == editButton)
            {
                window.dispose();
                EditContact ob = new EditContact(contactLine);
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
    public static void main(String args[]) throws IOException 
    {
        ContactList ob = new ContactList();
        ob.getContacts();        ob.showWindow(); 
    }
}