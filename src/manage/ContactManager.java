package manage;

import input.Input;
import model.Contact;
import saveData.ReadAndWrite;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactManager {
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ReadAndWrite readAndWrite = new ReadAndWrite();

    public ContactManager() {
        loadContacts();
    }

    private void loadContacts() {
        contacts = readAndWrite.readFile();
    }

    private void saveContacts() {
        readAndWrite.writeFile(contacts);
    }

    public void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Danh bạ rỗng.");
            return;
        }
        int count = 0;
        for (Contact contact : contacts) {
            if (count > 5 && count % 5 == 0) {
                System.out.println("Nhấn Enter để tiếp tục...");
                Input.inputString();
            }
            System.out.println(contact);
            count++;
        }
    }

    public void addContact() {
        try {
            System.out.print("Nhập số điện thoại: ");
            String phoneNumber = Input.inputString();
            if (!isValidPhoneNumber(phoneNumber)) {
                throw new IllegalArgumentException("Số điện thoại không hợp lệ. vd : +84987654321");
            }
            System.out.print("Nhập nhóm: ");
            String group = Input.inputString();
            if (group.trim().isEmpty()) {
                throw new IllegalArgumentException("Nhóm không được để trống.");
            }
            System.out.print("Nhập họ tên: ");
            String name = Input.inputString();
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Họ tên không được để trống.");
            }
            System.out.print("Nhập giới tính: ");
            String gender = Input.inputString();
            if (gender.trim().isEmpty()) {
                throw new IllegalArgumentException("Giới tính không được để trống.");
            }
            System.out.print("Nhập địa chỉ: ");
            String address = Input.inputString();
            if (address.trim().isEmpty()) {
                throw new IllegalArgumentException("Địa chỉ không được để trống.");
            }
            System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
            Date birthDate;
            try {
                birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(Input.inputString());
            } catch (ParseException e) {
                throw new IllegalArgumentException("Ngày sinh không hợp lệ. Vd định dạng : yyyy-MM-dd.");
            }
            System.out.print("Nhập email: ");
            String email = Input.inputString();
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Email không hợp lệ. ví dụ: xxx@gmail.com.");
            }
            Contact contact = new Contact(phoneNumber, group, name, gender, address, birthDate, email);
            contacts.add(contact);
            System.out.println("Danh bạ đã được thêm.");
            saveContacts();
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi : " + e.getMessage());
        }
    }

    public void searchContacts() {
        System.out.print("Nhập số điện thoại hoặc họ tên để tìm kiếm: ");
        String query = Input.inputString();
        ArrayList<Contact> results = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().contains(query) || contact.getName().contains(query)) {
                results.add(contact);
            }
        }
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy kết quả.");
        } else {
            for (Contact result : results) {
                System.out.println(result);
            }
        }
    }

    public void updateContact() {
        System.out.print("Nhập số điện thoại của danh bạ cần sửa: ");
        String phoneNumber = Input.inputString();
        Contact contactToUpdate = findContactByPhoneNumber(phoneNumber);
        if (contactToUpdate == null) {
            System.out.println("Không tìm thấy danh bạ với số điện thoại này.");
            return;
        }

        System.out.print("Nhập nhóm mới (hiện tại: " + contactToUpdate.getGroup() + "): ");
        String group = Input.inputString();
        System.out.print("Nhập họ tên mới (hiện tại: " + contactToUpdate.getName() + "): ");
        String name = Input.inputString();
        System.out.print("Nhập giới tính mới (hiện tại: " + contactToUpdate.getGender() + "): ");
        String gender = Input.inputString();
        System.out.print("Nhập địa chỉ mới (hiện tại: " + contactToUpdate.getAddress() + "): ");
        String address = Input.inputString();
        System.out.print("Nhập ngày sinh mới (yyyy-MM-dd) (hiện tại: " + new SimpleDateFormat("yyyy-MM-dd").format(contactToUpdate.getBirthDate()) + "): ");
        Date birthDate;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(Input.inputString());
        } catch (ParseException e) {
            System.out.println("Ngày sinh không hợp lệ.");
            return;
        }
        System.out.print("Nhập email mới (hiện tại: " + contactToUpdate.getEmail() + "): ");
        String email = Input.inputString();
        if (!isValidEmail(email)) {
            System.out.println("Email không hợp lệ. Phải có định dạng @gmail.com.");
            return;
        }

        contactToUpdate.setGroup(group);
        contactToUpdate.setName(name);
        contactToUpdate.setGender(gender);
        contactToUpdate.setAddress(address);
        contactToUpdate.setBirthDate(birthDate);
        contactToUpdate.setEmail(email);
        System.out.println("Danh bạ đã được cập nhật.");
        saveContacts();
    }

    public void readFromFile() {
        System.out.print("Bạn có chắc chắn muốn cập nhật danh bạ từ file? (Y/N): ");
        String confirmation = Input.inputString();
        if ("Y".equalsIgnoreCase(confirmation)) {
            loadContacts();
            System.out.println("Danh bạ đã được cập nhật từ file.");
        } else {
            System.out.println("Hủy bỏ việc cập nhật từ file.");
        }
    }

    public void writeToFile() {
        System.out.print("Bạn có chắc chắn muốn lưu danh bạ vào file? (Y/N): ");
        String confirmation = Input.inputString();
        if ("Y".equalsIgnoreCase(confirmation)) {
            saveContacts();
            System.out.println("Danh bạ đã được lưu vào file.");
        } else {
            System.out.println("Hủy bỏ việc lưu vào file.");
        }
    }

    public void deleteContact() {
        System.out.print("Nhập số điện thoại của danh bạ cần xóa: ");
        String phoneNumber = Input.inputString();
        Contact contactToDelete = findContactByPhoneNumber(phoneNumber);
        if (contactToDelete == null) {
            System.out.println("Không tìm thấy danh bạ với số điện thoại này.");
            return;
        }
        System.out.print("Bạn có chắc chắn muốn xóa không? (Y/N): ");
        String confirmation = Input.inputString();
        if ("Y".equalsIgnoreCase(confirmation)) {
            contacts.remove(contactToDelete);
            System.out.println("Danh bạ đã được xóa.");
            saveContacts();
        } else {
            System.out.println("Hủy xóa.");
        }
    }
    private Contact findContactByPhoneNumber(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return contact;
            }
        }
        return null;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+84\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    } //regex sdt +84
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    } //regex email:...@gmail.com

}
