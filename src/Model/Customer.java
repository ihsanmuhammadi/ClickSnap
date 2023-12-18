/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author achma
 */
// Customer.java (kelas anak)
public class Customer extends User {
    private String role;
    private final int id;
    private String alamat;

    // Konstruktor
    public Customer(int id, String name, String email, String password, String noHp) {
        super(name, email, password, noHp);
        this.role = "customer";
        this.id = id;
    }

    // Getter dan setter untuk atribut role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Metode lain yang spesifik untuk Customer (jika ada)
    // ...

    public int getId() {
        return id;
    }
}

