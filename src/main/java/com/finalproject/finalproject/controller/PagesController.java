package com.finalproject.finalproject.controller;

import com.finalproject.finalproject.model.VisaApplicationModel;
import com.finalproject.finalproject.repository.VisaApplicationRepository;
import com.finalproject.finalproject.model.UserModel;
import com.finalproject.finalproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PagesController {

    @Autowired
    VisaApplicationRepository visaRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/registration")
    public String register(Model model) {
        //usersRepository.findByEmail(this.url)

        return "registration";
    }

    @PostMapping("/regAction")
    public String regAction(@RequestParam(value="email") String email, @RequestParam(value="password") String password, @RequestParam(value="confirm_password") String confirm_password) {
        //Проверяем есть ли пользователь по такой почте
        boolean isRegistered = usersRepository.findByEmail(email) != null;

        if (isRegistered)
        {
            System.out.println("Already Registered");
            System.out.println(email);
            return "redirect:/login";
        }

        //Проверяем соответствие паролей по хэшам
        if (password.hashCode() != confirm_password.hashCode())
        {
            System.out.println("Not Same Passwords");
            System.out.println(email);
            return "redirect:/registration";
        }

        System.out.println("Register");
        System.out.println(email);
        System.out.println(password.hashCode());

        //Новая запись
        UserModel newUser = new UserModel();
        newUser.setEmail(email);
        newUser.setPasswordHash(password.hashCode());

        //Сохранили в таблице
        usersRepository.save(newUser);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/loginAction")
    public String loginAction(@RequestParam(value="email") String email, @RequestParam(value="password") String password, RedirectAttributes redirectAttributes) {
        System.out.println(email);
        System.out.println(password);

        System.out.println(password.hashCode());

        //Ищем пользователя по почте
        UserModel user = usersRepository.findByEmail(email);

        //Если его нет, то регистрация
        if (user == null)
        {
            System.out.println("No user");
            return "redirect:/registration";
        }

        //Проверяем пароль по хэше
        if (user.getPasswordHash() != password.hashCode())
        {
            System.out.println("Wrong Passwords");
            System.out.println(email);
            return "redirect:/login";
        }

        //Добавляем аргумент userID в редирект запрос
        redirectAttributes.addFlashAttribute("userID", user.getUserId());

        return "redirect:/user_doc";
    }

    @GetMapping("/user_doc")
    public String userDock(Model model) {

        //Проверка пришёл ли аргумент (userID)
        if (!(model.getAttribute("userID") instanceof Integer))
        {
            return "redirect:/login";
        }

        //Забираем userID из аргументов запроса
        Integer userID = (Integer) model.getAttribute("userID");

        //Достаём записи о заявках из БД
        ArrayList<VisaApplicationModel> visasData = new ArrayList<VisaApplicationModel>();
        visaRepository.findByUserId(userID).forEach(visasData::add);

        //Кладём userID в данные шаблона
        model.addAttribute("userID", userID);

        //Кладём заявки в данные шаблона
        model.addAttribute("visasData", visasData);
        return "user_doc";
    }

    @GetMapping("/createApplication")
    public String createApplication(@RequestParam(value = "userID", required = true) Integer userID, Model model) {

        System.out.println(userID);
        model.addAttribute("userID", userID);
        return "document_create";
    }

    @PostMapping("/newApplication")
    public String createApplication(
            @RequestParam(value = "userID", required = true) Integer userID,
            @RequestParam(value = "nameOwner", required = true) String nameOwner,
            @RequestParam(value = "passportOwner", required = true) String passportOwner,
            @RequestParam(value = "nameMigrant", required = true) String nameMigrant,
            @RequestParam(value = "passportMigrant", required = true) String passportMigrant,
            @RequestParam(value = "citizenshipMigrant", required = true) String citizenshipMigrant,
            @RequestParam(value = "address", required = true) String address,
            RedirectAttributes redirectAttributes) {

        System.out.println("newApplication");

        // Создаём заявку
        VisaApplicationModel newApplication = new VisaApplicationModel();
        newApplication.setUserId(userID);
        newApplication.setState((short) 0);
        newApplication.setNameOwner(nameOwner);
        newApplication.setPassportOwner(passportOwner);
        newApplication.setNameMigrant(nameMigrant);
        newApplication.setPassportMigrant(passportMigrant);
        newApplication.setCitizenshipMigrant(citizenshipMigrant);
        newApplication.setAddress(address);

        //Сохраняем завяку в БД
        visaRepository.save(newApplication);

        redirectAttributes.addFlashAttribute("userID", userID);

        return "redirect:/user_doc";
    }
}
