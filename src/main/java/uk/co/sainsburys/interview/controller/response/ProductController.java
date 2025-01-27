package uk.co.sainsburys.interview.controller.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.sainsburys.interview.client.model.UnifiedProduct;
import uk.co.sainsburys.interview.services.ProductService;


import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Set<UnifiedProduct>> getProducts() {
        Set<UnifiedProduct> products = productService.getUnifiedProductData();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

