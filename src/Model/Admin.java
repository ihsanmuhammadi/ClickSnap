/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author achma
 */
public class Admin extends User {

    // Konstruktor
    public Admin(int id, String name, String email, String password, String noHp) {
        super(id, name, email, password, noHp);
    }
    
    public Admin(String name) {
        super(name);
        this.name = name;
    }
}

