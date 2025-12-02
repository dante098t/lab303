package com.hoangtan2712.lab304m;
import java.util.List;
import com.hoangtan2712.lab304m.Product;
public interface ProductService {
Product createProduct(Product product);
Product getProductById(Long productId);
List<Product> getAllProducts();
Product updateProduct(Product product);
void deleteProduct(Long productId);
}
