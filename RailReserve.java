import java.io.*;
import java.util.*;

public class RailReserve extends IDcheck {
    Date d1 = new Date();
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    int pno[] = new int[275];
    String name[] = new String[275];
    String phno[] = new String[275];
    int age[] = new int[275];
    double amount;
    int pcount = 0;
    int pnum = 1;
    int linecount = 0;
    int row = 0;
    int col = 0;
    String[][] TrainTable;
    int trainid;
    int trno;

    RailReserve() throws Exception {
        initialiseTable();
    }

    private void printTable(int i) {
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", TrainTable[i][0], TrainTable[i][1],
                TrainTable[i][2], TrainTable[i][3], TrainTable[i][4], TrainTable[i][5], TrainTable[i][6],
                TrainTable[i][7], TrainTable[i][8]);
        System.out.println();

    }

    private void initialiseTable() throws Exception {
        linecount = 0;
        FileInputStream fstream = new FileInputStream("TrainTable.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String s;
        int i = 0;
        int j = 0;
        while ((s = br.readLine()) != null) {
            linecount++;
        }
        fstream.getChannel().position(0);
        br = new BufferedReader(new InputStreamReader(fstream));
        col = 9;
        row = linecount;
        TrainTable = new String[row][col];

        while ((s = br.readLine()) != null) {
            String[] line = s.trim().split("\t");
            for (j = 0; j < 9; j++) {
                TrainTable[i][j] = line[j];

            }
            i++;
        }
    }

    public void doMenu() throws Exception {
        int cho = 0;
        do {
            System.out.println("\f");
            doHeading();
            System.out.println("1.Book Ticket");
            System.out.println("2.Cancel Ticket");
            System.out.println("3.Search Passenger");
            System.out.println("4.Reservation Chart");
            System.out.println("5.Display Unbooked Tickets");
            System.out.println("6.Display Full Train Table");
            System.out.println("7.View Transaction History");
            System.out.println("8.Exit");
            System.out.println("Please enter your choice");
            cho = Integer.parseInt(br.readLine());
            switch (cho) {
            case 1:
                doBook();
                break;
            case 2:
                doCancel();
                break;
            case 3:
                doSearch();
                break;
            case 4:
                doDispList();
                break;
            case 5:
                doDispUnbooked();
                break;
            case 6:
                doDispList();
                break;
            case 7:
                doHistory();
                break;
            case 8:
                doExit();
                System.exit(0);

            default:
                System.out.println("Invalid choice");
            }
        } while (cho != 8);
    }

    private void doHeading() throws Exception {
        System.out.println("#########################################################");
        System.out.println("***************Railway Reservation System****************");
        System.out.println("#########################################################");
    }

    private void showTrain(String src, String dest) {
        for (int i = 0; i < row; i++) {
            if (i == 0) {
                printTable(i);
            }
            if (TrainTable[i][2].equalsIgnoreCase(src) && TrainTable[i][3].equalsIgnoreCase(dest)) {
                printTable(i);
            }
        }
        System.out.println();
    }

    private void doBook() throws Exception {
        System.out.println("Please enter the SOURCE & DESTINATION");
        String src = br.readLine();
        String dest = br.readLine();
        showTrain(src, dest);
        System.out.println("Enter Train No.");
        trno = Integer.parseInt(br.readLine());
        trainid = trno;
        System.out.println("Please enter no. of tickets");
        int t = Integer.parseInt(br.readLine());
        int ticketAvailable = 0;
        if (Integer.parseInt(TrainTable[trno][7]) >= t) {
            ticketAvailable = 1;
        }
        if (ticketAvailable == 1) {
            for (int i = 0; i < t; i++) {
                pno[pcount] = pnum;
                System.out.println("Please enter your name");
                name[pcount] = br.readLine();
                System.out.println("Please enter your age");
                age[pcount] = Integer.parseInt(br.readLine());
                System.out.println("Please enter your phno");
                phno[pcount] = br.readLine();
                pcount++;
                pnum++;
                System.out.println("Ticket successfully booked");
                String booked = TrainTable[trno][6];
                String unbooked = TrainTable[trno][7];
                int b = Integer.parseInt(booked);
                int u = Integer.parseInt(unbooked);
                b = b + 1;
                u = u - 1;
                TrainTable[trno][6] = Integer.toString(b);
                TrainTable[trno][7] = Integer.toString(u);
            }
            amount = Integer.parseInt(TrainTable[trno][8]);
            printBill();
            displayBill();
            System.out.println("\nPlease Pay Rs. " + t * amount);
        }
        if (ticketAvailable == 0) {
            System.out.println("The TRAIN is fully booked .");
        }
    }

    private void doCancel() throws Exception {
        int t_pno[] = new int[275];
        String t_name[] = new String[275];
        String t_phno[] = new String[275];
        int t_age[] = new int[275];
        int t_cl[] = new int[275];
        int t_pcount = 0;
        int passengerFound = 0;
        System.out.println("Please enter your PNR no.");
        int p = Integer.parseInt(br.readLine());
        for (int i = 0; i < pcount; i++) {
            if (pno[i] != p) {
                t_pno[t_pcount] = pno[i];
                t_name[t_pcount] = name[i];
                t_phno[t_pcount] = phno[i];
                t_age[t_pcount] = age[i];
                t_pcount++;
            } else {
                passengerFound = 1;
                System.out.println("Please collect refund of Rs." + 0.70 * Integer.parseInt(TrainTable[trno][8]));
                String booked = TrainTable[trno][6];
                String unbooked = TrainTable[trno][7];
                int b = Integer.parseInt(booked);
                int u = Integer.parseInt(unbooked);
                b = b - 1;
                u = u + 1;
                TrainTable[trno][6] = Integer.toString(b);
                TrainTable[trno][7] = Integer.toString(u);
                writeToFile("Refund Initiated towards PNR No. - " + p);
                writeToFile("Refund Amount = " + 0.70 * amount);
            }
        }
        if (passengerFound == 1) {
            pno = t_pno;
            name = t_name;
            age = t_age;
            phno = t_phno;
            pcount = t_pcount;
            System.out.println("Ticket Successfully Cancelled");
            writeToFile("Ticket Successfully Cancelled");
        } else if (passengerFound == 0) {
            System.out.println("PASSENGER Not Found");
        }
    }

    private void doDispList() throws Exception {
        System.out.println(
                "*************************************************\tTRAIN TABLE\t************************************************");
        for (int i = 0; i < linecount; i++) {
            printTable(i);
        }
    }

    private void doSearch() throws Exception {
        int passengerFound = 0;
        System.out.println("Please enter passenger no. to search");
        int p = Integer.parseInt(br.readLine());
        for (int i = 0; i < pcount; i++) {
            if (pno[i] == p) {
                System.out.println("Details Found-");
                passengerFound = 1;
                System.out.println("PNR NO - " + pno[i]);
                System.out.println("NAME - " + name[i]);
                System.out.println("Ph. No. - " + phno[i]);
                System.out.println("AGE - " + age[i]);
            }
        }
        if (passengerFound == 0)
            System.out.println("No such passenger");
    }

    private void doDispUnbooked() throws Exception {
        System.out.println("No. of Booked & Unbooked Tickets-");
        for (int i = 0; i < linecount; i++) {
            System.out.printf(" %-20s %-20s %-20s %-20s", TrainTable[i][0], TrainTable[i][1], TrainTable[i][6],
                    TrainTable[i][7]);
            System.out.println();

        }

    }

    private void doExit() throws Exception {
        System.out.println("**************************************");
        System.out.println("MADE BY - \nName : Hari Om Topal\nClass : CSE-A\nRoll No. : 36");
        System.out.println("**************************************");
        updateTable();
    }

    private void updateTable() throws Exception {
        FileOutputStream fstream = new FileOutputStream("TrainTable.txt");
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fstream));
        for (int i = 0; i < linecount; i++) {
            for (int j = 0; j < 9; j++) {

                br.write(TrainTable[i][j] + "\t");
            }
            br.newLine();
        }
        br.close();
    }

    private void doLogin() throws Exception {
        int reset = 1;
        while (reset == 1) {
            System.out.println("SELECT \n1.Registered User \n2.New User");
            int choice = Integer.parseInt(br.readLine());

            if (choice == 1) {
                if (ID() == 0) {
                    reset = 0;
                    doMenu();
                } else {
                    System.out.println("press 1 to retry , 0 to continue");
                    reset = Integer.parseInt(br.readLine());
                }
            } else if (choice == 2) {
                RegisterUser();
                doLogin();
            } else {
                System.out.println("Invalid Choice \n");
                doLogin();
            }
        }
    }

    private void printBill() {
        Date d1 = new Date();
        writeToFile("*************************************************");
        String s = ("\nLogged In AT " + d1);
        writeToFile(s);
        int i = 0;
        while (pno[i] != 0) {
            writeToFile("PNR NO. - " + Integer.toString(pno[i]));
            writeToFile("NAME - " + name[i]);
            writeToFile("AGE - " + Integer.toString(age[i]));
            writeToFile("PHONE NO - " + phno[i]);
            s = ("Train No - " + trainid + " l- " + (TrainTable[trainid][1]) + " FROM " + (TrainTable[trainid][2])
                    + " TO " + (TrainTable[trainid][3]));
            writeToFile(s);
            writeToFile("Amount = Rs. " + amount);

            i++;
        }
    }

    private void displayBill() {
        System.out.println("*************************************************");
        System.out.println("Bill Generated On " + d1);
        int i = 0;
        while (pno[i] != 0) {
            System.out.println("PNR NO. - " + Integer.toString(pno[i]));
            System.out.println("NAME - " + name[i]);
            System.out.println("AGE - " + Integer.toString(age[i]));
            System.out.println("PHONE NO - " + phno[i]);
            System.out.println("Train No - " + trainid + " - " + (TrainTable[trainid][1]) + " FROM "
                    + (TrainTable[trainid][2]) + " TO " + (TrainTable[trainid][3]));
            System.out.println("Amount = Rs. " + amount);
            i++;
        }

    }

    private void doHistory() throws Exception {
        File f1 = new File(userID + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(f1));
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
        br.close();
    }

    public static void main(String args[]) {
        try {
            RailReserve obj = new RailReserve();
            obj.doLogin();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}