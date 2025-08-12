package mylab.order.di.xml;

/**
 * OrderService(주문 서비스).
 *
 * - 비즈니스 책임: "장바구니 총 주문 금액 계산" 기능을 제공.
 * - 스프링 컨테이너가 관리하는 POJO이며, ShoppingCart에 **의존**한다.
 * - 이 의존성은 XML에서 **setter 주입**으로 연결한다.
 *
 *   예) mylab-order-di.xml
 *   <bean id="orderService" class="mylab.order.di.xml.OrderService">
 *     <property name="shoppingCart" ref="shoppingCart"/>
 *   </bean>
 *
 * 학습 포인트
 * - <property name="shoppingCart"> 는 setShoppingCart(...) 를 호출한다.
 * - 서비스는 “무엇을” 할지에 집중하고, “데이터 보유/합산”은 Cart가 맡는다(단일 책임).
 * - 테스트는 스프링 컨텍스트로 빈을 주입받아 calculateOrderTotal()을 검증한다.
 */
public class OrderService {

    // ──────────────────────────────────────────────────────────────────────────
    // 1) 필드(의존성)
    //    - 이 서비스가 작업을 수행하기 위해 필요한 협력 객체(ShoppingCart).
    //    - 스프링이 XML 설정을 통해 주입한다.
    // ──────────────────────────────────────────────────────────────────────────
    private ShoppingCart shoppingCart;


    // ──────────────────────────────────────────────────────────────────────────
    // 2) 생성자
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * 기본 생성자.
     *
     * - setter 주입을 사용할 것이므로 기본 생성자가 필요하다.
     * - (참고) 생성자 주입을 쓰고 싶다면
     *   public OrderService(ShoppingCart cart) { this.shoppingCart = cart; }
     *   를 추가하고 XML에 <constructor-arg ref="shoppingCart"/> 를 사용한다.
     */
    public OrderService() {}


    // ──────────────────────────────────────────────────────────────────────────
    // 3) Getter / Setter
    //    - XML의 <property name="shoppingCart"> 와 정확히 이름이 매칭되어야 한다.
    // ──────────────────────────────────────────────────────────────────────────

    /** @return 주입된 ShoppingCart */
    public ShoppingCart getShoppingCart() { return shoppingCart; }

    /**
     * @param shoppingCart 스프링 XML에서 주입되는 장바구니 빈
     */
    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }


    // ──────────────────────────────────────────────────────────────────────────
    // 4) 비즈니스 메서드
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * 주문 총액을 계산한다.
     *
     * 구현 노트:
     * - 실제 합산 로직은 ShoppingCart#getTotalPrice() 가 담당(책임 분리).
     * - 과제에서는 NPE가 발생하지 않지만, 실무에선 null 가드를 둘 수도 있다.
     *   예) return (shoppingCart == null) ? 0.0 : shoppingCart.getTotalPrice();
     */
    public double calculateOrderTotal() {
        return shoppingCart.getTotalPrice();
    }


    // ──────────────────────────────────────────────────────────────────────────
    // 5) toString
    //    - 디버깅/로그 시 주입 상태를 확인하기 좋다.
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "OrderService[shoppingCart=" + shoppingCart + "]";
    }

    // ──────────────────────────────────────────────────────────────────────────
    // (참고) 확장 아이디어
    // - 할인/쿠폰/배송비 정책을 전략(Strategy) 인터페이스로 분리해 주입받기.
    // - 트랜잭션 처리, 로깅(AOP), 검증 등을 스프링 기능과 결합해 확장.
    // ──────────────────────────────────────────────────────────────────────────
}
