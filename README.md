<<<<<<< HEAD
# HDSGTest

# 📸 Hệ Thống Quản Lý Người Dùng – Spring Boot + OpenCV

🚀 Các chức năng:
Đăng ký

Đăng nhập (JWT)

Xem thông tin cá nhân (gồm ảnh avatar)

Đổi mật khẩu có kiểm tra ảnh xác thực ≥ 85%

⚙️ Công nghệ sử dụng
=======
# 📸 Hệ Thống Quản Lý Người Dùng – Spring Boot + OpenCV

# 🚀 Các chức năng chính:

   - Đăng ký
   - Đăng nhập (JWT)
   - Xem thông tin cá nhân (gồm ảnh avatar)
   - Đổi mật khẩu có kiểm tra ảnh xác thực ≥ 85%

# ⚙️ Công nghệ sử dụng
>>>>>>> 937a2e00a4cfcbb37f53441c52d3a72f601bca23

| Thành phần        | Công nghệ                 |
| ----------------- | ------------------------- |
| API Backend       | Java Spring Boot          |
| Cơ sở dữ liệu     | PostgreSQL                |
| Bảo mật           | Spring Security + JWT     |
| So khớp ảnh       | OpenCV                    |
| Công cụ build     | Maven                     |


<<<<<<< HEAD
🛠️ Cài đặt
=======
# 🛠️ Cài đặt
>>>>>>> 937a2e00a4cfcbb37f53441c52d3a72f601bca23

### 1. Clone dự án
git clone https://github.com/nhduy01/HDSGTest.git

### 2. Tải và cấu hình OpenCV

🔹 **Nếu bạn dùng Windows**:

<<<<<<< HEAD
1. Tải bản OpenCV tại:  
   👉 https://opencv.org/releases

2. Giải nén (ví dụ: `C:\opencv`)

3. Cấu hình biến môi trường hệ thống:  
   Thêm `C:\opencv\build\java\x64` (hoặc `x86` tùy hệ điều hành) vào biến `PATH`.

### 3. Chạy script PostpreSql nằm trong Folder HDSG

### 4. 
=======
   1. Tải bản OpenCV tại:  
   👉 https://opencv.org/releases

   2. Giải nén (ví dụ: `C:\opencv`)

   3. Cấu hình biến môi trường hệ thống:  
   Thêm `C:\opencv\build\java\x64` (hoặc `x86` tùy hệ điều hành) vào biến `PATH`.

### 3. Chạy script PostgreSQL
   1. Mở công cụ quản lý cơ sở dữ liệu (pgAdmin hoặc terminal).

   2. Kết nối đến PostgreSQL và chạy file SQL trong thư mục HDSG để:

      - Tạo database.

      - Tạo bảng users.

      - Thêm dữ liệu mẫu.

### 4. Chạy Ứng dụng

### 5. Test API với Postman
   1. Mở Postman.

   2. Import file HDSGTest.postman_collection.json trong thư mục HDSG.

   3. Gửi request để kiểm tra các chức năng: đăng ký, đăng nhập, xem thông tin cá nhân, đổi mật khẩu...
      
⚠️ **Lưu ý:** Sau khi đăng nhập, vui lòng sao chép token và sử dụng trong các yêu cầu tiếp theo để đảm bảo các chức năng khác hoạt động chính xác.

### 6. Tài liệu mô hình hệ thống

📁 **Thư mục `HDSG` chứa:**
- **ERD (Entity Relationship Diagram)**: mô tả cấu trúc bảng trong cơ sở dữ liệu.
- **Sequence Diagram**: minh họa luồng xử lý đăng nhập, đổi mật khẩu, và các chức năng chính.

👉 Vui lòng mở các file ảnh để tham khảo chi tiết mô hình.
>>>>>>> 937a2e00a4cfcbb37f53441c52d3a72f601bca23
