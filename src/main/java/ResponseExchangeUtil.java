import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.System.getSecurityManager;

@Component
public class ResponseExchangeUtil {


    private static final Logger logger = LoggerFactory.getLogger(ResponseExchangeUtil.class);

    private static final String COLON = "\":\"";
    private static System ProcessEnvironment;

    public static JSONObject getJSONFromString(final String data) {

        JSONObject jsonObj = null;

        if (data != null) {
            final org.json.simple.parser.JSONParser parser = new JSONParser();
            try {
                jsonObj = (JSONObject) parser.parse(data);
            } catch (ParseException e) {
                logger.error("Retrieving JSON from String caused failure", e);
            }
        }
        return jsonObj;
    }

    public static void setStartupContext() {
        MDC.clear();
        MDC.put(ActionConstants.SERVICE_NAME, ActionConstants.SERVICENAME);
        MDC.put(ActionConstants.APNAME, ActionConstants.ACTION_DEMAND_ENDED);
    }
     String name;

    public static String setLog4jContext(final JSONObject response) {
        MDC.clear();
        String hostName = getpool();

        if (hostName != null) {
            MDC.put(ActionConstants.HOSTNAME, hostName);
            String gaiaConfigs[] = hostName.split("\\.");

            if(gaiaConfigs.length > 1) {
                MDC.put(ActionConstants.PLACE_NAME, gaiaConfigs[0]);
                MDC.put(ActionConstants.POOL_NAME, gaiaConfigs[1]);
            }
        }

        MDC.put(ActionConstants.ACCOUNT_SYMBOL, getAttributeValue(response, ActionConstants.ACCTAPP_RESID));
        MDC.put(ActionConstants.PARALLELID, getAttributeValue(response, ActionConstants.EXCLUSIVE_AGREEMENT_ID));
        MDC.put(ActionConstants.DEMANDID, getAttributeValue(response, ActionConstants.AGREEMENT_ID));
        MDC.put(ActionConstants.CONTRACTOR_RES_ID, getAttributeValue(response, ActionConstants.ACCTAPP_ID));
        MDC.put(ActionConstants.ACTION_ID, getAttributeValue(response, ActionConstants.ID));
        MDC.put(ActionConstants.UTILITY_NAME, ActionConstants.UTILITYNAME);
        MDC.put(ActionConstants.APNAME, ActionConstants.ACTION_DEMAND_ENDED);
        return hostName;
    }


    private static String getAttributeValue(final JSONObject demand, final String attribute) {


        if(null != demand && null != demand.get(attribute)) {
            return demand.get(attribute).toString();
        }
        return "";
    }

    public static String maskQtInfo(final String payload) {

        String[] maskValues = {"name", "record_name", "charge_id", "procedure", "summary"};
        String maskedPayload = payload;

        if (StringUtils.isNotBlank(maskedPayload)) {
            for(final String fieldNode : maskValues) {
                String[] nodesData = StringUtils.substringsBetween(payload, "\"" + fieldNode + COLON, "\"");
                if (nodesData != null) {
                    for (final String nodeData : nodesData) {
                        maskedPayload = StringUtils.replace(maskedPayload, "\"" + fieldNode + COLON + nodeData + "\"",
                                "\"" + fieldNode + COLON + StringUtils.repeat("*", 5) + "\"");
                    }
                }
            }
        }
        return maskedPayload;
    }

    public static String getpool() {

        final int apiOffest = 5;   // apiOffest was misspelled in the source code

        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            SecurityManager sm = getSecurityManager();
            OperationConfigContents opContents = objectMapper.readValue(System.getenv("DCDOT_OPERATION"),
                    OperationConfigContents.class);
            final String pool = opContents.getOperationUris().get(0);
            return pool.substring(pool.indexOf("apps") + apiOffest, pool.indexOf(".gap")).toUpperCase();
        } catch (final Exception e) {
            logger.error("Cannot set tool contents for log4j context", e);
        }

        return "";
    }

    public static void main(String[] args) {
        String name = "Dineshreddy"+"\\."+"reddy"+"\\."+"reddy1";
        String gaiaConfigs[] = name.split("\\.");
        System.out.println(gaiaConfigs.length);
    }
}
