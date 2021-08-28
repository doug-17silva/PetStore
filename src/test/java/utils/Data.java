package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class Data {

    //Método para ler um arquivo json - método estático
//    public static String lerJson(String caminhoJson) throws IOException {
//        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
//    }

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    public List<String[]> lerCSV(String caminhoCSV) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(caminhoCSV)); //Lê um texto
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build(); //Abre como um csv - o n°1 siginifica a qtde de linhas que deverão ser ignoradas
        List<String[]> users = csvReader.readAll(); //Lê todos os dados do csv

        return users;
    }

}
