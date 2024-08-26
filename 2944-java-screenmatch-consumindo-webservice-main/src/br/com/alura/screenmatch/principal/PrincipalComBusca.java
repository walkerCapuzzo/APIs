package br.com.alura.screenmatch.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;



public class PrincipalComBusca {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner leitura = new Scanner(System.in);
        String busca = "";
        List<Titulo> titulos = new ArrayList<>();

        Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setPrettyPrinting()
        .create();

        while (!busca.equalsIgnoreCase("sair")) {

            System.out.println("Digite o nome do filme ou sair para encerrar");
            busca = leitura.nextLine();
            if (busca.equalsIgnoreCase("sair")) {
                break;
            }

            String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=bf594df1";
            // String endereco = "https://viacep.com.br/ws/" + busca.replace(" ", "+") + "/json/";

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                // System.out.println("Resposta da API: " + json);

                if (json.startsWith("{")) {
                   
                    TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                    // System.out.println("Titulo MDB: " + meuTituloOmdb);

                    Titulo meuTitulo = new Titulo(meuTituloOmdb);
                    // System.out.println("Titulo Convertido ");
                    // System.out.println(meuTitulo);

                    titulos.add(meuTitulo);
                    
                } else {
                    System.out.println("Erro na resposta da API: " + json);
                }

            } catch (JsonSyntaxException e) {
                System.out.println("Erro de sintaxe no JSON: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Aconteceu um erro: " + e.getMessage());
            } catch (ErroDeConversaoDeAnoException e) {
                System.out.println(e.getMessage());
            }
        }
        leitura.close();

        System.out.println(titulos);

        FileWriter escrita = new FileWriter("filmes.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();

        System.out.println("Programa finalizado");
    }
}
