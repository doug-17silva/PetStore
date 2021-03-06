package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;

public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet"; //endere?o da entidade Pet

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority=1) //identifica o m?todo ou fun??o como um teste para o TestNg
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Charlie Brown"))
                .body("status", is("available"))
                .body("category.name", containsString("dog")) //pode ser feito com "is" tamb?m
//                .body("tags.name", contains("name"))
        ;

    }

    @Test(priority=2)
    public void consultarPet() {
        String petId = "2403198157";
        String tagId = null;

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Charlie Brown"))
                .body("category.name", is("dog")) //pode ser feito com "containsString"
                .body("status", is("available"))
        .extract()
                .path("tag.id") //tentar com "containsString"
        ;

        System.out.println("O id da tag ?: " + tagId);

    }

    @Test(priority=3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Charlie Brown"))
                .body("status", is("sold"))
        ;

    }

    @Test(priority=4)
    public void excluirPet() {
        String petId = "2403198157";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;

    }

    @Test(priority=5)
    public void consultarPetPorStatus() {
        String status = "available";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri +  "/findByStatus?status=" + status)
        .then()
                .log().all()
                .statusCode(200)
                .body("name[]", everyItem(equalTo("Charlie Brown")))

//                .body("name", contains("[\"Charlie Brown\"]")) // vamos arrumar na pr?xima aula (24/08/2021)

        ;

    }

}

