import java.io.*;
import java.util.Scanner;

class IDcheck extends OutputHandler {
    String userID, password;

    int ID() {
        try {
            Scanner sc = new Scanner(System.in);
            File f1 = new File("ID.txt");
            BufferedReader br = new BufferedReader(new FileReader(f1));
            System.out.println("Enter USER ID and PASSWORD");
            String s;
            userID = sc.next();
            password = sc.next();
            int match = 2;
            while ((s = br.readLine()) != null) {
                String[] words = s.split(" ");
                for (int i = 0; i < 2; i++) {
                    if (words[0].equals(userID)) {
                        if (words[1].equals(password)) {
                            match = 0;
                        } else {
                            match = 1;
                        }
                    }

                }
            }
            if (match == 0)
                System.out.println("ID MATCH FOUND, Logging in ...");
            else if (match == 1)
                System.out.println("Either USER ID of PASSWORD is incorrect ");
            else if (match == 2)
                System.out.println("NOT FOUND");

            return (match);
        } catch (IOException e) {

            System.out.println(e);
        }
        return (2);
    }

    void RegisterUser() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter USER ID and PASSWORD");
        userID = sc.next();
        password = sc.next();
        writeToFile(userID, password);
        System.out.println("ACCOUNT CREATED SUCCESSFULLY ");
    }

    void writeToFile(String s) {
        try {

            File file = new File(userID + ".txt");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(s);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
