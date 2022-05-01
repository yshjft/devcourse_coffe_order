package com.devcourse.coffeeorder.domain.order.dao.order;

import static com.devcourse.coffeeorder.TestData.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
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
class OrderJdbcRepositoryTest {
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

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("주문 생성 및 조회")
    void testOrderCreation() {
        orderRepository.create(order);

        List<Order> orders = orderRepository.findAll();

        assertThat(orders.size(), is(1));
        assertThat(orders.get(0), samePropertyValuesAs(order));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("주문 상태에 따른 조회 테스트")
    void testGetOrderByStatus() {
        orderRepository.create(order2);

        List<Order> orders = orderRepository.findByStatus(OrderStatus.ORDER_ACCEPTED);
        assertThat(orders.size(), is(2));

        orders = orderRepository.findByStatus(OrderStatus.ORDER_CANCELLED);
        assertThat(orders.size(), is(0));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("주문 접수 -> 주문 준비 중 by time")
    void testUpdateOrderStatusByTime() {
        LocalDateTime testTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 50, 0, 0));
        orderRepository.orderAcceptedToPreparingForShipment(testTime);

        List<Order> orders = orderRepository.findByStatus(OrderStatus.PREPARING_FOR_SHIPMENT);
        assertThat(orders.size(), is(2));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("주문 상태 변경 by ID")
    void testUpdateOrderStatusById() {
        orderRepository.updateStatusById(OrderStatus.ORDER_ACCEPTED, order.getOrderId());

        List<Order> orders = orderRepository.findByStatus(OrderStatus.ORDER_ACCEPTED);
        assertThat(orders.size(), is(1));
    }

}