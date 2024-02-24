package com.springboot.restapi.controller;

import com.springboot.restapi.bean.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    // http://localhost:8080/product
    @GetMapping("product")
    public ResponseEntity<Product> getProduct() {
        Product product = new Product();
        product.setProdId(1);
        product.setProdName("Mobile");
        product.setProdConst(1222);
//        return new ResponseEntity<>(product, HttpStatus.OK);
//        return ResponseEntity.ok(product);
        return ResponseEntity.
                ok().
                header("custom-header", "Deva").
                body(product);
    }

    // http://localhost:8080/productlist
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> productList = new ArrayList();
        productList.add(new Product(1, "Mobile", 123));
        productList.add(new Product(2, "Laptop", 400));
        productList.add(new Product(3, "Desktop", 300));
        return ResponseEntity.ok(productList);
    }

    // @Pathvariable
    //http://localhost:8080/product/12/HP/1200
    @GetMapping("{id}/{prod-name}/{prod-cost}")
    public Product getSingleProduct(@PathVariable("id") int proId,
                                    @PathVariable("prod-name") String prodName,
                                    @PathVariable("prod-cost") int prodCost) {
        return new Product(proId, prodName, prodCost);
    }

    // @ Spring boot rest api request params
    // http://localhost:8080/product/query?proId=111&productName=JJJ&prodCost=400
    @GetMapping("query")
    public Product getProductWithQueryParams(@RequestParam int proId,
                                             @RequestParam String productName,
                                             @RequestParam int prodCost) {
        return new Product(proId, productName, prodCost);
    }

    // Spring boot rest api @Requestbody and @Postmapping
    @PostMapping("create")
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product) {
        System.out.println(product.getProdConst());
        System.out.println(product.getProdId());
        System.out.println(product.getProdName());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    // Spring boot rest api @Requestbody and @Putmapping

    @PutMapping("{id}/update")
    public Product updateProduct(@RequestBody Product product
            , @PathVariable int id) {
        product.setProdId(id);
        System.out.println(product.getProdName());
        System.out.println(product.getProdConst());
        System.out.println(id);
        return product;
    }

    @DeleteMapping("{id}/delete")
    public Product deleteProduct(@RequestBody Product product
            , @PathVariable int id) {
        product.setProdId(id);
        System.out.println(product.getProdName());
        System.out.println(product.getProdConst());
        System.out.println(id);
        return product;
    }
}
