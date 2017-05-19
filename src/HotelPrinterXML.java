import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Printer for Hotels data in XML format
 * @author Suleyman Uslu
 */
public class HotelPrinterXML implements HotelPrinter {

    /** MEMBERS **/

    List<Hotel> hotels;

    /** CONSTRUCTORS **/

    HotelPrinterXML(List<Hotel> hotels) {

        this.hotels = hotels;
    }

    /** METHODS **/

    /**
     * Prints Hotel data
     */
    @Override
    public void print(String fileName) {

        try {

            PrintWriter writer = new PrintWriter(fileName);

            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<hotels>");

            for(Hotel hotel : hotels) {

                writer.println("\t<hotel>");
                writer.println("\t\t<name>" + replaceXmlChars(hotel.getName()) + "</name>");
                writer.println("\t\t<address>" + replaceXmlChars(hotel.getAddress()) + "</address>");
                writer.println("\t\t<contact>" + replaceXmlChars(hotel.getContact()) + "</contact>");
                writer.println("\t\t<phone>" + replaceXmlChars(hotel.getPhone()) + "</phone>");
                writer.println("\t\t<uri>" + replaceXmlChars(hotel.getUri()) + "</uri>");
                writer.println("\t\t<stars>" + Integer.toString(hotel.getStars()) + "</stars>");
                writer.println("\t</hotel>");
            }

            writer.println("</hotels>");

            writer.close();
        }
        catch (FileNotFoundException e) {

            Logger.log("ERROR: Cannot write to file " + fileName,HotelPrinterXML.class,LogLevel.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Replaces characters not allowed in XML with corresponding XML versions
     * @param input string with not allowed characters
     * @return      string with replaced characters
     */
    static String replaceXmlChars(String input) {

        String output;

        output = input.replaceAll("&", "&amp;");
        output = output.replaceAll("\"", "&quot;");
        output = output.replaceAll("'", "&apos;");
        output = output.replaceAll("<", "&lt;");
        output = output.replaceAll(">", "&gt;");

        return output;
    }
}
