package br.com.alura.screenmatch.viaCep;

import br.com.alura.screenmatch.modelos.EnderecoRecord;

import java.io.IOException;
import java.util.Scanner;

public class principalCep {

    public static void main(String[] args) {
        consultaCep consultaCep = new consultaCep();

        try {

            Scanner leitura = new Scanner(System.in);
            String busca = "";
            System.out.println("Digite o CEP");
            busca = leitura.nextLine();

            EnderecoRecord novoEndereco = consultaCep.buscaEndereco(busca);
            leitura.close();
            System.out.println(novoEndereco);

            GeradorDeArquivo gerador = new GeradorDeArquivo();
            gerador.salvaJson(novoEndereco);

        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Finalizar a aplicação");
        }
    }

}
