/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author achma
 */
public class Product {
    private int id;
    private String nama;
    private int stok, harga;
    private String detail;
    
    public Product(String nama, int stok, int harga, String detail) {
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
        this.detail = detail;
    }

    public String getNama() {
        return nama;
    }
    
    public int getStok() {
        return stok;
    }

    public int getHarga() {
        return harga;
    }

    public String getDetail() {
        return detail;
    }
    
    @Override
    public String toString() {
        // Override toString untuk menampilkan nama saat ditampilkan di JComboBox
        return nama;
    }

    public int getId() {
        return id;
    }

}
