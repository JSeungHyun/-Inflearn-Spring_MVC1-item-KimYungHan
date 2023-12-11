package hello.springmvc.domain.item;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    // 상품 초기화
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }

    /**
     * @param model
     * @return 상품 목록 페이지
     */
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    /**
     * @param itemId
     * @param model
     * @return 상품 상세 페이지
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * @return 상품 등록 페이지
     */
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * 상품 등록 Version 별로
     * @param itemName
     * @param price
     * @param quantity
     * @param model
     * @return
     */
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam int price,
                            @RequestParam Integer quantity, Model model){
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * ModelAttribute는 데이터를 DTO로 파싱하고 Model에 추가하는 역할까지
     */
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
//        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){ // @ModelAttribute 에 name을 지정하지 않으면 클래스명 첫 글자만 소문자로 변경
        itemRepository.save(item);
//        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV5(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
}
