package mylab.order.di.xml;

import java.util.List;

/**
 * ShoppingCart(쇼핑 카트) 도메인 객체.
 *
 * - 스프링 컨테이너가 관리할 POJO.
 * - XML 설정에서 products(List<Product>) 컬렉션을 **setter 주입**으로 받습니다.
 *   예)
 *   <bean id="shoppingCart" class="mylab.order.di.xml.ShoppingCart">
 *     <property name="products">
 *       <list>
 *         <ref bean="product1"/>
 *         <ref bean="product2"/>
 *       </list>
 *     </property>
 *   </bean>
 *
 * 학습 포인트
 * - <property name="products"> 는 setProducts(List<Product>) 를 호출합니다.
 * - <list> 내부의 <ref bean="..."> 들이 리스트 요소로 주입됩니다.
 * - 총액 계산은 단순 예제이므로 double 사용(실무는 BigDecimal 권장).
 */
public class ShoppingCart {

    // ──────────────────────────────────────────────────────────────────────────
    // 1) 필드(상태)
    //    - 카트에 담긴 상품 목록. 스프링이 XML로 주입합니다.
    //    - 제네릭 타입을 명시해 컴파일 타임에 타입 안전성 확보(List<Product>).
    // ──────────────────────────────────────────────────────────────────────────
    private List<Product> products;


    // ──────────────────────────────────────────────────────────────────────────
    // 2) 생성자
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * 기본 생성자.
     *
     * - setter 주입을 사용할 것이므로 반드시 필요합니다.
     *   스프링은 기본 생성자로 객체 생성 → setProducts(...) 순서로 값을 넣습니다.
     */
    public ShoppingCart() {}


    // ──────────────────────────────────────────────────────────────────────────
    // 3) Getter / Setter
    //    - XML의 <property name="products"> 와 정확히 이름이 매칭되어야 합니다.
    // ──────────────────────────────────────────────────────────────────────────

    /** @return 카트에 담긴 상품 목록(스프링이 주입) */
    public List<Product> getProducts() { return products; }

    /**
     * @param products 스프링 XML에서 <property name="products"> 로 전달되는 리스트
     *                 (내부적으로는 <list> + <ref bean="..."> 형태)
     */
    public void setProducts(List<Product> products) { this.products = products; }


    // ──────────────────────────────────────────────────────────────────────────
    // 4) 비즈니스 메서드
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * 카트에 담긴 상품들의 총 금액을 계산합니다.
     *
     * 구현 노트:
     * - NPE 방지를 위해 products가 null인 경우 0.0 반환.
     * - 단순 반복문으로 합산(성능/가독성 균형). 스트림 사용도 가능:
     *   return products == null ? 0.0 : products.stream().mapToDouble(Product::getPrice).sum();
     */
    public double getTotalPrice() {
        double total = 0.0;
        if (products != null) {
            for (Product p : products) {
                total += p.getPrice(); // 각 상품 가격 합산
            }
        }
        return total;
    }


    // ──────────────────────────────────────────────────────────────────────────
    // 5) toString
    //    - 디버깅/로그 시 객체 상태를 읽기 쉽게 출력.
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "ShoppingCart[products=" + products + "]";
    }

    // ──────────────────────────────────────────────────────────────────────────
    // (참고) 확장 아이디어
    // - addProduct/removeProduct 메서드를 제공하면 XML 외 런타임 조작도 가능.
    // - 외부 수정 방지를 위해 getProducts()가 방어적 복사/불변 리스트를 반환하도록 변경 가능.
    // ──────────────────────────────────────────────────────────────────────────
}
