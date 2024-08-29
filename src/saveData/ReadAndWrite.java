package saveData;

import model.Contact;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadAndWrite {
    private File file = new File("data/Contact.csv");
    public void writeFile(ArrayList<Contact> contacts) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Contact contact : contacts) {
                String line = contact.getPhoneNumber()+"\n"
                        +contact.getGroup()+"\n"
                        +contact.getName()+"\n"
                        +contact.getAddress()+"\n"
                        +contact.getEmail()+"\n"
                        +contact.getGender()+"\n"
                        +  new SimpleDateFormat("yyyy-MM-dd").format(contact.getBirthDate());
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            } catch (IOException e) {
            System.out.println("Loi : " + e.getMessage());
            e.printStackTrace();

        }

    }
    public ArrayList<Contact> readFile() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line=bufferedReader.readLine())!=null){
                String[] data = line.split(",");
                if (data.length == 7) {

                    String phoneNumber = data[0];
                    String group = data[1];
                    String name = data[2];
                    String gender = data[3];
                    String address = data[4];
                    Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(data[5]);
                    String email = data[6];

                    Contact contact = new Contact(phoneNumber, group, name, gender, address, birthDate, email);
                    contacts.add(contact);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Loi : " + e.getMessage());
            e.printStackTrace();
        }
        return contacts;
    }
}

