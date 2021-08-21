package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class User {

    String uri = "https://petstore.swagger.io/v2/user\n";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void incluirUsuario() throws IOException {
        String jsonBoby = lerJson("db/user1.json");

        String userId =
        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBoby)
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
