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

/**
 *
 * @author Telnet
 */
public class CA {

    String message = "";
    PreparedStatement pst;
    ResultSet rs;
    String data[] = new String[2];

    private void C() {
        try {
            pst = Mysql.connect("soen")
                    .prepareStatement("select gender,first_name,last_name,phone_number from ca");
        } catch (SQLException ex) {
            Logger.getLogger(CA.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            rs = pst.executeQuery();
            while (rs.next()) {
                data[0] = rs.getString("gender");
                data[1] = (rs.getString("first_name") + " " + rs.getString("last_name"));

                message = processMessage(data);

                sendSMS(rs.getString("phone_number"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param data: index 0 {sex} 1{firstname+lastname}
     * @return
     */
    private String processMessage(String data[]) {
        message = "Dear {sex}{name}, Kisii University Computing Sciences Association(KUCSA ) "
                + "invites you to our Computer day out tommorow Friday,13th October 2017  "
                + "from 8:00AM to 4:00PM at Chacha Square next to Saturn hostel."
                + "We will offer free phones and computers repair and troubleshooting."
                + "Regards,"
                + " "
                + "KUCSA PR ";

        String sex = data[0].equalsIgnoreCase("male") ? "Mr." : "Miss.";

        return message.replace("{name}", " " + data[1] + " ").replace("{sex}", sex);
    }

    private void sendSMS(String recepient) {
        try {
            pst = Mysql.connect("soen").
                    prepareStatement("INSERT INTO outbox(receiver,msg,msgtype,sender)"
                            + " VALUES(?,?,?,?)");
            pst.setString(1, recepient);
            pst.setString(2, message);
            pst.setString(3, "sms.text");
            pst.setString(4, "+2547");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            pst.execute();
        } catch (SQLException ex) {
        }
        System.out.println("sent");
    }

    public static void main(String[] args) {
        CA v = new CA();
        v.C();
    }
}
