package com.devcourse.coffeeorder.domain.order.dao.orderitem;

import com.devcourse.coffeeorder.domain.order.dao.order.OrderRepository;
import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.domain.product.dao.ProductRepository;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.devcourse.coffeeorder.TestData.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderItemJdbcRepositoryTest {
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
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    @Test
    @Order(1)
    @DisplayName("주문 상품 생성 및 조회")
    void testCreatAndFindAll() {
        productRepository.create(coffee);
        productRepository.create(cookie);

        orderRepository.create(order);

        orderItemRepository.create(orderItem1);
        orderItemRepository.create(orderItem2);

        List<OrderItem> orderItems = orderItemRepository.findAll();

        assertThat(orderItems.size(), is(2));
        assertThat(orderItems.get(0).getOrderItemId(), is(1L));
    }

    @Test
    @Order(2)
    @DisplayName("주문 상품 JOIN 조회(product)")
    void testFindByOrderIdWithProduct() {
        List<OrderItem> orderItems = orderItemRepository.findByOrderIdWithProduct(order.getOrderId());

        assertThat(orderItems.get(0).getOrderItemId(), is(1L));
        assertThat(orderItems.get(0).getProduct().getCategory(), is(Category.COFFEE_BEAN_PACKAGE));
        assertThat(orderItems.size(), is(2));
    }

    @Test
    @Order(3)
    @DisplayName("주문 상품 JOIN 조회(order)")
    void testFindByOrderItemIdWithOrder() {
//        orderItemRepository.create(orderItem3);

        OrderItem orderItem = orderItemRepository.findByOrderItemIdWithOrder(2L).get();

        assertThat(orderItem.getOrder(), samePropertyValuesAs(order));
    }

    @Test
    @Order(4)
    @DisplayName("주문 상품 수량 수정")
    void testUpdate() {
        OrderItem orderItem = orderItemRepository.findByOrderItemIdWithOrder(2L).get();
        orderItem.updateQuantity(10);

        orderItemRepository.update(orderItem);
        orderItem = orderItemRepository.findByOrderItemIdWithOrder(2L).get();

        assertThat(orderItem.getQuantity(), is(10));
    }

    @Test
    @Order(5)
    @DisplayName("주문 상품 삭제")
    void testDelete() {
        OrderItem orderItem = orderItemRepository.findByOrderItemIdWithOrder(2L).get();
        orderItemRepository.delete(orderItem);

        Optional<OrderItem> retrievedOrderItem = orderItemRepository.findByOrderItemIdWithOrder(2L);

        assertThat(retrievedOrderItem, is(Optional.empty()));
    }
}