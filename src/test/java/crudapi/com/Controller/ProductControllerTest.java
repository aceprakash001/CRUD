package crudapi.com.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crudapi.com.Entity.Product;
import crudapi.com.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllProductsTest() throws Exception {
        List<Product> mockProductList = Arrays.asList(new Product("Bed", "Double bed and comfy"), new Product("Coat", "Woolen Coat"), new Product("Bag", "Laptop Bag"));
        when(productService.getAllProducts()).thenReturn(mockProductList);
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Bed"));
    }

    @Test
    public void getProductByIdTest() throws Exception {
        Long productId = 500L;
        Product mockProduct = new Product("Bed", "Double bed and comfy");
        when(productService.getProductById(productId)).thenReturn(mockProduct);
        mockMvc.perform(get("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Bed"));
    }

    @Test
    public void createProductTest() throws Exception {
        Product mockProduct = new Product("Bed", "Double bed and comfy");
        when(productService.createProduct(mockProduct)).thenReturn(mockProduct);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockProduct)))
                .andExpect(status().isOk());

        verify(productService).createProduct(any());
    }

    @Test
    public void deleteProductTest() throws Exception {
        Long productId = 120L;
        Product mockProduct = new Product("Bed", "Double bed and comfy");
        doNothing().when(productService).deleteProduct(productId);
        mockMvc.perform(delete("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductTest() throws Exception {
        Long productId = 100L;
        Product existingProduct = new Product("Bed", "Double bed and comfy");
        Product updatedProductDetails = new Product("Sofa", "Big sofa");
        updatedProductDetails.setId(productId);

        when(productService.getProductById(eq(productId))).thenReturn(existingProduct);
        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(updatedProductDetails);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProductDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedProductDetails.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedProductDetails.getDescription()));

        verify(productService, times(1)).updateProduct(eq(productId), any(Product.class));
    }
}
