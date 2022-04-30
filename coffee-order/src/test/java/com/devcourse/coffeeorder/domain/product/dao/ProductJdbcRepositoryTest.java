package com.devcourse.coffeeorder.domain.product.dao;

import static com.devcourse.coffeeorder.TestData.product;
import static com.devcourse.coffeeorder.TestData.product2;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {
    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig config = aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test_coffee_order", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository productRepository;



    @Test
    @Order(1)
    @DisplayName("제품 생성 및 체조회 테스트")
    void testProductCreation() {
        productRepository.create(product);
        productRepository.create(product2);

        List<Product> products = productRepository.findAll();

        assertThat(products.size(), is(2));
    }

    @Test
    @Order(2)
    @DisplayName("id에 의한 조회 확인")
    void testFindById() {
        Optional<Product> retrieved = productRepository.findById(product.getProductId());

        assertThat(retrieved.isPresent(), is(true));
        assertThat(retrieved.get(), samePropertyValuesAs(product));
    }

    @Test
    @Order(3)
    @DisplayName("id에 의해 아무것도 찾을 수 없는 경우 테스트")
    void testFindByNotExistId() {
        Optional<Product> retrieved = productRepository.findById(UUID.randomUUID());
        assertThat(retrieved.isEmpty(), is(true));
    }

    @Test
    @Order(4)
    @DisplayName("카테고리에 의한 조회 확인")
    void testFindByCategory() {
        List<Product> products = productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(products.size(), is(2));
    }

    @Test
    @Order(5)
    @DisplayName("수정 테스트")
    void testUpdate() {
        Optional<Product> retrieved = productRepository.findById(product.getProductId());
        Product retrievedProduct = retrieved.get();

        retrievedProduct.updateProduct(retrievedProduct.getProductName(), retrievedProduct.getCategory(), 5000, retrievedProduct.getDescription());
        productRepository.update(retrievedProduct);

        retrieved = productRepository.findById(product.getProductId());
        assertThat(retrieved.get().getPrice(), is(5000L));
        assertThat(retrieved.get().getUpdatedAt(), not(retrieved.get().getCreatedAt()));
    }

    @Test
    @Order(6)
    @DisplayName("삭제 테스트")
    void testDelete() {
        productRepository.delete(product);
        productRepository.delete(product2);

        List<Product> products = productRepository.findAll();

        assertThat(products.size(), is(0));
    }
}