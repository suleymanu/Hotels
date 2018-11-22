/**
 * Application for Hotel data formatter
 * @author Suleyman Uslu
 */
public class Application {

    // application-wide log level
    static LogLevel logLevel = LogLevel.DEBUG;

    public static void main(String[] args) {

        HotelDataFormatter hotelDataFormatter = new HotelDataFormatter();
        hotelDataFormatter.readCSVFile("res/hotels.csv");
        hotelDataFormatter.sort();
        hotelDataFormatter.printHotelsAsXML("res/hotels.xml");
        hotelDataFormatter.printHotelsAsJSON("res/hotels.json");
    }
}
