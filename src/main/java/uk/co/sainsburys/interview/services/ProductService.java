package uk.co.sainsburys.interview.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.sainsburys.interview.client.ProductsClient;
import uk.co.sainsburys.interview.client.model.Product;
import uk.co.sainsburys.interview.client.model.ProductPrice;
import uk.co.sainsburys.interview.client.model.UnifiedProduct;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductService {

    @Autowired
    private ProductsClient productsClient;

    public Set<UnifiedProduct> getUnifiedProductData() {
        Set<Product> products = productsClient.getProducts();
        Set<ProductPrice> prices = productsClient.getProductsPrices();

        return products.stream()
                .filter(this::isValidProduct)
                .map(product -> {

                    ProductPrice price = prices.stream()
                            .filter(this::isValidPrice)
                            .filter(p -> Integer.valueOf(p.productUid()).equals(product.productUid()))  // Match price by productUid
                            .findFirst()
                            .orElse(null);

                    assert price != null;

                    return new UnifiedProduct(
                            product.productUid(),
                            product.productType(),
                            product.name(),
                            product.fullUrl(),
                            price.unitPrice(),
                            price.unitPriceMeasure(),
                            price.unitPriceMeasureAmount()
                    );
                })
                .collect(Collectors.toSet());
    }


    private boolean isValidProduct(Product product) {
        // Expected fields in Product
        Set<String> expectedFields = Set.of(
                "productUid",
                "productType",
                "name",
                "fullUrl"
        );


        return hasValidFields(product, expectedFields);
    }



    private boolean isValidPrice(ProductPrice price) {

        Set<String> expectedFields = Set.of(
                "productUid",
                "unitPrice",
                "unitPriceMeasure",
                "unitPriceMeasureAmount"
        );


        return hasValidFields(price, expectedFields);
    }



    private boolean hasValidFields(Object object, Set<String> expectedFields) {

        Field[] fields = object.getClass().getDeclaredFields();


        for (Field field : fields) {
            if (!expectedFields.contains(field.getName())) {
                return false;
            }
        }

        return true;
    }
}
