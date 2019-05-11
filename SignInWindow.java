import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class SignInWindow 
{
    JFrame window;    JTextField user_id;   JPasswordField pass;
    JButton SignIn;
    public SignInWindow()
    {
        window = new JFrame("Sign In");
        window.setVisible(true);
        window.setSize(350, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }
    public void showLogin()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel name = new JLabel("User ID", JLabel.CENTER);
        JLabel passwd = new JLabel("Password", JLabel.CENTER);
        user_id = new JTextField(20);
        pass = new JPasswordField(20);
        SignIn = new JButton("Sign In");
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        
        c.gridx = 0;    c.gridy = 0;    panel.add(name, c);        
        c.gridx = 2;    c.gridy = 0;    panel.add(user_id, c);        
        c.gridx = 0;    c.gridy = -1;   panel.add(passwd, c);        
        c.gridx = 2;    c.gridy = -1;   panel.add(pass, c);        
        c.gridx = 2;    c.gridy = -3;   panel.add(SignIn, c);           
       
        window.add(panel);
    }
    public void signIn()
    {
        HandlerClass h = new HandlerClass(); 
        pass.addActionListener(h);
        SignIn.addActionListener(h);
    }
    public void callAllMethods()
    {
        showLogin();        signIn();
    }
    public static void main()
    {
        SignInWindow ob = new SignInWindow();
        ob.showLogin();        ob.signIn();
    }
    
    class HandlerClass implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == SignIn || e.getSource() == pass)
            {
                if(user_id.getText().equals("") == true)
                JOptionPane.showMessageDialog(null, "Please Enter User ID");
                else if(pass.getText().equals("") == true)
                JOptionPane.showMessageDialog(null, "Please Enter Password");
                else if(pass.getText().equals("12345") == false)
                JOptionPane.showMessageDialog(null, "Wrong Password");
                else
                {
                    window.dispose();     // dispose function programmatically closes the JFrame.                    
                    JOptionPane.showMessageDialog(null, "Congratulation. You have Signed In");                    
                    AfterSignIn win  = new AfterSignIn();
                    win.callAllMethods();
                }
            }
        }
    }    
}
