package crudapi.com;

import crudapi.com.Entity.Product;
import crudapi.com.Repository.ProductRepository;
import crudapi.com.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

//@RunWith(SprigRunner.class)
@SpringBootTest
class CrudApplicationTests {


    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void getAllProductsTest() {
        when(productRepository.findAll())
                .thenReturn(Stream.of(new Product("Bed", "Double bed and comfy"), new Product("Coat", "Woolen Coat"), new Product("Bag", "Laptop Bag")).collect(Collectors.toList()));
        assertEquals(3, productService.getAllProducts().size());
    }

    @Test
    public void getProductByIdTest() {
        Long id = 211L;
        when(productRepository.findById(id))
                .thenReturn(Optional.of(new Product("Bed", "Double bed and comfy")));
        Product product = productService.getProductById(id);
        assertNotNull(product);
        assertEquals("Bed", product.getTitle());
        assertEquals("Double bed and comfy", product.getDescription());
    }

    @Test
    public void createProductTest() {
        Product product = new Product("Monitor", "27' Full HD Display");
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(product, productService.createProduct(product));
    }

    @Test
    public void updateProductTest() {
        Long id = 45L;
        Product existingProduct = new Product("Pen", "Black Pen");
        Product productDetails = new Product("Ball Pen", "Blue pen");

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);
        Product updatedProduct = productService.updateProduct(id, productDetails);

        assertNotNull(updatedProduct);
        assertEquals(productDetails.getTitle(), updatedProduct.getTitle());
        assertEquals(productDetails.getDescription(), updatedProduct.getDescription());

        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void deleteProductTest() {
        Long id = 37L;
        productService.deleteProduct(id);
        verify(productRepository, times(1)).deleteById(id);
    }
}

