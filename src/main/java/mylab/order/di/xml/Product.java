package mylab.order.di.xml;

/**
 * Product(상품) 도메인 객체.
 *
 * - 이 클래스는 스프링 컨테이너가 관리할 평범한 자바 객체(POJO)입니다.
 * - XML 설정에서 두 가지 방식으로 주입되도록 설계됨:
 *   1) setter 주입: 기본 생성자 호출 후 setId/setName/setPrice 로 값 주입
 *   2) 생성자 주입: (id, name, price)를 받는 생성자를 통해 한 번에 값 주입
 *
 * 학습 포인트
 * - Spring XML에서 <property name="..."> 는 대응되는 set...() 메서드를 찾아 호출합니다.
 * - <constructor-arg> 는 파라미터의 순서/타입에 맞춰 생성자를 호출합니다.
 * - 금액은 예제라서 double을 사용했지만, 실무에서는 오차를 피하기 위해 BigDecimal을 권장합니다.
 */
public class Product {

    // ──────────────────────────────────────────────────────────────────────────
    // 1) 필드(상태)
    //    - 스프링이 값을 주입해 관리할 대상들
    // ──────────────────────────────────────────────────────────────────────────
    /** 상품 ID (예: "P001") */
    private String id;

    /** 상품 이름 (예: "노트북") */
    private String name;

    /** 상품 가격 (예: 150000). 예제 단순화를 위해 double 사용 */
    private double price;


    // ──────────────────────────────────────────────────────────────────────────
    // 2) 생성자
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * 기본 생성자 (파라미터 없음).
     *
     * 왜 필요할까?
     * - "setter 주입"을 사용할 때 스프링은
     *   1) 먼저 기본 생성자로 객체를 만든 뒤
     *   2) XML의 <property>에 지정된 값으로 setXxx()를 호출해 값을 채웁니다.
     * - 기본 생성자가 없으면 setter 주입을 사용할 수 없습니다.
     */
    public Product() {
        // 비어 있어도 됩니다. 스프링이 리플렉션으로 호출합니다.
    }

    /**
     * 모든 필드를 한 번에 초기화하는 생성자.
     *
     * 언제 쓰일까?
     * - "생성자 주입"을 사용할 때 XML의 <constructor-arg>가
     *   이 시그니처와 매칭되어 호출됩니다.
     * - 예) <constructor-arg value="P002"/> <constructor-arg value="스마트폰"/> <constructor-arg value="800000"/>
     */
    public Product(String id, String name, double price) {
        // this.id = id 등은 필드와 파라미터 이름이 같을 때 구분하려고 사용
        this.id = id;
        this.name = name;
        this.price = price;
    }


    // ──────────────────────────────────────────────────────────────────────────
    // 3) Getter / Setter
    //    - 스프링의 <property name="id" value="..."/> 는 setId(...)를 호출합니다.
    //    - 프로퍼티 이름 ↔ set메서드 이름이 정확히 매칭되어야 합니다.
    // ──────────────────────────────────────────────────────────────────────────
    /** @return 상품 ID */
    public String getId() { return id; }

    /** @param id 스프링 XML의 <property name="id" ...> 가 호출할 세터 */
    public void setId(String id) { this.id = id; }

    /** @return 상품 이름 */
    public String getName() { return name; }

    /** @param name 스프링 XML의 <property name="name" ...> 가 호출할 세터 */
    public void setName(String name) { this.name = name; }

    /** @return 상품 가격 */
    public double getPrice() { return price; }

    /** @param price 스프링 XML의 <property name="price" ...> 가 호출할 세터 */
    public void setPrice(double price) { this.price = price; }


    // ──────────────────────────────────────────────────────────────────────────
    // 4) toString
    //    - 디버깅/로그 출력 시 객체 상태를 읽기 좋게 보여줍니다.
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}
