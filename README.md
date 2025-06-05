
# HDSGTest

# 📸 Hệ Thống Quản Lý Người Dùng – Spring Boot + OpenCV

# 🚀 Các chức năng chính:

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
### 3. Cấu hình cơ sở dữ liệu

Trước khi chạy ứng dụng, bạn cần cấu hình kết nối đến cơ sở dữ liệu PostgreSQL:

1. Mở file `src/main/resources/application.properties`.
2. Chỉnh sửa các thông số sau cho phù hợp với hệ thống của bạn:
<pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/database_name  
spring.datasource.username=your_username  
spring.datasource.password=your_password
</pre>

> 📝 **Ghi chú:**
> - Hãy thay `database_name`, `your_username` và `your_password` bằng thông tin thực tế trên máy bạn.
> - Đảm bảo database đã được tạo trong PostgreSQL trước khi chạy ứng dụng (chỉ cần tạo database rỗng).
> - Ứng dụng sử dụng cấu hình `spring.jpa.hibernate.ddl-auto=update` nên sẽ **tự động tạo bảng** khi chạy lần đầu.
> - Ngoài ra, ứng dụng cũng sẽ **tự chèn một số dữ liệu mẫu** để thuận tiện cho việc kiểm thử.
### 4. Chạy Ứng dụng

### 5. Test API với Postman

1. Mở Postman.

2. Import file `HDSGTest.postman_collection.json` trong thư mục `HDSG`.

3. Gửi request để kiểm tra các chức năng.

⚠️ **Lưu ý:**
- Sau khi đăng nhập, vui lòng sao chép access token và sử dụng trong các yêu cầu tiếp theo để đảm bảo các chức năng khác hoạt động chính xác (thêm vào phần Header: `Authorization: Bearer <token>`).
- Hàm cập nhật ngưỡng so khớp khuôn mặt (`/api/system-setting/face-match`) yêu cầu tài khoản có quyền `ADMIN`. Vui lòng đăng nhập bằng tài khoản admin để sử dụng API này.

### 6. Tài liệu mô hình hệ thống

📁 **Thư mục `HDSG` chứa:**
- **ERD (Entity Relationship Diagram)**: mô tả cấu trúc bảng trong cơ sở dữ liệu.
- **Sequence Diagram**: minh họa luồng xử lý đăng nhập, đổi mật khẩu, và các chức năng chính.

👉 Vui lòng mở các file trong thư mục `HDSG` để tham khảo chi tiết mô hình hệ thống.

