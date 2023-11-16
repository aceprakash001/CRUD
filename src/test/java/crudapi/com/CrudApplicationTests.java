package crudapi.com;

import crudapi.com.Entity.Product;
import crudapi.com.Entity.Review;
import crudapi.com.Repository.ProductRepository;
import crudapi.com.Repository.ReviewRepository;
import crudapi.com.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@RunWith(SprigRunner.class)
@SpringBootTest
class CrudApplicationTests {
}


