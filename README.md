#EduWork - Space

## Lời nói đầu:
Dự án WorkSpace xây dựng hệ thống cung cấp hỗ trợ quản lý công việc như quản lý lịch làm việc, quản lý sinh viên, điểm danh.
## Liên hệ với tôi: 
Gmail: hoangvuvan677@gmail.com
## Giới thiệu
Hệ thống EduWorkSpace là một hệ thống dạng quản lý cá nhân. Nó được triển khai dựa trên các công nghệ như:
  - Backend: SpringBoot, Spring DataJpa, Spring cloud gateway, Jpa, JWT...
  - Fontend: Html, Css, JavaScipt, React
Các tính năng hỗ trợ;
  - Xem lịch làm việc theo ngày, tuần và tháng
  - Tạo qr điểm danh.
  - Hỗ trợ quản lý sinh viên như thêm sửa xóa

## TechStack
 - SpringBoot
 - Spring Cloud Gateway
 - Spring Security
 - Spring Data Jpa
 - Eureka Server
 - JWT
 - Docker
 - PostgreSQL

### Vai trò các công nghệ này trong hệ thống
  - Eureka Server(Discovery Server):
    + Các microservice (Client Services) đăng ký với Eureka Discovery Server.
    + Eureka Server duy trì một danh sách các dịch vụ đang chạy và thông tin về cách liên lạc với chúng (như địa chỉ IP, port, v.v.).
  - Spring Cloud Gateway(Service Discovery):
    + Spring Cloud Gateway, hoạt động như một API Gateway, cũng đăng ký với Eureka Server.
    + Khi cần chuyển tiếp yêu cầu tới một microservice, Gateway sẽ truy vấn Eureka Server để lấy thông tin vị trí của dịch vụ đó.

## WorkFlow
![image](https://github.com/hoangvuvan0611/Edu-Workspace/assets/113603055/7da2135a-8459-4d0a-ae77-b05eb5db234d)


### Giải thích luồng làm việc của hệ thống
  - Khi có request gửi tới Spring Cloud Gateway, Gateway kiểm tra định tuyến và xác định microservice nào sẽ xử lý yêu cầu dựa trên cấu hình định tuyến.
  - Gateway truy vấn Eureka Server để tìm vị trí hiện tại của microservice đích.
  - Sau khi xác định được endpoint, Gateway chuyển tiếp yêu cầu tới microservice đích(Request Forwarding).
  - Các service được gọi sẽ xử lý yêu cầu và trả về response tới gateway.
  - Gateway sẽ chuyển tiếp phản hồi tới client.


![image](https://github.com/hoangvuvan0611/Edu-Workspace/assets/113603055/bd7db057-44a1-47de-83f7-1b24093a600c)



