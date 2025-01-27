package uk.co.sainsburys.interview.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import uk.co.sainsburys.interview.client.model.Product;
import uk.co.sainsburys.interview.client.model.ProductPrice;

import java.util.Set;

@Component
public class ProductsClient {

    private static final String PRODUCTS_URL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_v2.json";
    private static final String PRODUCTS_PRICE_URL = "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/sainsbury_products/products_price_v2.json";

    private final WebClient webClient;

    @Autowired
    public ProductsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Set<Product> getProducts() {
        return webClient.get()
                .uri(PRODUCTS_URL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<Product>>() {})
                .log()
                .block();
    }

    public Set<ProductPrice> getProductsPrices() {
        return webClient.get()
                .uri(PRODUCTS_PRICE_URL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<ProductPrice>>() {})
                .log()
                .block();
    }
}
