# HDSGTest

# 📸 Hệ Thống Quản Lý Người Dùng – Spring Boot + OpenCV

# 🚀 Các chức năng:

   - Đăng ký
   - Đăng nhập (JWT)
   - Xem thông tin cá nhân (gồm ảnh avatar)
   - Đổi mật khẩu có kiểm tra ảnh xác thực ≥ 85%

# ⚙️ Công nghệ sử dụng

| Thành phần        | Công nghệ                 |
| ----------------- | ------------------------- |
| API Backend       | Java Spring Boot          |
| Cơ sở dữ liệu     | PostgreSQL                |
| Bảo mật           | Spring Security + JWT     |
| So khớp ảnh       | OpenCV                    |
| Công cụ build     | Maven                     |


# 🛠️ Cài đặt

### 1. Clone dự án
git clone https://github.com/nhduy01/HDSGTest.git

### 2. Tải và cấu hình OpenCV

🔹 **Nếu bạn dùng Windows**:

   1. Tải bản OpenCV tại:  
   👉 https://opencv.org/releases

   2. Giải nén (ví dụ: `C:\opencv`)

   3. Cấu hình biến môi trường hệ thống:  
   Thêm `C:\opencv\build\java\x64` (hoặc `x86` tùy hệ điều hành) vào biến `PATH`.

### 3. Chạy script PostgreSQL
Mở công cụ quản lý cơ sở dữ liệu (pgAdmin hoặc terminal).

Kết nối đến PostgreSQL và chạy file SQL trong thư mục HDSG để:

Tạo database.

Tạo bảng users.

Thêm dữ liệu mẫu.

### 4. Chạy Ứng dụng

### 5. Test API với Postman
   1. Mở Postman.

   2. Import file HDSGTest.postman_collection.json trong thư mục HDSG.

   3. Gửi request để kiểm tra các chức năng: đăng ký, đăng nhập, xem thông tin cá nhân, đổi mật khẩu...
      
⚠️ **Lưu ý:** Sau khi đăng nhập, vui lòng sao chép token và sử dụng trong các yêu cầu tiếp theo để đảm bảo các chức năng khác hoạt động chính xác.
