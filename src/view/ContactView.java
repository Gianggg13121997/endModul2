package view;
import manage.ContactManager;
import input.Input;

import java.sql.SQLOutput;

public class ContactView {
    private ContactManager contactManager = new ContactManager();

    public void showMenu() {
        while (true) {
            System.out.println("=== CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ ===");
            System.out.println("Chọn chức năng theo số (để tiếp tục ) :");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm danh bạ");
            System.out.println("3. Cập nhật danh bạ");
            System.out.println("4. Xóa danh bạ");
            System.out.println("5. Tìm kiếm danh bạ");
            System.out.println("6. Đọc từ file");
            System.out.println("7. Lưu vào file");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    contactManager.displayContacts();
                    break;
                case 2:
                    contactManager.addContact();
                    break;
                case 3:
                    contactManager.updateContact();
                    break;
                case 4:
                    contactManager.deleteContact();
                    break;
                case 5:
                    contactManager.searchContacts();
                    break;
                case 6:
                    contactManager.readFromFile();
                    break;
                case 7:
                    contactManager.writeToFile();
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng ứng dụng. Tạm biệt!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }
}
