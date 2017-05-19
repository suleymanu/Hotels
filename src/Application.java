/**
 * Application for Hotel data formatter
 */
public class Application {

    // application-wise log level
    static LogLevel logLevel = LogLevel.DEBUG;

    public static void main(String[] args) {

        HotelDataFormatter hotelDataFormatter = new HotelDataFormatter();
        hotelDataFormatter.readCSVFile("hotels.csv");
        hotelDataFormatter.sort();
        hotelDataFormatter.printHotelsAsXML("hotels.xml");
        hotelDataFormatter.printHotelsAsJSON("hotels.json");
    }
}
