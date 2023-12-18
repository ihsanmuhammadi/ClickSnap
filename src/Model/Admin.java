/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author achma
 */
// Admin.java (kelas anak)
public class Admin extends User {
    private String role;
    private final int id;

    // Konstruktor
    public Admin(int id, String name, String email, String password, String noHp) {
        super(name, email, password, noHp);
        this.role = "admin";
        this.id = id;
    }

    // Getter dan setter untuk atribut role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Metode lain yang spesifik untuk Admin (jika ada)
    // ...
}

