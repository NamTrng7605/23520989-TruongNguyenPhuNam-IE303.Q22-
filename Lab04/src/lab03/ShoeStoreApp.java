package lab03;


import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ShoeStoreApp extends Application {

    // Component 
    private ImageView mainImageView;
    private Label mainNameLabel;
    private Label mainPriceLabel;
    private VBox leftPanel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts(this);

        if (products.isEmpty()) {
            System.out.println("Không thể tải sản phẩm từ Cơ sở dữ liệu!");
            return; 
        }

        leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setPrefWidth(350);
        leftPanel.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");

        mainImageView = new ImageView();
        mainImageView.setFitWidth(300);
        mainImageView.setFitHeight(300);
        mainImageView.setPreserveRatio(true);

        mainNameLabel = new Label();
        mainNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        mainPriceLabel = new Label();
        mainPriceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label mainBrandLabel = new Label("Adidas");
        mainBrandLabel.setTextFill(Color.GRAY);
        
        Label mainDescLabel = new Label("This product is excluded from all\npromotional discounts and offers.");
        mainDescLabel.setTextFill(Color.GRAY);
        mainDescLabel.setWrapText(true);

        leftPanel.getChildren().addAll(mainImageView, mainNameLabel, mainPriceLabel, mainBrandLabel, mainDescLabel);

        // Hiển thị mặc định sản phẩm đầu tiên
        updateMainProduct(products.get(0));

        FlowPane rightGrid = new FlowPane();
        rightGrid.setPadding(new Insets(20));
        rightGrid.setHgap(15);
        rightGrid.setVgap(15);
        rightGrid.setStyle("-fx-background-color: white;");

        // Tạo Card 
        for (Product product : products) {
            VBox card = createProductCard(product);
            rightGrid.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(rightGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: white;");

        HBox root = new HBox(leftPanel, scrollPane);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Shoe Store UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // Tạo card cho mỗi sản phẩm trong grid bên phải
    private VBox createProductCard(Product product) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setPrefWidth(200);
        card.setPrefHeight(250);
        card.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #e0e0e0;");
        card.setCursor(Cursor.HAND);

        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Label descLabel = new Label("This product is excluded...");
        descLabel.setTextFill(Color.GRAY);
        descLabel.setFont(Font.font("Arial", 10));

        ImageView imageView = new ImageView(new Image(product.getImagePath()));
        imageView.setFitWidth(150);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);
        
        HBox imageBox = new HBox(imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setMinHeight(130);

        HBox bottomBox = new HBox();
        Label brandLabel = new Label(product.getBrand());
        brandLabel.setTextFill(Color.GRAY);
        brandLabel.setFont(Font.font("Arial", 12));
        
        Label priceLabel = new Label(String.format("$%.2f", product.getPrice()));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomBox.getChildren().addAll(brandLabel, spacer, priceLabel);

        card.getChildren().addAll(nameLabel, descLabel, imageBox, bottomBox);

        card.setOnMouseClicked(event -> {
            updateMainProduct(product);
        });

        return card;
    }

    // Cập nhật thông tin sản phẩm chính khi click vào card

    private void updateMainProduct(Product product) {
        mainNameLabel.setText(product.getName());
        mainPriceLabel.setText(String.format("$%.2f", product.getPrice()));
        
        try {
            mainImageView.setImage(new Image(product.getImagePath()));
        } catch (Exception e) {
            System.out.println("Không tìm thấy ảnh: " + product.getImagePath());
        }

        FadeTransition fade = new FadeTransition(Duration.millis(500), leftPanel);
        fade.setFromValue(0.2);
        fade.setToValue(1.0);
        fade.play();
    }


    class Product {
        private String name;
        private double price;
        private String brand;
        private String imagePath;

        public Product(String name, double price, String brand, String imagePath) {
            this.name = name;
            this.price = price;
            this.brand = brand;
            this.imagePath = imagePath;
        }

        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getBrand() { return brand; }
        public String getImagePath() { return imagePath; }
    }
}