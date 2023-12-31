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
    
    // Konstruktor
    public Customer(int id, String name, String email, String password, String noHp) {
        super(id, name, email, password, noHp);
    }
    
    public Customer(String name) {
        super(name);
        this.name = name;
    }
}

