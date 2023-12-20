/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author achma
 */
abstract class User {
    private int id;
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
    

//    public User(int aInt, String string, String string0, String string1, String string2, String admin) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    public String getName() {
        return name;
    }
    
    public int getid() {
        return id;
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
    
    public abstract String getRole();
}
