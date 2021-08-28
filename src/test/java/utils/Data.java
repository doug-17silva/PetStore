package utils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class Data {

    //Método para ler um arquivo json - método estático
//    public static String lerJson(String caminhoJson) throws IOException {
//        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
//    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    public Collection<String[]> lerCSV(String caminhoCSV) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(caminhoCSV));

        return null;
    }

}
