/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ter;

import database.Mysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.Register;

/**
 *
 * @author Telnet
 */
public class TER {
    private static PreparedStatement pst;
    private ResultSet rs;

    /**
     * @param args the command line arguments
     */
    public  void main() {
        try {
             pst= Mysql.connect("soen").prepareStatement("select tel1,id FROM classmembers");
        } catch (SQLException ex) {
            Logger.getLogger(TER.class.getName()).log(Level.SEVERE, null, ex);
        }
        String original="",id="";
        try {
            ResultSet rs=pst.executeQuery();
            
            while(rs.next()){
                original=rs.getString("tel1");
                id=rs.getString("id");
                String fin = null;
                if(original.startsWith("+254")){
                fin=original.replace("+254", "+2547");
              }
               else if(!original.startsWith("+254")){
                  
                    
              }
                
              pst=  Mysql.connect("soen").prepareStatement(""
                        + "UPDATE classmembers set tel1=? where tel1=?");
              pst.setString(1, fin);
              
              pst.setString(2, original);
              pst.execute();
                System.out.println("updated "+original +" to "
                        .concat(fin));
            }}
            catch (SQLException ex) {
            try {
                pst=Mysql.connect("soen").prepareStatement("DELETE from classmembers where tel1=?");
            pst.setString(1, id);
            } catch (SQLException ex1) {
                Logger.getLogger(TER.class.getName()).log(Level.SEVERE, null, ex1);
            }
            try {
                pst.execute();
            } catch (SQLException ex1) {
                Logger.getLogger(TER.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
        }
    }
    
   /* private void ch(){
        try {
            pst=Mysql.connect("soen").prepareStatement("select id,mobile from cu");
        } catch (SQLException ex) {
            Logger.getLogger(TER.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            rs=pst.executeQuery();
            String mobile="",id="";
            while(rs.next()){
                mobile=rs.getString("mobile");
                id=rs.getString("id");
                if(mobile.length()!=13){
                    pst=Mysql.connect("soen").prepareStatement("delete from cu where id=?");
                    pst.setString(1, id);
                    pst.execute();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TER.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/
    
    public static void main(String[] args) {
        TER n=new TER();
        n.main();
    }
}
