import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Creates a new format for hotel data given in CSV format
 * @author Suleyman Uslu
 */
public class HotelDataFormatter {

    /** MEMBERS **/

    List<Hotel> hotels = new ArrayList<>();

    /** METHODS **/

    /**
     * Reads a CSV file and created Hotel objects
     * @param fileName  name of the file to be read in 'csv-files' folder
     */
    void readCSVFile(String fileName) {

        try {
            Scanner scanner = new Scanner(new File(fileName));
            // assuming the first line is the header
            if(scanner.hasNextLine()) {
                String headerLine = scanner.nextLine();
                String[] headers = headerLine.split(",");

                // read new lines as a Hotel description until the end of file
                while(scanner.hasNextLine()) {

                    Map<String,String> members = new HashMap<>(headers.length);
                    String hotelLine = scanner.nextLine();
                    String field;
                    String remainingLine = hotelLine;
                    int headerOrder = 0;

                    // continue parsing the line until it becomes empty
                    while( ! remainingLine.isEmpty() || headerOrder < headers.length) {
                        String[] fieldAndData = extractOneField(remainingLine);
                        field = fieldAndData[0];
                        remainingLine = fieldAndData[1];
                        members.put(headers[headerOrder],field);
                        headerOrder++;
                    }

                    // create new Hotel object and add it to Hotel list
                    Hotel hotel = new Hotel(members);
                    hotels.add(hotel);
                }
            }
            else {
                Logger.log("Header is not found. Terminating...",HotelDataFormatter.class,LogLevel.ERROR);
            }
        }
        catch (FileNotFoundException e) {
            Logger.log("Cannot open file: csv-files/" + fileName,HotelDataFormatter.class,LogLevel.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Extracts the first field of given string and prepares the new string for remaining fields
     * @param line  Hotel data to be parsed
     * @return      first feature encountered and remaining Hotel data
     */
    static String[] extractOneField(String line) {

        String data[] = new String[2];
        data[0] = "";
        data[1] = "";

        if(line.length() == 0) {
            Logger.log("Empty hotel data cannot be parsed",HotelDataFormatter.class,LogLevel.ERROR);
        }
        else {
            int firstCommaIndex = line.indexOf(",");
            int firstQuoteIndex = line.indexOf("\"");

            // if there is no comma ',' or double quote '"'
            if(firstCommaIndex == -1 && firstQuoteIndex == -1) {
                // remaining is just one field
                data[0] = line;
            }
            // if there is no double quote '"' but comma ','
            else if(firstQuoteIndex == -1) {
                data[0] = line.substring(0,firstCommaIndex);
                data[1] = line.substring(firstCommaIndex+1);
            }
            // if there is no comma ',' but double quote '"'
            else if(firstCommaIndex == -1) {
                data[0] = line.substring(1,line.length()-1);
            }
            // if there is both
            else {
                // if comma ',' comes first
                if(firstCommaIndex < firstQuoteIndex) {
                    data[0] = line.substring(0,firstCommaIndex);
                    data[1] = line.substring(firstCommaIndex+1);
                }
                // if quote '"' comes first
                else {
                    int secondQuoteIndex = line.indexOf("\"",firstQuoteIndex+1);
                    data[0] = line.substring(1,secondQuoteIndex);
                    data[1] = line.substring(secondQuoteIndex+2);
                }
            }
        }

        return data;
    }

    /**
     * Prints Hotel data in XML format
     * @param fileName  name of the XML file to be created
     */
    void printHotelsAsXML(String fileName) {

        HotelPrinter hotelPrinter = new HotelPrinterXML(hotels);
        hotelPrinter.print(fileName);
    }

    /**
     * Prints Hotel data in JSON format
     * @param fileName  name of the JSON file to be created
     */
    void printHotelsAsJSON(String fileName) {

        HotelPrinter hotelPrinter = new HotelPrinterJSON(hotels);
        hotelPrinter.print(fileName);
    }

    /**
     * Sorts Hotels based on compareTo method which is currently based on name
     */
    void sort() {

        Collections.sort(hotels);
    }
}
