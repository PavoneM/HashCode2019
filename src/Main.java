import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {
    private static String FILENAME = "example.in";
    private static String OUTPUTNAME = "example.out";

    public static void main(String[] args) {
        try {
            InputStreamReader is = new InputStreamReader(
                Main.class.getClassLoader().getResourceAsStream(Main.FILENAME));
            BufferedReader bf = new BufferedReader(is);
            String line = bf.readLine();
            String[] constants = line.split(" ");
            int firstInt = Integer.parseInt(constants[0]);
            System.out.println(firstInt);
            Main.saveOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveOutput() {
        StringBuilder sb = new StringBuilder("");

        sb.append(3);
        sb.append(" ");
        sb.append("4");
        sb.append("\n");
        sb.append("2");

        try {
            PrintWriter out = new PrintWriter(Main.OUTPUTNAME);
            out.write(sb.toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
