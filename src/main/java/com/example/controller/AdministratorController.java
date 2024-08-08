package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;

import jakarta.servlet.http.HttpSession;

/**
 * 管理者登録画面を表示するコントローラクラス
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private HttpSession session;

    /**
     * 管理者情報登録画面に遷移するメソッド
     * 
     * @param form  登録フォームオブジェクト
     * @param model モデルオブジェクト
     * @return フォワード先のテンプレート名
     */
    @GetMapping("/toInsert")
    public String toInsert(InsertAdministratorForm form, Model model) {
        model.addAttribute("insertAdministratorForm", form);
        return "administrator/insert";
    }

    /**
     * ログイン画面に遷移するメソッド
     * 
     * @param form  ログインフォームオブジェクト
     * @param model モデルオブジェクト
     * @return フォワード先のテンプレート名
     */
    @GetMapping("/")
    public String toLogin(LoginForm form, Model model) {
        model.addAttribute("loginForm", form);
        return "administrator/login";
    }

    /**
     * 管理者情報を登録する
     * 
     * @param form 管理者情報
     * @return redirect リダイレクト
     */
    @PostMapping("/insert")
    public String insert(InsertAdministratorForm form) {
        Administrator administrator = new Administrator();

        administrator.setName(form.getName());
        administrator.setMailAddress(form.getMailAddress());
        administrator.setPassword(form.getPassword());

        administratorService.insert(administrator);

        return "redirect:/";
    }

    /**
     * ログイン情報を確認する
     * 
     * @param form ログイン情報
     * @return 以下のパターンを使用
     * <ol>
     * <li>administrator/login ログインページ</li>
     * <li>redirect:/employee/showList 従業員情報一覧ページ</li>
     * </ol>
     */
    @PostMapping("/login")
    public String login(LoginForm form, Model model) {
        Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
        if (administrator == null) {
            model.addAttribute("error", "メールアドレスまたはパスワードが不正です。");
            return "administrator/login";
        }
        session.setAttribute("administratorName", administrator.getName());
        return "redirect:/employee/showList";
    }
}
