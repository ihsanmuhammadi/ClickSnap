/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Admin;
import Model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import martmain.AdminPanel;
import martmain.Login;
import martmain.Products;
import martmain.Purchase;
import martmain.SignUp;
import martmain.TransaksiAdmin;
import martmain.TransaksiUser;
import martmain.UserPanel;

/**
 *
 * @author achma
 */
public class UserController {
    private SignUp regist;
    private Login lgn;
    private Customer usrm;
    private Connection conn;
    private Customer currentUser;
    private UserPanel up;
    private AdminPanel ap;

    public UserController(SignUp regist, Login lgn, UserPanel up) {
        this.regist = regist;
        this.lgn = lgn;
        this.up = up;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.conn = koneksiDB.getConn();

    }

    public void regist() {
        usrm = new Customer(
                0,
                regist.getNama().getText(),
                regist.getEmail().getText(),
                regist.getPass().getText(),
                regist.getNo().getText());

        // Validate email format
        if (!isValidEmail(usrm.getEmail())) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid email format", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the email already exists in the database
        if (isEmailExists(usrm.getEmail())) {
            JOptionPane.showMessageDialog(new JFrame(), "Email already exists. Please choose a different email.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if email already exists
        }

        // Validate password length
        if (usrm.getPassword().length() < 5) {
            JOptionPane.showMessageDialog(new JFrame(), "Password should be at least 5 characters long", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO users (name, email, password, role, noHp) VALUES (?, ?, ?, 'user', ?)";

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
                JOptionPane.showMessageDialog(new JFrame(), "Whatsapp Number is require", "Error",
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
                    showLoginPanel();
                } else {
                    JOptionPane.showConfirmDialog(regist, "Gagal menambahkan akun.", "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    showRegistPanel();
                }
                st.execute(sql);
            }

        } catch (Exception e) {
            System.out.println("Error!" + e.getMessage());
        }
    }

    public void login() {
        usrm = new Customer(
                0,
                null,
                lgn.getEmail().getText(),
                lgn.getPass().getText(),
                null);

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            if ("".equals(lgn.getEmail().getText()) || "".equals(lgn.getPass().getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Email and password are required", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                st.setString(1, usrm.getEmail());
                st.setString(2, usrm.getPassword());

                ResultSet resultSet = st.executeQuery();
                if (resultSet.next()) {
                    String role = resultSet.getString("role");

                    if ("admin".equals(role)) {
                        JOptionPane.showConfirmDialog(lgn, "Login successful as Admin.", "Info",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        Admin currentUser = new Admin(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("noHp"));
                        showAdminPanel();
                        lgn.dispose();
                    } else if ("user".equals(role)) {
                        Customer currentUser = new Customer(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("noHp"));

                        showUserPanel(currentUser);
                        lgn.dispose();

                    } else {
                        JOptionPane.showConfirmDialog(lgn, "Invalid role.", "Error",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showConfirmDialog(lgn, "Incorrect email or password.", "Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error!" + e.getMessage());
        }
    }

    public void showTxPanel(Customer currentUser) {
        Purchase tx = new Purchase(currentUser);
        tx.setVisible(true);
        tx.pack();
        tx.setLocationRelativeTo(null);
    }

    public void showUserPanel(Customer currentUser) {
        UserPanel upp = new UserPanel(currentUser);
        upp.setVisible(true);
        upp.pack();
        upp.setLocationRelativeTo(null);
        JOptionPane.showMessageDialog(new JFrame(), "Selamat datang " + currentUser.getName(), "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void showTrxUsr(Customer currentUser) {
        TransaksiUser trxu = new TransaksiUser(currentUser);
        trxu.setVisible(true);
        trxu.pack();
        trxu.setLocationRelativeTo(null);
    }

    public void showTrxAdm(Customer currentUser) {
        TransaksiAdmin trxa = new TransaksiAdmin(currentUser);
        trxa.setVisible(true);
        trxa.pack();
        trxa.setLocationRelativeTo(null);
    }

    public void showPrdct() {
        Products trxa = new Products();
        trxa.setVisible(true);
        trxa.pack();
        trxa.setLocationRelativeTo(null);
    }

    public void showAdminPanel() {
        AdminPanel ap = new AdminPanel();
        ap.setVisible(true);
        ap.pack();
        ap.setLocationRelativeTo(null);
    }

    public void showLoginPanel() {
        Login lgn = new Login();
        lgn.setVisible(true);
        lgn.pack();
        lgn.setLocationRelativeTo(null);
    }

    public void showRegistPanel() {
        SignUp rgst = new SignUp();
        rgst.setVisible(true);
        rgst.pack();
        rgst.setLocationRelativeTo(null);
    }

    // Method to check if an email already exists in the database
    private boolean isEmailExists(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Return true if the email already exists in the database
            }
        } catch (SQLException e) {
            System.out.println("Error checking email existence: " + e.getMessage());
            return false; // Return false in case of an exception or error
        }
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        // Use a simple regex for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}