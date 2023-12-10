/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import martmain.Login;
import martmain.SignUp;
import martmain.UserPanel;

/**
 *
 * @author achma
 */
public class UserController {
    private SignUp regist;
    private Login lgn;
    private User usrm;
    private Connection conn;

    public UserController(SignUp regist, Login lgn) {
        this.regist = regist;
        this.lgn = lgn;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.conn = koneksiDB.getConn();
    }

    public void regist() {
        usrm = new User();
        usrm.setName(regist.getNama().getText());
        usrm.setEmail(regist.getEmail().getText());
        usrm.setPassword(regist.getPass().getText());
        usrm.setNoHp(regist.getNo().getText());

        String sql = "INSERT INTO users (name, email, password, noHp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            if ("".equals(regist.getNama().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Name is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(regist.getEmail().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Email Address is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(regist.getPass().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Password is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(regist.getNo().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "No Wa is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                st.setString(1, usrm.getName());
                st.setString(2, usrm.getEmail());
                st.setString(3, usrm.getPassword());
                st.setString(4, usrm.getNoHp());

                int rowsInserted = st.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showConfirmDialog(regist, "New account has been created successfully!", "Info",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showConfirmDialog(regist, "Gagal menambahkan produk.", "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                st.execute(sql);
            }

        } catch (Exception e) {
            System.out.println("Error!" + e.getMessage());
        }
    }
    
    public void Login() {
        usrm = new User();
        usrm.setEmail(lgn.getEmail().getText());
        usrm.setPassword(lgn.getPass().getText());
        
        String sql = "SELECT * FROM users where email = ? AND password = ?";
        
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            if ("".equals(regist.getNama().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Name is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ("".equals(regist.getPass().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Password is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                st.setString(1, usrm.getEmail());
                st.setString(2, usrm.getPassword());
                
                int rowsInserted = st.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showConfirmDialog(regist, "Berhasil Login.", "Info",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showConfirmDialog(regist, "Incorrect email or password.", "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
                st.execute(sql);
            }
    } catch (Exception e) {
            System.out.println("Error!" + e.getMessage());
        }
    }
    
    public void showUserpanel() {
        UserPanel up = new UserPanel();
        up.setVisible(true);
        up.pack();
        up.setLocationRelativeTo(null);
    }
    
    public void showLoginPanel() {
        Login lgn = new Login();
        lgn.setVisible(true);
        lgn.pack();
        lgn.setLocationRelativeTo(null);
    }
}
