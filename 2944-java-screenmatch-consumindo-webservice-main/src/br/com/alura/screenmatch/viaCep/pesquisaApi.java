package br.com.alura.screenmatch.viaCep;

import java.io.IOException;
import java.net.URI;
// import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;



public class pesquisaApi {

    public static void main(String[] args) throws IOException, InterruptedException {
        // https://viacep.com.br/ws/01001000/json/

        Scanner leitura = new Scanner(System.in);
        String busca = "";

        Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

        System.out.println("Digite o CEP");
        busca = leitura.nextLine();

        String endereco = "https://viacep.com.br/ws/" + busca.replace(" ", "+") + "/json/";

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create
                (endereco))
                .build();

        HttpResponse<String> response = cliente
                .send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.body());
        leitura.close();

        String json = response.body();
        System.out.println("Resposta da API: " + json);  
        
        

        FileWriter escrita = new FileWriter("endereco.json");
        escrita.write(gson.toJson(json));
        escrita.close();


        System.out.println("Programa finalizado");


    }

}
