import java.io.*;
import java.util.*;
class DeleteContact
{
    String x;               //Holds the First Name of Contact.
    public DeleteContact()
    {
        this.x = "";
    }
    
    public DeleteContact(String x)
    {
        this.x = x;
    }
    
    public void delContact()throws IOException
    {
        FileWriter fw = new FileWriter("ContactRegister1.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);
        
        String s = "";
        while((s = br.readLine()) != null)
        {
            StringTokenizer st = new StringTokenizer(s, "\t");
            int idn = Integer.parseInt(st.nextToken());
            String p = removeHash(st.nextToken() + " " + st.nextToken() + " " + st.nextToken());
            
            if(x.trim().equals(p) == false)
                pw.println(s);                
        }        
        pw.close();     bw.close();     fw.close();     br.close();    fr.close();
    }
    
    public void deleteOldFile()
    {
        File oldFile = new File("ContactRegister.txt");
        oldFile.delete();
    }
    
    public void copyFile()throws IOException
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
    
    public void reAssignID()throws IOException
    {
        int k = 1;  String s = "";
        
        FileReader fr = new FileReader("ContactRegister.txt");
        BufferedReader br = new BufferedReader(fr);
        
        FileWriter fw = new FileWriter("ContactRegister1.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        
        while((s = br.readLine()) != null)
        {
            s = s.substring(s.indexOf("\t"));
            pw.println(k++ + "\t" + s);
        }
        
        pw.close();     bw.close();     fw.close();     br.close();     fr.close();        
        
        FileReader frr = new FileReader("ContactRegister1.txt");
        BufferedReader brr = new BufferedReader(frr);
        
        FileWriter fww = new FileWriter("ContactRegister.txt");
        BufferedWriter bww = new BufferedWriter(fww);
        PrintWriter pww = new PrintWriter(bww);
        
        s = "";
        
        while((s = brr.readLine()) != null)
            pww.println(s);
            
        pww.close();    bww.close();      fww.close();      brr.close();        frr.close();
    }
    
    public void deleteContact()throws IOException
    {
        delContact();    deleteOldFile();    copyFile();    reAssignID();
    }
    
    public String removeHash(String x)
    {
        for(int i = 0; i < x.length(); i++)        
            if(x.charAt(i) == '#')
                return x.substring(0, i).trim() + " " + x.substring(i + 1).trim();        
        return x;
    }
}