
import mockit.MockUp;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import mockit.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResponseExchangeUtilTest {

    ResponseExchangeUtil responseExchangeUtil = mock(ResponseExchangeUtil.class);


    @Before
    public void setUp(){
    }


    @InjectMocks
    NrsPostRestInterceptor nrsPostRestInterceptor;

    private final String data = "{\"name\": \"Sam Smith\", \"technology\": \"Java\"}";
    String COLON = "\":\"";
    String testdata = "{\"" + "name"+COLON + "DINESH"+"\", \"" + "record_name"+COLON + "123456"+"\",\"" + "charge_id"+COLON + "1456"+"\",\"" + "procedure"+COLON + "test"+"\",\""+ "summary"+COLON + "devireddy"+"\"}";
    private final String data1 = "\"" + "name"+COLON + "DINESH"+"\"";
    String name = "Dineshreddy"+"\\."+"reddy"+"\\."+"reddy1";

    @Test
    public void testGetJSONFromString(){

        JSONObject jsonObj = responseExchangeUtil.getJSONFromString(data);
        assertEquals(jsonObj.get("name"), "Sam Smith");
        assertEquals(jsonObj.get("technology"), "Java");
    }

    @Test
    public void testsetStartupContext(){
        responseExchangeUtil.setStartupContext();
    }

    @Test
    public void testGetJSONFromString_returnsNull(){
        JSONObject jsonObj = responseExchangeUtil.getJSONFromString(data1);
        assertNull(jsonObj);
    }

    @Test
    public void testMaskQtInfo(){
        String jsonObj = responseExchangeUtil.maskQtInfo(testdata);
        assertNotNull(jsonObj);
    }
    @Test
    public void testsetLog4jContext(){
        ResponseExchangeUtil responseExchangeUtil = new ResponseExchangeUtil();
        ResponseExchangeUtil spyTemp = Mockito.spy(responseExchangeUtil);
        String[] lstData = {"Dinesh", "reddy"};
        when(ResponseExchangeUtil.getpool()).thenReturn(name);
        Map<String, Integer> mp = new HashMap<>();
        mp.put("BMMW",5);
        JSONObject response = new JSONObject(mp);

        String res = spyTemp.setLog4jContext(response);
        assertNotNull(res);

    }
}
