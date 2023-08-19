package apitesting;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.UUID;

public class User_Post_Get_Put_Delete_Test {

    UUID uuid = UUID.randomUUID();
    HashMap <String, String> map = new HashMap<>();
    int id;

    @BeforeTest
    public void addInfo(){
        map.put("name", "fake name");
        map.put("email", uuid + "@gmail.com");
        map.put("gender", "female");
        map.put("status", "active");
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "/public/v2/users";
    }

    @Test(priority = 0)
    public void postRequest(){
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(map)
                .header("Authorization", "Bearer type or paste token here")
                .when()
                .post()
                .then()
                .statusCode(201)
                .log().all()
                .contentType(ContentType.JSON).extract().response();
        JsonPath jsonPath = response.jsonPath();
        id = jsonPath.get("id");
        System.out.println("ID = " + id);
    }

    @Test(priority = 1)
    public void getRequest(){
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "/public/v2/users/" + id;

        RestAssured
                .given()
                .contentType("application/json")
                .header("Authorization"," Bearer type or paste token here")
                .when()
                .get()
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 2)
    public void putRequest(){
        map.put("name", "fake name");
        map.put("email", uuid + "@gmail.com");
        map.put("gender", "female");
        map.put("status", "inactive");
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "/public/v2/users/" + id;

        RestAssured
                .given()
                .contentType("application/json")
                .body(map)
                .header("Authorization", "Bearer type or paste token here")
                .when()
                .put()
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 3)
    public void deleteRequest(){
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "public/v2/users/" + id;

        RestAssured
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer type of paste token here")
                .when()
                .delete()
                .then()
                .statusCode(204);
    }
}
