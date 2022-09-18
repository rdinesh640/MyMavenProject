//import com.digital.constants.ActionConstants;
import mockit.*;
//import mockit.integration.junit5.JMockitExtension;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class LineCarrierTest {

    @InjectMocks
    LineCarrier lineCarrier;


    @BeforeEach
    public void setup() {
        lineCarrier = new LineCarrier();
    }

    @Test
    public void failTest(){
        assertTrue(true);
    }

    @Test
    public void testSendToReconQueue2conditiontrue() {

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("account_application_resource_id", "456789");
        jsonRequest.put("exclusiveAgreementId", UUID.randomUUID().toString());
        jsonRequest.put("agreementId", UUID.randomUUID().toString());
        jsonRequest.put("action_form", "action_demand.ended");

        try {
            lineCarrier.sendToReconQueue(jsonRequest, "ACTION_DEMAND");
        } catch (Exception f) {
            Assertions.fail(f.getMessage());
        }

    }
}