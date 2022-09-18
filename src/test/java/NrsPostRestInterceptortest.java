import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NrsPostRestInterceptortest {

    @InjectMocks
    NrsPostRestInterceptor nrsPostRestInterceptor;
    @Mock
    ClientHttpRequestInterceptor ClientHttpRequestInterceptor;
    @Mock
    HttpRequest request;
    @Mock
    ClientHttpRequestExecution executor;

    String objectName = null;
    String name = "Dinesh";
    String str = "PANKAJ";
    byte[] body = str.getBytes();


    @Test
    public void testgetNrsPassword_null(){
        String response = nrsPostRestInterceptor.getNrsPassword(objectName);
        assertEquals(response,"");
    }

    @Test(expected = NullPointerException.class)
    public void testgetNrsPassword_nullpointerexception(){
        nrsPostRestInterceptor.getNrsPassword(name);
    }

    @Test
    public void testgetNrsUserName_null(){
        String response = nrsPostRestInterceptor.getNrsUserName(objectName);
        assertEquals(response,"");
    }

    @Test(expected = NullPointerException.class)
    public void testgetNrsUserName_nullpointerexception(){
        nrsPostRestInterceptor.getNrsUserName(name);
    }
    @Test
    public void testgetNrsPassword() throws IOException {
        final HttpRequest wrapper = new HttpRequestWrapper(request);
        HttpHeaders header = new HttpHeaders();
        when(wrapper.getHeaders()).thenReturn(header);
        ClientHttpResponse response = nrsPostRestInterceptor.intercept(request,body,executor);
        assertEquals(response,null);
    }

    @Test
    public void testgetBase64EncodedValue(){
        String response = nrsPostRestInterceptor.getBase64EncodedValue("Dinesh","dinesh");
        assertNotNull(response);
    }

    @Test
    public void testgetBase64EncodedValue_Exception() throws UnsupportedEncodingException {
        nrsPostRestInterceptor.getBase64EncodedValue("Dinesh","dinesh");
    }

    @Test
    public void testgetBase64EncodedValue_exception(){
        when(nrsPostRestInterceptor.getBase64EncodedValue("","")).thenThrow(UnsupportedEncodingException.class);
        //String response = nrsPostRestInterceptor.getBase64EncodedValue("","");
        //assertNotNull(response);
    }

    @Test(expected = NullPointerException.class)
    public void getBase64EncodedValue_Exception() throws UnsupportedEncodingException {
        String nrsUserName = "peter";
        String nrsPassword = "pasword";
            doThrow(UnsupportedEncodingException.class).when(Base64.getEncoder().encodeToString((nrsUserName + ":" + nrsPassword).getBytes("utf-8")));
        //interceptor.getBase64EncodedValue(nrsUserName,nrsPassword);
        }
}
