

> Bài thực hành 04

Xây dựng CSDL lưu thông tin sản phẩm bằng MySQL và hiển thị lại giao diện bài lab3 bằng Java Swing + JDBC.


- **Ngôn ngữ**: Java (JDK 21)
- **Thư viện UI**: JavaFX SDK 21
- **Cơ sở dữ liệu**: MySQL
- **Kết nối CSDL**: MySQL Connector/J (JDBC)
- **Công cụ phát triển**: Visual Studio Code


### 1. Chuẩn bị môi trường
- Đảm bảo máy tính đã cài đặt JDK 21 và JavaFX SDK.
- Đã cài đặt hệ quản trị CSDL MySQL Server và MySQL Workbench.
- VS Code đã cài đặt gói *Extension Pack for Java*.

### 2. Thiết lập Cơ sở dữ liệu
1. Mở MySQL Workbench.
2. Mở và chạy file script `database.sql` (nằm trong thư mục gốc của dự án) để tự động tạo database `shoestore_db` và nạp dữ liệu mẫu.
3. Mở file `src/lab03/ProductDAO.java`, cập nhật lại hằng số `USER` và `PASSWORD` cho khớp với thông tin đăng nhập MySQL.

### 3. Cấu hình thư viện trên VS Code
- Thêm file thư viện `mysql-connector-j.jar` (nằm trong thư mục `lib/`) vào mục **Referenced Libraries** của bảng điều khiển 

### 4. Khởi chạy ứng dụng
Mở file `ShoeStoreApp.java`, và chọn **Run and Debug**. 
