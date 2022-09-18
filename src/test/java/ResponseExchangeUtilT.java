
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.digital.pojo.OperationConfigContents;
//import mockit.integration.junit5.JMockitExtension;
import mockit.MockUp;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.MDC;
import org.mockito.junit.jupiter.MockitoExtension;

import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

//@ExtendWith(JMockitExtension.class)
public class ResponseExchangeUtilT {

    @BeforeEach
    public void setUp() {
    }

    @Mocked
    private ObjectMapper mapper;

    @AfterEach
    public void breakDown() {
    }

    @AfterEach
    public void tearDown() { MDC.clear(); }

    @Test
    public void testGetJSONFromStringNotNull() {
        Assertions.assertNotNull(ResponseExchangeUtil.getJSONFromString("{\"test\":1}"));
    }

    @Test
    public void testGetJSONFromStringExcep() {
        Assertions.assertNull(ResponseExchangeUtil.getJSONFromString("test"));
    }

    @Test
    public void testGetJSONFromStringParseExcep() {
        Assertions.assertNull(ResponseExchangeUtil.getJSONFromString("{\"test\":\1}"));
    }

    @Test
    public void testSetStartupContext() {
        ResponseExchangeUtil.setStartupContext();
        Assertions.assertEquals("ACTION_DEMAND_ENDED", MDC.get("applicationName"));
    }

    @Test
    public void testSetLog4jContext() {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("account_application_resource_id", "456789");
        jsonRequest.put("exclusiveAgreementId", "876543");
        jsonRequest.put("agreementId", "1234567890");
        ResponseExchangeUtil.setLog4jContext(jsonRequest);
        Assertions.assertEquals(jsonRequest.get("exclusiveAgreementId"), MDC.get("parallelId"));

    }

    @Test
    public void testSetLog4jContextWithNullAttribute() {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("account_application_resource_id", "456789");
        jsonRequest.put("agreementId", "1234567890");
        ResponseExchangeUtil.setLog4jContext(jsonRequest);
        Assertions.assertEquals(null, MDC.get("parallelId"));

    }

    @Test
    public void testMaskQtData() {
        String request = "{\"test\":\1,\"name\":\"Test22\",\"id\":\"9186\",\"record_name\":\"AccountValuation\",\"summary\":\"See beta KT\"" +
                ",\"rating\":\"undetermined\",\"procedure\":\"See beta KT to continue operation\",\"action_form\":\"verification.ended\"," +
                "\"charge_id\":\"12345\"}";

        String maskedRequest = "{\"test\":\1,\"name\":\"****\",\"id\":\"9186\",\"record_name\":\"****\",\"summary\":\"****\"" +
                ",\"rating\":\"undetermined\",\"procedure\":\"****\",\"action_form\":\"verification.ended\"," +
                "\"charge_id\":\"****\"}";

        Assertions.assertEquals(maskedRequest, ResponseExchangeUtil.maskQtInfo(request));
        Assertions.assertEquals(null, ResponseExchangeUtil.maskQtInfo(null));
        Assertions.assertEquals(" ", ResponseExchangeUtil.maskQtInfo(" "));

    }

 @Test
    public void testSetLog4jContext1() {
        final JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("account_application_resource_id", "456789");
        jsonRequest.put("exclusiveAgreementId", "876543");
        jsonRequest.put("agreementId", "1234567890");
        new MockUp<ResponseExchangeUtil>() { @Mock String getpool() { return ""; } };
        ResponseExchangeUtil.setLog4jContext(jsonRequest);
        Assertions.assertEquals(jsonRequest.get("exclusiveAgreementId"), MDC.get("parallelId"));
        Assertions.assertEquals(null, MDC.get("place-name"));
        Assertions.assertEquals(null, MDC.get("pool-name"));

    }

    @Test
    public void testSetLog4jContextWithoutDot() {
        final JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("account_application_resource_id", "456789");
        jsonRequest.put("exclusiveAgreementId", "876543");
        jsonRequest.put("agreementId", "1234567890");
        //(MockUp) getPool() { return "SOURCE_ONE";};
        ResponseExchangeUtil.setLog4jContext(jsonRequest);
        Assertions.assertEquals(jsonRequest.get("exclusiveAgreementId"), MDC.get("parallelId"));
        Assertions.assertEquals(null, MDC.get("place-name"));
        Assertions.assertEquals(null, MDC.get("pool-name"));
    }
    public void testSetLog4jContextWithDot() {
        final JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("account_application_resource_id", "456789");
        jsonRequest.put("exclusiveAgreementId", "876543");
        jsonRequest.put("agreementId", "1234567890");
        String name = "java"+"\\."+"python"+"\\."+"c++";
        //(MockUp) getPool() { return name;};
        ResponseExchangeUtil.setLog4jContext(jsonRequest);
        Assertions.assertEquals(jsonRequest.get("exclusiveAgreementId"), MDC.get("parallelId"));
        System.out.println(MDC.get("place-name")+"**********");
        Assertions.assertEquals("java"+"\\", MDC.get("place-name"));
        Assertions.assertEquals("python"+"\\", MDC.get("pool-name"));
    }


    @Test
    public void testGetPoolGivenNullEnvVariableReturnEmpty() throws IOException {
        new Expectations() {
            {
                mapper.readValue(System.getenv( "DCDOT_OPERATION"), OperationConfigContents.class);
                result = null;
            }

        };
        Assertions.assertEquals("", ResponseExchangeUtil.getpool());
    }

    @Test
    public void testGetPoolGivenEmptyAppUriReturnEmpty() throws IOException {
        OperationConfigContents opContents = new OperationConfigContents();
        opContents.setOperationUris(Collections.emptyList());
        new Expectations() {
            {
                mapper.readValue(System.getenv( "DCDOT_OPERATION"), OperationConfigContents.class);
                result = opContents;
            }

        };
        Assertions.assertEquals("", ResponseExchangeUtil.getpool());
    }

    @Test
    public void testGetPoolGivenInvalidUriFormatReturnEmpty() throws IOException {
        OperationConfigContents opContents = new OperationConfigContents();
        List<String> uris = new ArrayList<>();
        uris.add("https://secure.source-one.com");
        opContents.setOperationUris(uris);
        new Expectations() {
            {
                mapper.readValue(System.getenv( "DCDOT_OPERATION"), OperationConfigContents.class);
                result = opContents;
            }

        };
        Assertions.assertEquals("", ResponseExchangeUtil.getpool());
    }

}
