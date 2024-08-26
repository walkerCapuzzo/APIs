package br.com.alura.screenmatch.viaCep;

import br.com.alura.screenmatch.modelos.EnderecoRecord;

import java.io.IOException;
import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class consultaCep {
    public EnderecoRecord buscaEndereco(String cep) {

        URI endereco = URI.create("https://viacep.com.br/ws/" + cep + "/json/");

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endereco)
                .build();

        try{
            HttpResponse<String> response = cliente
            .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), EnderecoRecord.class);

        } catch(Exception e) {
            throw new RuntimeException("Não consegui obter o endereço" + e);
        }
    

    }

}