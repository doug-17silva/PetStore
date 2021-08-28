package datadriven;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;
import static org.hamcrest.CoreMatchers.is;

public class UserDD {

    String uri = "https://petstore.swagger.io/v2/user\n";
    Data data;

    @DataProvider //Provedor de dados para os testes
    public Iterator<Object[]> provider() throws IOException {
        List<Object[]> usersTestData  = new ArrayList<>();
        String[] testCase = null; //é um array pq guarda todos os dados de um usuário
        String linha = null;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("db/users.csv"));

        while((linha = bufferedReader.readLine()) != null) {
            testCase = linha.split(",");
            usersTestData.add(testCase);
        }

        return usersTestData.iterator();
    }

    @BeforeMethod
    public void setup() {
        data = new Data();
    }

    @Test(dataProvider = "provider")
    public void incluirUsuario(String id, String username, String firstName, String lastName, String email, String password,String phone, String userStatus) throws IOException {
//        String jsonBoby = Data.lerJson("db/user1.json"); //caso seja criado método estático
        String jsonBody = new JSONObject()
        .put("id", id)
        .put("username", username)
        .put("firstName", firstName)
        .put("lastName", lastName)
        .put("email", email)
        .put("password", password)
        .put("phone", phone)
        .put("userStatus", userStatus)
        .toString();

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
