package lab03; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/shoestore_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<ShoeStoreApp.Product> getAllProducts(ShoeStoreApp appInstance) {
        List<ShoeStoreApp.Product> productList = new ArrayList<>();
        String query = "SELECT name, price, brand, image_path FROM products";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String brand = rs.getString("brand");
                String imagePath = rs.getString("image_path");
                
                ShoeStoreApp.Product product = appInstance.new Product(name, price, brand, imagePath);
                productList.add(product);
            }
        } catch (Exception e) {
            System.err.println("Lỗi truy xuất CSDL: " + e.getMessage());
        }
        return productList;
    }
}