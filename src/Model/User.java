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
    private int Id;

    public int getId() {
        return Id;
    }
    
    private String name, email, password, noHp;
    
     public User(String name, String email, String password, String noHp) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.noHp = noHp;
    }
    
//    public User(String current_user) {
//        this.name = current_user;
//    }
    
    public User(int current_user) {
        this.Id = current_user;
    }

//    public User(int aInt, String string, String string0, String string1, String string2, String admin) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setNoHp(String noHp) {
//        this.noHp = noHp;
//    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNoHp() {
        return noHp;
    }
}
