package cuke.steps;

import cucumber.api.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class apiSteps {
    private static RequestSpecification spec;

    @BeforeClass
    public static RequestSpecification initSpec() {
        spec = new RequestSpecBuilder()

                .build();
        return spec;
    }

    @When("^a user sends a POST request to fishAPI URL and gets the status Not Found$")
    public void aUserSendsAPOSTRequestToFishAPIURLTheResponseIsNotFound() throws IOException {
        given().when().
                post("https://fish-text.ru/post?number=1&type=sentence&format=html").then().statusCode(404);

    }


    @When("^a user sends a GET request to fishAPI URL with number = \"([^\"]*)\", the response is \"([^\"]*)\"$")
    public void aUserSendsAGETRequestToFishAPIURLWithTheResponseIs(int arg0, String arg1) throws Throwable {
        tooMuch(arg0, "sentence", "json", arg1);
    }

    @When("^a user sends a GET request with number like \"([^\"]*)\", type like \"([^\"]*)\", format like \"([^\"]*)\" and gets \"([^\"]*)\" items in response$")
    public void aUserSendsGetWithParams(int number, String type, String format, String resultValue) throws IOException {
        int result = Integer.parseInt(resultValue);
        if (type.matches("sentence")) {
            sentenceTest(number, type, format, result);
        } else if (type.matches("title")) {
            titleTest(number, type, format, result);
        } else {
            paragraphTest(number, type, format, result);
        }
    }


    public void sentenceTest(int number, String type, String format, int matcherValue) {

        String response = given()
                .contentType("text/html; charset=UTF-8")
                .baseUri("https://fish-text.ru/")
                .when().
                        param("number", number).param("type", type).param("format", format).
                        get("get").then().extract().response().asString();
        System.out.println(response);

        Assert.assertThat((StringUtils.countMatches(response, ".")), Matchers.is(matcherValue));

    }

    public void titleTest(int number, String type, String format, int matcherValue) {

        String response = given().
                contentType("text/html; charset=UTF-8").
                baseUri("https://fish-text.ru/").
                when().
                param("number", number).param("type", type).param("format", format).
                get("get").then().extract().response().asString();
        System.out.println(response);


        Assert.assertThat((StringUtils.countMatches(response, "<h1>")), Matchers.is(matcherValue));

    }

    public void paragraphTest(int number, String type, String format, int matcherValue) {

        String response = given().
                contentType("text/html; charset=UTF-8").
                baseUri("https://fish-text.ru/").
                when().
                param("number", number).param("type", type).param("format", format).
                get("get").then().extract().response().asString();
        System.out.println(response);


        Assert.assertThat((StringUtils.countMatches(response, "<p>")), Matchers.is(matcherValue));

    }

    public void tooMuch(int number, String type, String format, String alert) {
        String response = given().
                contentType("text/html; charset=UTF-8").
                baseUri("https://fish-text.ru/").
                when().
                param("number", number).param("type", type).param("format", format).
                get("get").then().extract().response().asString();
        System.out.println(response);

        Assert.assertThat(response, Matchers.containsString(alert));
    }


}


