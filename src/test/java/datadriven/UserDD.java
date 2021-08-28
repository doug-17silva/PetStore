package datadriven;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class UserDD {

    String uri = "https://petstore.swagger.io/v2/user\n";
    Data data;

    @BeforeMethod
    public void setup() {
        data = new Data();
    }

    @Test
    public void incluirUsuario() throws IOException {
//        String jsonBoby = Data.lerJson("db/user1.json"); //caso seja criado método estático
        String jsonBody = data.lerJson("db/user1.json");

        String userId =
                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
                .when()
                        .post(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("code", is(200))
                        .body("type", is("unknown"))
                .extract()
                        .path("message")
                ;

        System.out.println("O userId é: " + userId);

    }

}
