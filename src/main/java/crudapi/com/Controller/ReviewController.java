package crudapi.com.Controller;

import crudapi.com.Entity.Review;
import crudapi.com.Services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{productId}/reviews")
public class ReviewController {

    private final ProductService productService;

    public ReviewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Review> getCommentsForTask(@PathVariable Long productId) {
        return productService.getReviewsForProduct(productId);
    }

    @PostMapping
    public Review addCommentToTask(@PathVariable Long productId, @RequestBody Review review) {
        return productService.addReviewToProduct(productId, review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long productId, @PathVariable Long reviewId) {
        productService.deleteReview(productId, reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }

}
