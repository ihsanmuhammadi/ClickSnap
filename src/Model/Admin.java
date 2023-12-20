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

    // Konstruktor
    public Admin(int id, String name, String email, String password, String noHp) {
        super(id, name, email, password, noHp);
    }
    
    public Admin(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public String getRole() {
        return "admin";
    }
    
}

