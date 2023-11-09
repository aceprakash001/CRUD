package crudapi.com.Services;

import crudapi.com.Entity.Product;
import crudapi.com.Entity.Review;
import crudapi.com.Repository.ProductRepository;
import crudapi.com.Repository.ReviewRepository;
import crudapi.com.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private  final ProductRepository productRepository;
    private  final ReviewRepository reviewRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> productDetails = productRepository.findAll();
        System.out.println("Getting data: " + productDetails);
        return productDetails;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setTitle(productDetails.getTitle());
        product.setDescription(productDetails.getDescription());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Review> getReviewsForProduct(Long productId) {
        Product product = getProductById(productId);
        return product.getReviews();
    }

    public Review addReviewToProduct(Long productId, Review review) {
        Product product = getProductById(productId);
        review.setProduct(product);
        return reviewRepository.save(review);
    }

    public void deleteReview(Long productId, Long reviewId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "+ productId));

        Review reviewToDelete = product.getReviews().stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);

        if (reviewToDelete != null) {
            product.getReviews().remove(reviewToDelete);
            productRepository.save(product);
            reviewRepository.delete(reviewToDelete);
        } else {
            throw new ResourceNotFoundException("Review not found with ID: " + reviewId);
        }
    }
}
