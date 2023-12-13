/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import martmain.Products;

/**
 *
 * @author achma
 */
public class ProductController {
    private Products prdkf;
    private Product prdkm;
    private Connection connection;
//    private User currentUser;
    
    
    public ProductController(Products prdkf) {
        this.prdkf = prdkf;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.connection = koneksiDB.getConn();
//        setCurrentUser(currentUser);
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
    
    public void edit() {
    prdkm = new Product();
    prdkm.setNama(prdkf.getNama().getText());
    prdkm.setStok(Integer.parseInt(prdkf.getStok().getText()));
    prdkm.setHarga(Integer.parseInt(prdkf.getHarga().getText()));
    prdkm.setDetail(prdkf.getDetail().getText());

    String sql = "UPDATE product SET stok=?, harga=?, detail=? WHERE nama=?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, prdkm.getStok());
        statement.setInt(2, prdkm.getHarga());
        statement.setString(3, prdkm.getDetail());
        statement.setString(4, prdkm.getNama());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(prdkf, "Produk berhasil di edit.", "Info", JOptionPane.INFORMATION_MESSAGE);
            tampilkan_data();
        } else {
            JOptionPane.showMessageDialog(prdkf, "Nama tidak dapat di edit", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(prdkf, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    
    public void deleteProduct() {
        prdkm = new Product();
        prdkm.setNama(prdkf.getNama().getText());
        
    try {
        String query = "DELETE FROM product WHERE nama = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, prdkm.getNama());

        int deletedRows = statement.executeUpdate();

        if (deletedRows > 0) {
            JOptionPane.showMessageDialog(prdkf, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(prdkf, "Data tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
        }

        statement.close();

        // Refresh data di tabel setelah menghapus
        tampilkan_data();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(prdkf, "Error saat menghapus data dari database", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    public void tampilkan_data() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No.");
    model.addColumn("Name");
    model.addColumn("Stok");
    model.addColumn("Price");
    model.addColumn("Detail");
    
    try {
        int no = 1;
        String sql = "SELECT * FROM product";
        java.sql.Statement stm = connection.createStatement();
        java.sql.ResultSet res = stm.executeQuery(sql);
        
        while (res.next()) {
            // Sesuaikan kolom yang sesuai dengan tabel database Anda
            model.addRow(new Object[]{
                no++,
                res.getString("nama"),
                res.getInt("stok"),
                res.getInt("harga"),
                res.getString("detail")
            });
        }
        prdkf.getTable().setModel(model);
    } catch (SQLException e) {
        System.out.println("Error : " + e.getMessage());
    }
}

//    private void setCurrentUser(User currentUser) {
//        this.currentUser = currentUser;
//    }


    
    
}
