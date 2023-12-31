/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DB.KoneksiDB;
import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public ProductController(Products prdkf) {
        this.prdkf = prdkf;
        KoneksiDB koneksiDB = new KoneksiDB();
        koneksiDB.bukaKoneksi();
        this.connection = koneksiDB.getConn();
    }

    public void reset() {
        prdkf.getNama().setText("");
        prdkf.getHarga().setText("");
        prdkf.getDetail().setText("");
    }

    public void simpan() {
        // Menggunakan konstruktor dengan parameter
        prdkm = new Product(
                prdkf.getNama().getText(),
                "Tersedia".equals(prdkf.getStok().getSelectedItem()) ? 1 : 0,
                Integer.parseInt(prdkf.getHarga().getText()),
                prdkf.getDetail().getText());

        // Pemeriksaan keberadaan nama produk sebelum menyimpan
        if (cekNamaProduk(prdkm.getNama())) {
            JOptionPane.showMessageDialog(prdkf, "Nama produk sudah ada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO product (nama, stok, harga, detail) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Menggunakan getter dari objek prdkm
            statement.setString(1, prdkm.getNama());
            statement.setInt(2, prdkm.getStok());
            statement.setInt(3, prdkm.getHarga());
            statement.setString(4, prdkm.getDetail());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showConfirmDialog(prdkf, "Produk berhasil ditambahkan.", "Info", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
                tampilkan_data();
            } else {
                JOptionPane.showConfirmDialog(prdkf, "Gagal menambahkan produk.", "Error", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(prdkf, "Terjadi kesalahan: " + e.getMessage(), "Error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void edit() {
        // Menggunakan konstruktor dengan parameter
        prdkm = new Product(
                prdkf.getNama().getText(),
                "Tersedia".equals(prdkf.getStok().getSelectedItem()) ? 1 : 0,
                Integer.parseInt(prdkf.getHarga().getText()),
                prdkf.getDetail().getText());

        String sql = "UPDATE product SET stok=?, harga=?, detail=? WHERE nama=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Menggunakan getter dari objek prdkm
            statement.setInt(1, prdkm.getStok());
            statement.setInt(2, prdkm.getHarga());
            statement.setString(3, prdkm.getDetail());
            statement.setString(4, prdkm.getNama());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(prdkf, "Produk berhasil di edit.", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                tampilkan_data();
            } else {
                JOptionPane.showMessageDialog(prdkf, "Nama tidak dapat di edit", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(prdkf, "Terjadi kesalahan: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void delete() {
        // Menggunakan konstruktor dengan parameter
        prdkm = new Product(prdkf.getNama().getText(), 0, 0, null);

        try {
            String query = "DELETE FROM product WHERE nama = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, prdkm.getNama());

                int deletedRows = statement.executeUpdate();

                if (deletedRows > 0) {
                    JOptionPane.showMessageDialog(prdkf, "Produk berhasil dihapus", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(prdkf, "Produk tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Refresh data di tabel setelah menghapus
                tampilkan_data();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(prdkf, "Error saat menghapus data dari database", "Error",
                    JOptionPane.ERROR_MESSAGE);
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
                String stokString;
                int stok = res.getInt("stok");

                // Konversi nilai stok ke string "Kosong" atau "Tersedia"
                if (stok == 0) {
                    stokString = "Kosong";
                } else if (stok == 1) {
                    stokString = "Tersedia";
                } else {
                    stokString = "Undefined"; // Sesuaikan dengan kebutuhan Anda jika ada nilai stok lainnya
                }

                model.addRow(new Object[] {
                        no++,
                        res.getString("nama"),
                        stokString,
                        res.getInt("harga"),
                        res.getString("detail")
                });
            }
            prdkf.getTable().setModel(model);
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    // Metode untuk memeriksa keberadaan nama produk di database
    private boolean cekNamaProduk(String namaProduk) {
        String sql = "SELECT COUNT(*) FROM product WHERE nama = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, namaProduk);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}