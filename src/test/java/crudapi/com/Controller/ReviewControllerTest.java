package crudapi.com.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crudapi.com.Entity.Product;
import crudapi.com.Entity.Review;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ReviewController reviewController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getReviewsForTaskTest() throws Exception {
        Long productId = 53L;
        List<Review> mockReview = Arrays.asList(
                new Review("The bed is good. 10/10", new Product("Bed", "Double Bed and Comfy")),
                new Review("Nice product", new Product("Mobile", "Snapdragon 8 gen +1 , 5G , 8/246GB"))
        );

        when(productService.getReviewsForProduct(anyLong())).thenReturn(mockReview);

        mockMvc.perform(get("/products/{productId}/reviews", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].review").value("The bed is good. 10/10"))
                .andExpect(jsonPath("$[1].review").value("Nice product"));
    }

    @Test
    public void addReviewsToTaskTest() throws Exception {
        Long productId = 78L;
        Review newReview = new Review("Nice product", new Product("Mobile", "Snapdragon 8 gen +1 , 5G , 8/246GB"));
        Review savedReview = new Review("Nice product", new Product("Mobile", "Snapdragon 8 gen +1 , 5G , 8/246GB"));

        when(productService.addReviewToProduct(anyLong(), any(Review.class))).thenReturn(savedReview);

        mockMvc.perform(post("/products/{productId}/reviews", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newReview)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.review").value("Nice product"));

        verify(productService, times(1)).addReviewToProduct(eq(productId), any(Review.class));
    }

    @Test
    public void deleteReviewTest() throws Exception {
        Long productId = 85L;
        Long reviewId = 100L;

        doNothing().when(productService).deleteReview(productId,reviewId);

        mockMvc.perform(delete("/products/{productId}/reviews/{reviewId}",productId,reviewId))
                .andExpect(status().isOk())
                .andExpect(content().string("Review deleted successfully"));

        verify(productService,times(1)).deleteReview(eq(productId),eq(reviewId));
    }
}