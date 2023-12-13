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
//    private int id;
    private String nama;
    private int stok, harga;
    private String detail;
    
    public Product(){
        
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setDetail(String detail) {
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

}
