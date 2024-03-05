import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PostmanEchoTests {

    private static final String BASE_URL = "https://postman-echo.com";

    public static void main(String[] args) throws IOException {
        testGetRequest();
        testPostRawText();
        testPostFormData();
        testPutRequest();
    }

    public static void testGetRequest() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(BASE_URL + "/get?foo1=bar1&foo2=bar2");

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            printResponse(response);
        }
    }

    public static void testPostRawText() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(BASE_URL + "/post");
        StringEntity requestEntity = new StringEntity("{\"test\":\"value\"}", ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            printResponse(response);
        }
    }

    public static void testPostFormData() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(BASE_URL + "/post");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setEntity(new StringEntity("foo1=bar1&foo2=bar2", ContentType.APPLICATION_FORM_URLENCODED));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            printResponse(response);
        }
    }

    public static void testPutRequest() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(BASE_URL + "/put");
        StringEntity requestEntity = new StringEntity("This is expected to be sent back as part of response body.", ContentType.TEXT_PLAIN);
        httpPut.setEntity(requestEntity);

        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            printResponse(response);
        }
    }

    public static void printResponse(CloseableHttpResponse response) throws IOException {
        System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("Response Body: " + responseString);
        }
    }
}
