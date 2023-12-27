/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author achma
 */
public class Transactions {
    private int id;
    private int userId;
    private int productId;
    private Date date;
    private int jumlahBeli;
    private int totalHarga;
    private Customer currentUser;
    private Product prdc;

    
    // Getter dan setter
    
    public Transactions() {
        
    }

    public Transactions(Customer currentUser, int productId, int jumlahBeli, int totalHarga) {
//        this.userId = userId;
        this.currentUser = currentUser;
//        this.prdc = prdc;
        this.productId = productId;
        this.jumlahBeli = jumlahBeli;
        this.totalHarga = totalHarga;
    }

    public Product getPrdc() {
        return prdc;
    }

    public void setPrdc(Product prdc) {
        this.prdc = prdc;
    }

    public void setCurrentUser(Customer currentUser) {
        this.currentUser = currentUser;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Customer currentUser) {
        this.currentUser = currentUser;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Customer getUser() {
        return currentUser;
    }
    
}
