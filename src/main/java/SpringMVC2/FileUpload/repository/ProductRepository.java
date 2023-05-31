package SpringMVC2.FileUpload.repository;

import SpringMVC2.FileUpload.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {

    private final Map<Long, Product> store = new HashMap<>();
    private long sequence = 0L;


    public Product save(Product product) {
        product.setId(++sequence);
        store.put(product.getId(), product);
        return product;
    }

    public Product findById(Long id) {
        return store.get(id);
    }
}
