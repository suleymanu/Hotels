import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Printer for Hotel data in JSON format
 * @author Suleyman Uslu
 */
public class HotelPrinterJSON implements HotelPrinter {

    /** MEMBERS **/

    List<Hotel> hotels;

    /** CONSTRUCTORS **/

    HotelPrinterJSON(List<Hotel> hotels) {

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

            writer.println("{");
            writer.println("\t\"hotels\": {");
            writer.println("\t\t\"hotel\": [");

            for(int i=0; i<hotels.size(); i++) {

                Hotel hotel = hotels.get(i);

                writer.println("\t\t\t{");
                writer.println("\t\t\t\t\"name\": \"" + hotel.getName() + "\",");
                writer.println("\t\t\t\t\"address\": \"" + hotel.getAddress() + "\",");
                writer.println("\t\t\t\t\"contact\": \"" + hotel.getContact() + "\",");
                writer.println("\t\t\t\t\"phone\": \"" + hotel.getPhone() + "\",");
                writer.println("\t\t\t\t\"uri\": \"" + hotel.getUri() + "\",");
                writer.println("\t\t\t\t\"stars\": \"" + Integer.toString(hotel.getStars()) + "\"");
                if(i==hotels.size()-1)
                    writer.println("\t\t\t}");
                else
                    writer.println("\t\t\t},");
            }

            writer.println("\t\t]");
            writer.println("\t}");
            writer.println("}");

            writer.close();
        }
        catch (FileNotFoundException e) {

            Logger.log("Cannot write to file " + fileName,HotelPrinterJSON.class,LogLevel.ERROR);
            e.printStackTrace();
        }
    }
}
