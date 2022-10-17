package sparta.com.rp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests {

    static class GetRequests{
        static Response response;

        @BeforeAll
        static void setupAll() {
             //response = RestAssured.get("https://reqres.in/api");
             baseURI = "https://reqres.in/api";
        }

        @Test
        @DisplayName("status code test")
        void statusCodeTest() {
            given().get("/users?page=2").then().statusCode(200);
            //assertEquals(200, response.getStatusCode());
        }

        @Test
        @DisplayName("check that the first person in data list is Michael")
        void checkThatTheFirstPersonInDataListIsMichael() {
            given().get("/users?page=2").then().body("data[0].first_name", equalTo("Michael"));
        }

        @Test
        @DisplayName("check that the first page of users has page field equal to 1")
        void checkThatTheFirstPageOfUsersHasPageFieldEqualToOne() {
            given().get("/users?page=1").then().body("page", equalTo(1));
        }

        @Test
        @DisplayName("check that the first person in data list has id of 1")
        void checkThatTheFirstPersonInDataListHasIdOf1() {
            given().get("/users?page=1").then().body("data[0].id", equalTo(1));
        }

        @Test
        @DisplayName("check that the all the first names in the data list of page 1 are correct")
        void checkThatTheAllTheFirstNamesInTheDataListOfPage1AreCorrect() {
            given().get("/users?page=1").then().body("data.first_name", hasItems("George", "Janet", "Emma", "Eve", "Charles"));
        }

        @Test
        @DisplayName("Check that the id in data of page on go from 1 to 6")
        void checkThatTheIdInDataOfPageOnGoFrom1To6() {
            given().get("/users?page=1").then().body("data.id", hasItems(1,2,3,4,5,6));
        }
    }

    static class PostRequest {
        @BeforeAll
        static void setupAll() {
            baseURI = "https://reqres.in/api";
        }

        @Test
        @DisplayName("Check that the status code for post request is 201")
        void checkThatTheStatusCodeForPostRequestIs201() {
            JSONObject request = new JSONObject();
            request.put("name", "Jake");
            request.put("job", "detective");

            given().
                    contentType(ContentType.JSON).// content sent is of type JSON
                    accept(ContentType.JSON).//response is of type json
                    body(request.toJSONString()).
            when().
                    post("/users").
            then().
                    statusCode(201).log().all();
        }

    }
}
