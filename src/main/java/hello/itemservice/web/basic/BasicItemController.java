package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";

    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ){
        Item item = new Item(itemName, price, quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * @ModelAttribute("item")
     * model.addAttribute("item", item); 자동 등록되서 지워도 됨.
     * @ModelAttribute 이름을 생략하면 @ModelAttribute Item item  클래스 명(Item)의 첫글자를 소문자로 바꾸어서 model.addAttribute("item",item) 이 됨.
     *
     */
//    @PostMapping("/add")
    public String addItemV2(
           @ModelAttribute("item") Item item,
            Model model
    ){
        itemRepository.save(item);

        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV3(
            Item item,
            Model model
    ){
        itemRepository.save(item);

        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV4(
            Item item,
            Model model
    ){
        itemRepository.save(item);
// 인코딩해줘서 넣어야됨. -> RedirectAttributes 사용
        return "redirect:/basic/items"+item.getId();
    }

    @PostMapping("/add")
    public String addItemV5(
            Item item,
            RedirectAttributes redirectAttributes
    ){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm"; // 수정페이지
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}"; // 상세 화면
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }

}
