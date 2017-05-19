import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Log class
 * @author Suleyman Uslu
 */
public class Logger {

    private static String logFile = "hotels.log";

    /**
     * Prints log to log file
     * @param logText log text to be printed
     * @param caller class who calls log
     */
    public static void log(String logText, Class caller, LogLevel level) {

        if(level.ordinal() >= Application.logLevel.ordinal()) {

            try {
                PrintWriter logWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = Calendar.getInstance().getTime();
                String dateString = dateFormat.format(date);

                String classString = caller.getName();

                String logLevelString = level.name();

                String printString = logLevelString + ": " + dateString + " : ( " + classString + " ) " + logText;

                System.out.println(printString);
                logWriter.println(printString);

                logWriter.flush();
                logWriter.close();
            }
            catch (IOException e) {
                System.out.println("ERROR: Unable to write " + logFile);
                e.printStackTrace();
            }
        }
    }
}
