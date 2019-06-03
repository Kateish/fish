package cuke.steps;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cuke.feature.TableData;
import cuke.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class apiSteps extends Util {
    private TableData requiredData;
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;
    private String baseURI = "https://fish-text.ru/api";

    @When("^a user sends a POST request to fishAPI URL$")
    public void aUserSendsAPOSTRequestToFishAPIURLTheResponseIsNotFound() throws IOException {
        request = given().baseUri("https://fish-text.ru/post?type=sentence&format=html");

    }

    @Then("^the status code is (\\d+)$")
    public void notFound(int statusCode) {
        response = request.when().post();
        json = response.then().statusCode(statusCode);
    }

    @When("^a user sends a GET request to fishAPI URL with \"([^\"]*)\", the response is \"([^\"]*)\"$")
    public void aUserSendsAGETRequestToFishAPIURLWithTheResponseIs(String arg0, String arg1) throws Throwable {
        assertEquals(200, getResponse("title", "html", 1000000).getStatusLine().getStatusCode());
        assertThat(getResponse("title", "html", 1000000).getEntity().getContent().toString(), is("You requested too much content. Be more moderate"));
        throw new PendingException();
    }

    @When("^a user sends a GET request with number like \"([^\"]*)\", type like \"([^\"]*)\", format like \"([^\"]*)\"$")
    public void aUserSendsGetWithParams(int number, String type, String format) throws IOException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("number", number);
        param.put("type", type);
        param.put("format", format);

        response = request.parameters(param).when().get(baseURI).thenReturn();

        System.out.println("response: " + response.prettyPrint());
//        JsonPath js = new JsonPath(response.toString());
//        js.get().
//                getResponse(type, format, number);
        //assertThat(getResponse(type, format, number).getEntity().getContentType(), Matchers.<Header>hasToString("Content-Type: text/html; charset=utf-8"));
//        System.out.println(retrieveResourceFromResponse(getResponse(type, format, number)));
//        requiredData = this.retrieveResourceFromResponse(getResponse(type, format, number), TableData.class);
    }

    @Then("the result string is")
    public void positive(Map<String, String> response) {

        for (Map.Entry<String, String> field : response.entrySet()) {
            json.body(field.getKey(), equalTo(field.getValue()));
        }
        sentenceCounter(String.valueOf(response));
    }

    @Then("the user gets \"([^\"]*)\" entities$")
    public void resultStringMatches(Map<String, String> response) throws Throwable {
        for (Map.Entry<String, String> field : response.entrySet()) {
            json.body(field.getKey(), containsInAnyOrder(field.getValue()));
//            for (int i = 0; i < arg.size(); i++) {
//                System.out.println(arg.get(i).getNumber());
//                System.out.println(arg.get(i).getType());
//            }
//        System.out.println(list.get(0).get("format"));
//        System.out.println(list.get(0).get("result"));

//        Assert.assertThat(map.get("result")).isEqualTo(requiredData.getResult());
            throw new PendingException();
        }
    }

    private CloseableHttpResponse getResponse(String type, String format, int number) throws IOException {
        HttpGet request0 = new HttpGet("https://fish-text.ru/get?number=" + number + "&format=" + format + "&type=" + type + "");
        CloseableHttpResponse getReponse = HttpClientBuilder.create().build().execute(request0);
        return getReponse;
    }

    private CloseableHttpResponse postResponse(String type, String format, int number) throws IOException {
        HttpPost request = new HttpPost("https://fish-text.ru/post?number=" + number + "");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("format", format));
        request.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse postResponse = HttpClientBuilder.create().build().execute(request);
        return postResponse;
    }


    @And("^the status is OK$")
    public void theStatusIsOK() throws IOException {
        assertThat(getResponse("title", "html", 2).getStatusLine().getStatusCode(), is(200));
    }

    public int sentenceCounter(String response) {
        int count = StringUtils.countMatches(response.toString(), ".");
        return count;

    }
}


