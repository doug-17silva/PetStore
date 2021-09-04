package utils;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {

    public static void iniciarLog() throws IOException {
        //Texto que será gravado no cabeçalho do arquivo
        String[] cabecalho = {"dataHora,", "tipo", "mensagem", "duracao"};

        //Definição da data e hora da criação do arquivo de log para concatenar com o nome do arquivo
        String dataHoraInicial = new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime());
        System.out.println("Data e hota: " + dataHoraInicial);
        Writer writer = Files.newBufferedWriter(Paths.get("target/logs/userDD - " + dataHoraInicial + ".csv"));

        //Prepara a escrita no arquivo
        CSVWriter csvWriter = new CSVWriter(writer);

        //Escreve no arquivo
        csvWriter.writeNext(cabecalho, false);

        //Salva o arquivo
        csvWriter.flush();

        //Fechar o arquivo
        csvWriter.close();
        writer.close();

    }

    public void logar(String dataHora, String tipo, String mensagem, int duracao) {

    }

    public void finalizarLog() {

    }

}
