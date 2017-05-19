import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Suleyman Uslu
 */
public class TestHotel {

    /**
     * Tests extractOneField function over several different possible Hotel data
     */
    @Test
    public void testExtractOneField() {
        Map<String,String[]> check = new HashMap<>(10);
        check.put("a",new String[]{"a",""});
        check.put("a,b",new String[]{"a","b"});
        check.put("a,b,c",new String[]{"a","b,c"});
        check.put("a,\"b\"",new String[]{"a","\"b\""});
        check.put("a,\"b,c\"",new String[]{"a","\"b,c\""});
        check.put("a,b,\"c,d\"",new String[]{"a","b,\"c,d\""});
        check.put("\"a\",b",new String[]{"a","b"});
        check.put("\"a\",b,c",new String[]{"a","b,c"});
        check.put("\"a,b\",c",new String[]{"a,b","c"});
        check.put("\"a,b\",c,d",new String[]{"a,b","c,d"});
        for(Map.Entry<String,String[]> entry : check.entrySet()) {
            String[] result = HotelDataFormatter.extractOneField(entry.getKey());
            assert result[0].equals(entry.getValue()[0]);
            assert result[1].equals(entry.getValue()[1]);
        }
    }
}
