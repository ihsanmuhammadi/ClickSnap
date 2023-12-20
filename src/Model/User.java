/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author achma
 */
public class User {
    protected int id;
    protected String name, email, password, noHp;
    
     public User(int id, String name, String email, String password, String noHp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.noHp = noHp;
    }
    
    public User(String currentUser) {
            this.name = currentUser;
        }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNoHp() {
        return noHp;
    }
    
//    public abstract String getRole(String role);
}
