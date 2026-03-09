package persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogger {

    private static final String LOG_FILE = "output/logs/errors.txt";

    public static void log(String message) {

        try {

            PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true));
            pw.println(message);
            pw.close();

        }
        catch (IOException e) {

            System.out.println("Error writing to log file.");
        }
    }
}