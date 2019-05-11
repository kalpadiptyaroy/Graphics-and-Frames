import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

class AfterSignIn
{
    JFrame window;    JPanel panel, btnPanel;    JButton contactlist, addcontact;
    public AfterSignIn()
    {
        window = new JFrame("Welcome User");
        window.setVisible(true);
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        
        panel = new JPanel(new GridBagLayout());
        btnPanel = new JPanel(new GridBagLayout());
        
        contactlist = new JButton("View Contacts");
        addcontact = new JButton("Add Contact");
    }

    public void showWindow()
    {       
        JLabel msg1 = new JLabel("Welcome Dear User!");
        JLabel msg2 = new JLabel("Please Select one of the Actions Below");      

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;        c.gridy = 0;        panel.add(msg1, c);
        c.gridx = 0;        c.gridy = -1;       panel.add(msg2, c);
        
        c.gridy = -2;   panel.add(btnPanel, c);
        
        c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;        c.gridy = -2;       btnPanel.add(contactlist, c);
        c.gridx = 1;        c.gridy = -2;       btnPanel.add(addcontact, c);

        window.add(panel);
    }

    public void handleEvents()
    {
        HandlerClass h = new HandlerClass();
        contactlist.addActionListener(h);
        addcontact.addActionListener(h);
    }
    class HandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == addcontact)
            {
                window.dispose();
                NewContact ob = new NewContact();
                ob.callAllMethods();
            }
            else if(e.getSource() == contactlist)
            {                
                try
                {
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
        }
    }
    public void callAllMethods()
    {
        showWindow();       handleEvents();
    }

    public static void main(String args[])
    {
        AfterSignIn ob = new AfterSignIn();
        ob.showWindow();       ob.handleEvents();
    }
}
