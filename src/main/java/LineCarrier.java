//import com.mq.jms.MQQueueConnectionFactory;
//import com.digital.constants.ActionConstants;
//import org.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/*import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
//import javax.jms.Session;*/

@Component
public class LineCarrier {

    public static final String UTILITY_FORM = "UTILITY_FORM";
    private static final Logger agreementLogger = LoggerFactory.getLogger("ActionDemandEndedAgreementLogger");

    @Value("${mq.action.demand.ended.recon.queue:defaultQueue}")
    private String actionDemandEndedReconQueue;
    @Value("${mq.action.demand.ended.queue:defaultQueue}")
    private String actionDemandEndedQueue;

    @Autowired
    @Qualifier("mqConnectionFactory")
    //private ConnectionFactory mqConnectionFactory;

    public void sendToReconQueue(final Object response, final String responseForm) {
        if (response != null) {
            agreementLogger.info("Response to MQ: " + actionDemandEndedReconQueue + " for responseForm: " + responseForm);
            if (responseForm.equals(ActionConstants.ACTION_DEMAND_PICKER)) {
                System.out.println(responseForm);
                try {
                    System.out.println("dinesh");
                    //send(new Response(response, responseForm, null), responseForm, actionDemandEndedReconQueue);
                } catch (final Exception e) {
                    agreementLogger.error("#VITAL WARNING# Cannot send response to Queue" + actionDemandEndedReconQueue, e);
                }
            }
        }
    }

    /*public void send(final Response response, final String responsePicker, final String statedQueueName) {
        agreementLogger.debug("Response to " + statedQueueName + " response " + response);
        try {
            JmsTemplate jmsTemplate = new JmsTemplate(mqConnectionFactory);
            JmsTemplate.send(statedQueueName, arrangeJmsObjectMessage(response, responsePicker));
            agreementLogger.info("Response to MQ: " + statedQueueName + " for responseForm: " + responsePicker);
        } catch (final Exception e) {
            agreementLogger.error("#VITAL WARNING# send(response, responsePicker): Cannot send response to Queue" + statedQueueName, e);
        }
    }

    private MessageCreator arrangeJmsObjectMessage(final Response response, final String responsePicker) {
        return new MessageCreator() {
            public javax.jms.Message createMessage(final Session session) throws JMSException {
                final ObjectMessage objMessage = session.createObjectMessage();
                objMessage.setObject(response);

                if(StringUtils.hasLength(responsePicker)) {
                    objMessage.setStringProperty(UTILITY_FORM, responsePicker);
                }
                return objMessage;
            }
        }*/
    }
