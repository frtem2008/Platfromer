import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UsefulCoords {
    public static void main(String[] args) throws IOException {
        File coords = new File("coords.txt");
        File tilds = new File("tilds.txt");
        File output = new File("output.txt");
        coords.createNewFile();
        output.createNewFile();
        tilds.createNewFile();
        clearFile(output.getPath());
        clearFile(tilds.getPath());
        Scanner s = new Scanner(coords);
        while (s.hasNextLine()) {
            String cur = s.nextLine();
            cur = cur.replaceAll(",", "~");
            cur = cur.replaceAll("- ", "");
            cur = cur.replaceAll(" ", "");
            String[] curSplit = cur.split("~");
            String first, second, third, forth;
            first = str(curSplit[0]);
            second = str(curSplit[1]);
            third = str(curSplit[2]);
            forth = str(curSplit[3]);
            appendStrToFile(output.getPath(), first + "~" + second + "~" + third + "~" + forth + "\n");
        }
    }

    public static int firstNum(String str) {
        char[] num = {'-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < num.length; j++) {
                if (str.charAt(i) == num[j]) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int lastNum(String str) {
        int a = 0;
        char[] num = {'-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < num.length; j++) {
                if (str.charAt(i) == num[j]) {
                    a = i;
                }
            }
        }
        return a;
    }

    public static String str(String str) {
        int a = firstNum(str);
        int b = lastNum(str);
        return str.substring(a, b + 1);
    }

    public static void appendStrToFile(String fileName, String str) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(str);
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
    }

    public static void clearFile(String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));
            out.write("");
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
    }
}
