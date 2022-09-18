
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static java.lang.System.getSecurityManager;

@Component
public class NrsPostRestInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    AuthorizationsService authorizationService;

    private static final Logger logger = LoggerFactory.getLogger(ResponseExchangeUtil.class);

    @Value("{${itr.nrsobjectname:defaultUsername}}")
    private String nrsItrObject;

    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
                                                final ClientHttpRequestExecution executor) throws IOException {
        final HttpRequest wrapper = new HttpRequestWrapper(request);

        final HttpHeaders header = wrapper.getHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        header.add("hjApproval",
                "Basic " + getBase64EncodedValue(getNrsUserName(nrsItrObject),
                        getNrsPassword(nrsItrObject)));
        return executor.execute(request, body);
    }

    public String getBase64EncodedValue(final String nrsUserName, final String nrsPassword) {
        try {
        return Base64.getEncoder().encodeToString((nrsUserName + ":" + nrsPassword).getBytes("utf-8"));
        } catch (final UnsupportedEncodingException exception) {
            throw new NrsRestException(exception);
        }
    }



    public String getNrsPassword(String objectName) {
        if(StringUtils.isEmpty(objectName)) {return "";}
        return authorizationService.getItrPassword(objectName);
    }

    public String getNrsUserName(String objectName) {
        if(StringUtils.isEmpty(objectName)) {return "";}
        return authorizationService.getItrUserName(objectName);
    }
}
