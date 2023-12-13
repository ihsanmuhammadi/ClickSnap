/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import martmain.Products;

/**
 *
 * @author achma
 */
public class ProductController {
    private Products prdkf;
    private Product prdkm;
    private Connection connection;
    
    
    public ProductController(Products prdkf) {
        this.prdkf = prdkf;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.connection = koneksiDB.getConn();
    }
    
    public void simpan() {
        prdkm = new Product();
        prdkm.setNama(prdkf.getNama().getText());
        prdkm.setStok(Integer.parseInt(prdkf.getStok().getText()));
        prdkm.setHarga(Integer.parseInt(prdkf.getHarga().getText()));
        prdkm.setDetail(prdkf.getDetail().getText());

        String sql = "INSERT INTO product (nama, stok, harga, detail) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, prdkm.getNama());
            statement.setInt(2, prdkm.getStok());
            statement.setInt(3, prdkm.getHarga());
            statement.setString(4, prdkm.getDetail());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showConfirmDialog(prdkf, "Produk berhasil ditambahkan.", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showConfirmDialog(prdkf, "Gagal menambahkan produk.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(prdkf, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
}
