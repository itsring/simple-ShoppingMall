package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

/**
 * id           상품 id
 * itemName     상품 명
 * price        상품 가격
 * quantity     상품 수량
 */
@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price=0;
    private Integer quantity=0;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
