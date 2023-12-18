/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Admin;
import Model.Customer;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import martmain.AdminPanel;
import martmain.Login;
import martmain.SignUp;
import martmain.Transaksi;
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
    private User currentUser;
    private Transaksi trx;
    
    protected final ArrayList<User> userList = new ArrayList<>();

    public UserController(SignUp regist, Login lgn, Transaksi trx) {
        this.regist = regist;
        this.lgn = lgn;
        this.trx = trx;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.conn = koneksiDB.getConn();
//        setCurrentUser(currentUser);
    }
    
    public void addUser(User user) {
        userList.add(user);
    }

    public void regist() {
        usrm = new User(
            regist.getNama().getText(),
            regist.getEmail().getText(),
            regist.getPass().getText(),
            regist.getNo().getText()
        );
        
        // Validate email format
        if (!isValidEmail(usrm.getEmail())) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid email format", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

           // Check if the email already exists in the database
        if (isEmailExists(usrm.getEmail())) {
            JOptionPane.showMessageDialog(new JFrame(), "Email already exists. Please choose a different email.", "Error",
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
    usrm = new User(
        null,
        lgn.getEmail().getText(),
        lgn.getPass().getText(),
        null
    );

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
                    currentUser = new Admin(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("noHp")
                    );
                    showAdminPanel();
                } else if ("user".equals(role)) {
                    JOptionPane.showConfirmDialog(lgn, "Login successful.", "Info",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    currentUser = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("noHp")
                    );
                    
                    // Di dalam metode login() setelah pengisian currentUser
                    JOptionPane.showMessageDialog(new JFrame(), "Current User ID: " + currentUser.getId() + "\nCurrent User Name: " + currentUser.getName(), "Info", JOptionPane.INFORMATION_MESSAGE);

                    showUserPanel();
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

    
    public void showUserPanel() {
        UserPanel up = new UserPanel();
        up.setVisible(true);
        up.pack();
        up.setLocationRelativeTo(null);
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

    public User getCurrentUser() {
        return currentUser;
    }
    
//    public int getCurrentUserId() {
//        // Contoh implementasi: mengembalikan ID pengguna yang diambil dari objek User saat ini
//        if (currentUser != null) {
//            return currentUser.getId(); // Ubah sesuai dengan implementasi objek User Anda
//        } else {
//            // Handle jika pengguna tidak ada atau tidak masuk
//            JOptionPane.showMessageDialog(null, "Pengguna belum masuk.", "Error", JOptionPane.ERROR_MESSAGE);
//            return -1; // Nilai -1 mungkin digunakan untuk menunjukkan bahwa tidak ada pengguna yang masuk
//        }
//    }
    
}