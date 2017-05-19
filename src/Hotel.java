import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Hotel definition
 * @author Suleyman Uslu
 */
public class Hotel implements Comparable<Hotel>{

    /** MEMBERS **/

    private String name = "";
    private String address = "";
    private String contact = "";
    private String phone = "";
    private String uri = "";
    private int stars = 0;

    /** CONSTRUCTORS **/

    /**
     * Creating a Hotel by assigning all of its members
     * @param name      name of the Hotel
     * @param address   address of the Hotel
     * @param contact   contact person of the Hotel
     * @param phone     phone number of the Hotel
     * @param uri       website of the Hotel
     * @param stars     star rating of the Hotel
     */
    public Hotel(String name, String address, String contact, String phone, String uri, int stars) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.phone = phone;
        this.uri = uri;
        this.stars = stars;
    }

    /**
     * Creating a Hotel by assigning some of its members using two lists
     * @param memberNames   name of the member
     * @param memberValues  value of the corresponding member
     */
    public Hotel(List<String> memberNames, List<String> memberValues) {
        if(memberNames.size()==0 || memberValues.size()==0) {
            Logger.log("Not enough data to construct the Hotel object",Hotel.class,LogLevel.ERROR);
        }
        else if(memberNames.size() == memberValues.size()) {
            for (int i=0; i<memberNames.size(); i++) {
                String variable = memberNames.get(i);
                String value = memberValues.get(i);
                switch(variable) {
                    case "name":
                        if( isValidHotelName(value) ) {
                            this.name = value;
                        }
                        else {
                            Logger.log("Hotel name " + value + " is not UTF-8",Hotel.class,LogLevel.ERROR);
                        }
                        break;
                    case "address":
                        this.address = value;
                        break;
                    case "contact":
                        this.contact = value;
                        break;
                    case "phone":
                        this.phone = value;
                        break;
                    case "uri":
                        if( isValidURL(value) ) {
                            this.uri = value;
                        }
                        else {
                            Logger.log("Hotel URL " + value + " is not valid",Hotel.class,LogLevel.ERROR);
                        }
                        break;
                    case "stars":
                        try {
                            this.stars = Integer.parseInt(value);
                        }
                        catch (Exception e) {
                            Logger.log("Star value is not an integer",Hotel.class,LogLevel.ERROR);
                        }
                        break;
                    default:
                        Logger.log("Unknown variable name: " + variable,Hotel.class,LogLevel.ERROR);
                }
            }
        }
        else {
            Logger.log("Cannot initialize Hotel for unequal data list sizes",Hotel.class,LogLevel.ERROR);
        }
    }

    /**
     * Creating a Hotel by assigning some of its members using a map
     * @param memberMap names and values of members of the Hotel object to be constructed
     */
    public Hotel(Map<String,String> memberMap) {
        if(memberMap.size()==0) {
            Logger.log("Not enough data to construct the Hotel object",Hotel.class,LogLevel.ERROR);
        }
        else {
            for(Map.Entry<String, String> variablePair : memberMap.entrySet()) {
                String variable = variablePair.getKey();
                String value = variablePair.getValue();
                switch(variable) {
                    case "name":
                        if( isValidHotelName(value) ) {
                            this.name = value;
                        }
                        else {
                            Logger.log("Hotel name " + value + " is not UTF-8",Hotel.class,LogLevel.ERROR);
                        }
                        break;
                    case "address":
                        this.address = value;
                        break;
                    case "contact":
                        this.contact = value;
                        break;
                    case "phone":
                        this.phone = value;
                        break;
                    case "uri":
                        if( isValidURL(value) ) {
                            this.uri = value;
                        }
                        else {
                            Logger.log("Hotel URL " + value + " is not valid",Hotel.class,LogLevel.ERROR);
                        }
                        break;
                    case "stars":
                        try {
                            this.stars = Integer.parseInt(value);
                        }
                        catch (Exception e) {
                            Logger.log("Star value is not an integer",Hotel.class,LogLevel.ERROR);
                        }
                        break;
                    default:
                        Logger.log("Unknown variable name: " + variable,Hotel.class,LogLevel.ERROR);
                }
            }
        }
    }

    /** METHODS **/

    /**
     * Compares Hotels based on their name
     * @param hotel Hotel to be compared
     * @return      comparison based on names
     */
    @Override
    public int compareTo(Hotel hotel) {

        return this.name.compareTo(hotel.name);
    }

    /**
     * Checks if a URL is valid
     * @param url   url to be validated
     * @return      true if the url is valid
     */
    boolean isValidURL(String url) {

        String protocol1 = url.substring(0,7);
        String protocol2 = url.substring(0,8);

        return protocol1.equals("http://") || protocol2.equals("https://");
    }

    /**
     * Checks if a Hotel's name has any character other than UTF-8
     * @param name  Hotel name to be validated
     * @return      true if Hotel's name has only UTF-8 characters
     */
    static boolean isValidHotelName(String name) {

        try {
            byte[] bytes = name.getBytes("UTF-8");
            return isValidUTF8(bytes);
        }
        catch (UnsupportedEncodingException e) {
            Logger.log("Encoding : " + name + " " + e.toString(),Hotel.class,LogLevel.ERROR);
            return false;
        }
    }

    /**
     * Checks if a byte array in an encoded UTF-8 string
     * @param input byte array to be encoded
     * @return      true if it is UTF-8
     */
    static boolean isValidUTF8(byte[] input) {
        int i = 0;
        // Check for BOM
        if (input.length >= 3 && (input[0] & 0xFF) == 0xEF
                && (input[1] & 0xFF) == 0xBB & (input[2] & 0xFF) == 0xBF) {
            i = 3;
        }

        int end;
        for (int j = input.length; i < j; ++i) {
            int octet = input[i];
            if ((octet & 0x80) == 0) {
                continue; // ASCII
            }

            // Check for UTF-8 leading byte
            if ((octet & 0xE0) == 0xC0) {
                end = i + 1;
            } else if ((octet & 0xF0) == 0xE0) {
                end = i + 2;
            } else if ((octet & 0xF8) == 0xF0) {
                end = i + 3;
            } else {
                // Java only supports BMP so 3 is max
                return false;
            }

            while (i < end) {
                i++;
                octet = input[i];
                if ((octet & 0xC0) != 0x80) {
                    // Not a valid trailing byte
                    return false;
                }
            }
        }
        return true;
    }

    /** GETTERS AND SETTERS **/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
