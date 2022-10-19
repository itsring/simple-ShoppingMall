package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Item 저장소
@Repository
public class ItemRepository {
    //동시에 접근하면 다른데이터가 들어올수 있어서 HashMap 쓰면안됨, long도 쓰면 안됨. HashMap -> ConcurrentHashMap, long - >   AtomicLong
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L;

    // 저장
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    // 조회
    public Item findById(Long id) {
        return store.get(id);
    }

    // 모두 조회
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());


    }
    // test용 모두 삭제.
    public void  clearStore(){
        store.clear();
    }
}
