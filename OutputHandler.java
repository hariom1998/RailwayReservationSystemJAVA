import java.io.*;

class OutputHandler {
    void writeToFile(String s1, String s2) {
        try {

            File f1 = new File("ID.txt");
            FileWriter fw = new FileWriter(f1, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(s1 + " " + s2);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
