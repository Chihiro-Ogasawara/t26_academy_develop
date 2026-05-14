package jp.co.metateam.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.metateam.library.model.BookMst;
import jp.co.metateam.library.model.BookMstDto;
import jp.co.metateam.library.service.BookMstService;
import lombok.extern.log4j.Log4j2;

/**
 * 書籍関連クラス
 */
@Log4j2
@Controller
public class BookController {
    private final BookMstService bookMstService;

    @Autowired
    public BookController(BookMstService bookMstService){
        this.bookMstService = bookMstService;
    }

    @GetMapping("/book/index")
    public String index(Model model) {
        // 書籍を全件取得
        List<BookMstDto> bookMstList = this.bookMstService.findAvailableWithStockCount();

        model.addAttribute("bookMstList", bookMstList);

        return "book/index";
    }

    @GetMapping("/book/add")
    public String add(Model model) {
        if (!model.containsAttribute("bookMstDto")) {//取得したデータを一覧に返す
            model.addAttribute("bookMstDto", new BookMstDto());
        }

        return "book/add";
    }
    //ここから
      @PostMapping("/book/add")//このURL内に保存ボタンが押されたら実行 Dtoは入力データ
    public String book(@Valid @ModelAttribute BookMstDto bookMstDto, BindingResult result, RedirectAttributes ra) {
    //（）内は受け取るデータ
    //Vallidで入力チェック、ModelAttribute：画面入力値を自動的にDTO（BookMstDto bookMstDto）に入れる、 BindingResult result：バリデーションチェックした結果をresultに保存する、 RedirectAttributes ra：リダイレクト(画面間でのデータの受け渡し)したデータの受け渡し
    //@Valid @ModelAttribute BookMstDto bookMstDto；フォームの入力値をBookMstDtoに自動セット、Vallidで入力チェック

        bookMstService.save(bookMstDto);//bookMstServiceでbookMstDtoをデータベースに保存
        //serviseの中でsaveメゾットを呼び出している
        return "redirect:/book/index";//書籍一覧画面に戻る
        
    }

    //ここまで
}
    
