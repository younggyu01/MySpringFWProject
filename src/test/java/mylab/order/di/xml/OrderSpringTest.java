package mylab.order.di.xml;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * OrderSpringTest
 *
 * - 스프링의 테스트 컨텍스트 프레임워크를 이용해 XML(ApplicationContext)을 로드하고,
 *   그 컨텍스트에서 관리되는 빈(ShoppingCart, OrderService)을 주입 받아 검증한다.
 *
 * 핵심 포인트
 * 1) @ExtendWith(SpringExtension.class)
 *    - JUnit 5(Jupiter) 환경에서 스프링 테스트 기능을 활성화한다.
 *
 * 2) @ContextConfiguration(locations = "classpath:mylab-order-di.xml")
 *    - 테스트 실행 시 클래스패스에 있는 mylab-order-di.xml 을 읽어
 *      ApplicationContext를 구성한다.
 *
 * 3) @Autowired
 *    - 컨텍스트에 등록된 빈을 필드 주입한다.
 *
 * 4) 콘솔 출력(System.out.println)
 *    - 과제 요구사항을 눈으로 확인할 수 있도록 상태를 상세히 출력한다.
 *    - 테스트는 ‘조용히 성공’하는 것이 정상이라 출력은 선택 사항이지만,
 *      학습과 제출 확인용으로 넣어두었다.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:mylab-order-di.xml")
class OrderSpringTest {

    /** XML에서 정의한 id="shoppingCart" 빈이 주입된다. */
    @Autowired private ShoppingCart shoppingCart;

    /** XML에서 정의한 id="orderService" 빈이 주입된다. */
    @Autowired private OrderService orderService;

    /**
     * ShoppingCart 빈이 정상 주입되었는지 + 내부 products 리스트가
     * 과제 요구사항대로 구성되었는지 검증한다.
     *
     * 검증 항목
     * - shoppingCart != null
     * - products != null
     * - products.size() == 2
     * - products[0].name == "노트북"
     * - products[1].name == "스마트폰"
     */
    @Test
    @DisplayName("ShoppingCart 빈 주입 & 내용 검증 (콘솔 출력 포함)")
    void shoppingCartBeanInjectedAndContent_withConsole() {
        System.out.println("\n=== [ShoppingCart 빈 주입 & 내용 검증] ===");

        // 1) 빈 주입 여부
        System.out.println("• shoppingCart != null  -> " + (shoppingCart != null));
        assertNotNull(shoppingCart);

        // 2) 제품 리스트 주입 여부
        List<Product> products = shoppingCart.getProducts();
        System.out.println("• products != null      -> " + (products != null));
        assertNotNull(products);

        // 3) 제품 개수
        System.out.println("• products.size()       -> " + products.size() + " (기대: 2)");
        assertEquals(2, products.size());

        // 4) 제품명 순서/값 검증
        System.out.println("• products[0].name      -> " + products.get(0).getName() + " (기대: 노트북)");
        assertEquals("노트북", products.get(0).getName());

        System.out.println("• products[1].name      -> " + products.get(1).getName() + " (기대: 스마트폰)");
        assertEquals("스마트폰", products.get(1).getName());

        // 5) 목록 전체 출력(제출 확인용)
        System.out.println("• products 전체:");
        for (Product p : products) {
            System.out.printf("   - %s / %s / %.0f%n", p.getId(), p.getName(), p.getPrice());
        }
    }

    /**
     * OrderService 빈이 정상 주입되었는지 + 내부에 ShoppingCart가 연결되어 있는지,
     * 그리고 총액 계산이 정확한지 검증한다.
     *
     * 검증 항목
     * - orderService != null
     * - orderService.getShoppingCart() != null
     * - calculateOrderTotal() == 150000 + 800000
     */
    @Test
    @DisplayName("OrderService 스프링 빈 및 총액 계산 검증 (콘솔 출력 포함)")
    void orderServiceBeanAndTotal_withConsole() {
        System.out.println("\n=== [OrderService 총액 계산 검증] ===");

        // 1) 서비스 빈 주입 여부
        System.out.println("• orderService != null  -> " + (orderService != null));
        assertNotNull(orderService);

        // 2) Cart 의존성 주입 여부
        System.out.println("• orderService.shoppingCart != null -> " + (orderService.getShoppingCart() != null));
        assertNotNull(orderService.getShoppingCart());

        // 3) 총액 계산 정확성
        double total = orderService.calculateOrderTotal();
        System.out.println("• calculateOrderTotal() -> " + total + " (기대: 950000.0)");
        assertEquals(150000 + 800000, total, 1e-6);
    }
}
