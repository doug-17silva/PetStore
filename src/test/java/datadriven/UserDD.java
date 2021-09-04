package datadriven;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.testng.annotations.*;
import utils.Data;
import utils.Log;

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
    Log log;
    int contador = 0;
    double soma; //somar os valores das senhas (somente para verificar como fazer uma soma

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

    @BeforeClass //Antes da classe que executa os testes
    public void setup() throws IOException {
        data = new Data();
//        log = new Log();
        log.iniciarLog(); //criar o arquivo e escrever a linha do cabeçalho
    }

    @AfterClass //É executado depois que todos os testes da classe são executados
    public void tearDown() {
        System.out.println("TOTAL DE REGISTROS: " + contador); //imprime a tde de registros
        System.out.println("SOMA TOTAL: " + soma);
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
        contador += 1;
        System.out.println("Essa é a linha " + contador); //a cada execução imprime o número de regitros inseridos
        soma = soma + Double.parseDouble(password);

    }

}
