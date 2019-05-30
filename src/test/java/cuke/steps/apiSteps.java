package cuke.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cuke.feature.TableData;
import cuke.utils.Util;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class apiSteps extends Util {
    private TableData requiredData;

    @When("^a user sends a POST request to fishAPI URL, the response is Not Found$")
    public void aUserSendsAPOSTRequestToFishAPIURLTheResponseIsNotFound() throws IOException {
        assertEquals(404, postResponse("sentence", "html", 2).getStatusLine().getStatusCode());

    }

    @When("^a user sends a GET request to fishAPI URL with \"([^\"]*)\", the response is \"([^\"]*)\"$")
    public void aUserSendsAGETRequestToFishAPIURLWithTheResponseIs(String arg0, String arg1) throws Throwable {
        assertEquals(200, getResponse("title", "html", 1000000).getStatusLine().getStatusCode());
        assertThat("You requested too much content. Be more moderate", is(getResponse("title", "html", 1000000).getEntity().getContent().toString()));
        throw new PendingException();
    }

    @When("^a user sends a GET request with number like \"([^\"]*)\", type like \"([^\"]*)\", format like \"([^\"]*)\"$")
    public void aUserSendsGetWithParams(int number, String type, String format) throws IOException {
        getResponse(type, format, number);
        requiredData = this.retrieveResourceFromResponse(getResponse(type, format, number), TableData.class);
    }

//    @Then("the result string matches \"([^\"]*)\"$")
//    public void aUserSendsAGETRequestWithNumberLikeTypeLikeFormatLikeThenTheResultStringMatches(DataTable table) throws Throwable {
//        Map<String, String> map = table.asMap(String.class, String.class);
//        Assert.assertThat(map.get("result")).isEqualTo(requiredData.getResult());
//        throw new PendingException();
//    }

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
}


