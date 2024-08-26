package br.com.alura.screenmatch.viaCep;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.screenmatch.modelos.EnderecoRecord;
import java.io.FileWriter;
import java.io.IOException;


public class GeradorDeArquivo {

    public void salvaJson(EnderecoRecord endereco) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escrita = new FileWriter(endereco.cep() + ".json");
        escrita.write(gson.toJson(endereco));
        escrita.close();
    }
}
