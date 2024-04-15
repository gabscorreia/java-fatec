import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ProdutosEmPromocao {

    public static void main(String[] args) throws IOException, InterruptedException {
  
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.escuelajs.co/api/v1/products/"))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
    
            List<Produto> produtos = parseJson(response.body());

            List<Produto> produtosEmPromocao = filtrarProdutosEmPromocao(produtos);
            System.out.println("Produtos Imperdiveis em Promoção");
            for (Produto produto : produtosEmPromocao) {
                System.out.println(produto.getNome().toUpperCase());
            }
        } else {
            System.out.println("Erro ao consumir a API da empresa: " + response.statusCode());
        }
    }

    private static List<Produto> parseJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<Produto>>() {});
    }

    private static List<Produto> filtrarProdutosEmPromocao(List<Produto> produtos) {
        List<Produto> produtosEmPromocao = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getPreco() < 30.00) {
                produtosEmPromocao.add(produto);
            }
        }
        return produtosEmPromocao;
    }
}

class Produto {
    private String nome;
    private Double preco;

}
