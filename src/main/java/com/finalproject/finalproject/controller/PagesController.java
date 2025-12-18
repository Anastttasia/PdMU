package com.finalproject.finalproject.controller;

import com.finalproject.finalproject.model.*;
import com.finalproject.finalproject.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PagesController {

    private static class RegistrationValidationObject {
        public Boolean isEmailError = false;
        public Boolean isPasswordError = false;
        public Boolean isPassportError = false;

        public Boolean getIsEmailError() {
            return isEmailError;
        }

        public Boolean getIsPasswordError() {
            return isPasswordError;
        }

        public Boolean getIsPassportError() {
            return isPassportError;
        }
    }

    public boolean isInteger(String s) {
        if (s == null || s.isEmpty()) return false;

        for (int i = (s.charAt(0) == '-') ? 1 : 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @PostConstruct
    public void startUpInit() {
        // Create test user for Owner
        UserModel newOwnerUser = new UserModel();
        newOwnerUser.setEmail("123@ggg");
        newOwnerUser.setPasswordHash("333".hashCode());

        //Сохранили в таблице
        usersRepository.save(newOwnerUser);

        // Link it with Owner
        OwnerModel newOwner = new OwnerModel(
                newOwnerUser.getUserId(),
                "Васильков Василий Васильевич",
                "4410333444",
                "г. Санкт-Петербург, ул. Садовая, д. 13, кв. 15"
        );

        ownersRepository.save(newOwner);

        // Create test user for Migrant
        UserModel newOwnerUser2 = new UserModel();
        newOwnerUser2.setEmail("321@ggg");
        newOwnerUser2.setPasswordHash("333".hashCode());

        //Сохранили в таблице
        usersRepository.save(newOwnerUser2);

        // Link it with Owner
        OwnerModel newOwner2 = new OwnerModel(
                newOwnerUser2.getUserId(),
                "Петров Пётр Петрович",
                "4480222111",
                "г. Санкт-Петербург, ул. Конюшенная, д. 5, кв. 1"
        );

        ownersRepository.save(newOwner2);
    }

    @Autowired
    VisaApplicationRepository visaRepository;

    @Autowired
    AdminsRepository adminsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OwnersRepository ownersRepository;

    @Autowired
    MigrantsRepository migrantsRepository;

    @GetMapping("/registration")
    public String register(Model model) {
        //usersRepository.findByEmail(this.url)

        RegistrationValidationObject errorsChecker = new RegistrationValidationObject();

        model.addAttribute("errorsChecker", errorsChecker);

        return "registration";
    }

    @PostMapping("/regAction")
    public String regAction(
            Model model,
            @RequestParam(value="email") String email,
            @RequestParam(value="password") String password,
            @RequestParam(value="confirm_password") String confirm_password,
            @RequestParam(value="fio") String fio,
            @RequestParam(value="passport") String passport,
            @RequestParam(value="address") String address
    ) {

        RegistrationValidationObject errorsChecker = new RegistrationValidationObject();

        errorsChecker.isEmailError = !EmailValidator.getInstance().isValid(email);
        errorsChecker.isPasswordError = password.hashCode() != confirm_password.hashCode();
        errorsChecker.isPassportError = !this.isInteger(passport) || passport.length() != 10;

        model.addAttribute("errorsChecker", errorsChecker);

        if (errorsChecker.isEmailError)
        {
            System.out.println("Wrong Email");
            System.out.println(email);
        }

        //Проверяем соответствие паролей по хэшам
        if (errorsChecker.isPasswordError)
        {
            System.out.println("Not Same Passwords");
            System.out.println(email);
        }

        if (errorsChecker.isPassportError)
        {
            System.out.println("Wrong Passport");
            System.out.println(passport);

        }

        if (errorsChecker.isEmailError || errorsChecker.isPasswordError || errorsChecker.isPassportError)
        {
            return "registration";
        }

        //Проверяем есть ли пользователь по такой почте
        boolean isRegistered = usersRepository.findByEmail(email) != null;

        if (isRegistered)
        {
            System.out.println("Already Registered");
            System.out.println(email);
            return "redirect:/login";
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

        OwnerModel newOwner = new OwnerModel(
                newUser.getUserId(),
                fio,
                passport,
                address
        );

        ownersRepository.save(newOwner);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/loginAction")
    public String loginAction(@RequestParam(value="email") String email, @RequestParam(value="password") String password, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        System.out.println(email);
        System.out.println(password);

        System.out.println(password.hashCode());

        //Ищем пользователя по почте
        AdminModel admin = adminsRepository.findByEmail(email);

        if (admin != null)
        {
            if (admin.getPasswordHash() != password.hashCode())
            {
                System.out.println("Wrong Passwords");
                System.out.println(email);
                return "redirect:/login";
            }

            response.addCookie(new Cookie("adminID", admin.getAdminId().toString()));
            response.addCookie(new Cookie("passwordHash", admin.getPasswordHash().toString()));

            return "redirect:/admin_doc";
        }


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

        response.addCookie(new Cookie("userID", user.getUserId().toString()));
        response.addCookie(new Cookie("passwordHash", user.getPasswordHash().toString()));

        return "redirect:/user_doc";
    }

    @GetMapping("/user_doc")
    public String userDock(Model model, @CookieValue(value = "userID", defaultValue = "") String _userID, @CookieValue(value = "passwordHash", defaultValue = "") String _passwordHash) {

        //Проверка пришёл ли аргумент (userID)

        System.out.println(_userID);
        System.out.println(_passwordHash);

        if (_userID.isEmpty() || _passwordHash.isEmpty())
        {
            return "redirect:/login";
        }

        Integer userID = Integer.valueOf(_userID);

        UserModel user = usersRepository.findByUserId(userID);
        if (user == null)
        {
            return "redirect:/login";
        }

        Integer passwordHash = Integer.valueOf(_passwordHash);

        if (!user.getPasswordHash().equals(passwordHash))
        {
            return "redirect:/login";
        }

        OwnerModel owner = ownersRepository.findByUserId(userID);

        if (owner != null) {
            //Кладём userID в данные шаблона
            model.addAttribute("userID", userID);

            //Кладём ownerData в данные шаблона
            model.addAttribute("ownerData", owner);

            //Достаём записи о заявках из БД
            ArrayList<VisaApplicationModel> visasData = new ArrayList<VisaApplicationModel>();
            visaRepository.findByUserId(userID).forEach(visasData::add);

            //Кладём заявки в данные шаблона
            model.addAttribute("visasData", visasData);
            return "user_doc";
        }

        return "redirect:/login";
    }

    @GetMapping("/createApplication")
    public String createApplication(@RequestParam(value = "userID", required = true) Integer userID, Model model) {

        System.out.println(userID);

        OwnerModel owner = ownersRepository.findByUserId(userID);

        if (owner == null)
        {
            return "redirect:/user_doc";
        }

        model.addAttribute("userID", userID);
        model.addAttribute("ownerData", owner);
        return "document_create";
    }

    @PostMapping("/newApplication")
    public String createApplication(
            @RequestParam(value = "userID", required = true) Integer userID,
            @RequestParam(value = "nameMigrant", required = true) String nameMigrant,
            @RequestParam(value = "passportMigrant", required = true) String passportMigrant,
            @RequestParam(value = "citizenshipMigrant", required = true) String citizenshipMigrant,
            RedirectAttributes redirectAttributes) {

        System.out.println("newApplication");

        OwnerModel owner = ownersRepository.findByUserId(userID);

        MigrantModel migrant = migrantsRepository.findByPassport(passportMigrant);

        VisaApplicationModel newApplication = new VisaApplicationModel();

        String comment = "";
        short state = 0;

        if (migrant == null)
        {
            comment += "Данные о мигранте не найдены, проверьте документы.\n";
            state = 2;
        }
        else
        {
            if (!migrant.getCitizenship().equals(citizenshipMigrant))
            {
                comment += "Данные о гражданстве не совпали.\n";
                state = 2;
            }
            if (!migrant.getName().equals(nameMigrant))
            {
                comment += "Данные о ФИО не совпали.\n";
                state = 2;
            }
        }
        // Создаём заявку

        newApplication.setUserId(userID);
        newApplication.setState(state);
        newApplication.setNameOwner(owner.getName());
        newApplication.setPassportOwner(owner.getPassport());
        newApplication.setNameMigrant(nameMigrant);
        newApplication.setPassportMigrant(passportMigrant);
        newApplication.setCitizenshipMigrant(citizenshipMigrant);
        newApplication.setAddress(owner.getAddress());
        newApplication.setComment(comment);

        //Сохраняем завяку в БД
        visaRepository.save(newApplication);

        return "redirect:/user_doc";
    }

    @GetMapping("/admin_doc")
    public String adminDoc(Model model, @CookieValue(value = "adminID", defaultValue = "") String _adminID, @CookieValue(value = "passwordHash", defaultValue = "") String _passwordHash) {

        System.out.println(_adminID);
        System.out.println(_passwordHash);

        if (_adminID.isEmpty() || _passwordHash.isEmpty())
        {
            return "redirect:/login";
        }

        Integer adminID = Integer.valueOf(_adminID);

        AdminModel admin = adminsRepository.findByAdminId(adminID);
        if (admin == null)
        {
            return "redirect:/login";
        }

        Integer passwordHash = Integer.valueOf(_passwordHash);

        if (!admin.getPasswordHash().equals(passwordHash))
        {
            return "redirect:/login";
        }

        ArrayList<VisaApplicationModel> visasData = new ArrayList<VisaApplicationModel>();

        for (VisaApplicationModel visaData : visaRepository.findAll()) {
            if (visaData.getState() == 0)
            {
                visasData.add(visaData);
            }
        }

        //Кладём заявки в данные шаблона
        model.addAttribute("visasData", visasData);
        return "admin_doc";
    }

    @PostMapping("/openApplication")
    public String openApplication(
            Model model,
            @CookieValue(value = "adminID", defaultValue = "") String _adminID,
            @CookieValue(value = "passwordHash", defaultValue = "") String _passwordHash,
            @RequestParam(value = "applicationID", required = true) Integer applicationID
    ) {
        System.out.println(_adminID);
        System.out.println(_passwordHash);

        if (_adminID.isEmpty() || _passwordHash.isEmpty())
        {
            return "redirect:/login";
        }

        Integer adminID = Integer.valueOf(_adminID);

        AdminModel admin = adminsRepository.findByAdminId(adminID);
        if (admin == null)
        {
            return "redirect:/login";
        }

        Integer passwordHash = Integer.valueOf(_passwordHash);

        if (!admin.getPasswordHash().equals(passwordHash))
        {
            return "redirect:/login";
        }

        VisaApplicationModel visaData = visaRepository.findById(applicationID);

        if (visaData == null)
        {
            return "redirect:/admin_doc";
        }

        //Кладём заявки в данные шаблона
        model.addAttribute("visaData", visaData);
        return "document_work";
    }

    @PostMapping("/setApplicationStatus")
    public String setApplicationStatus(
            Model model,
            @CookieValue(value = "adminID", defaultValue = "") String _adminID,
            @CookieValue(value = "passwordHash", defaultValue = "") String _passwordHash,
            @RequestParam(value = "applicationID", required = true) Integer applicationID,
            @RequestParam(value = "comment", required = true) String comment,
            @RequestParam(value = "applicationStatus", required = true) String _applicationStatus
    ) {

        System.out.println(_adminID);
        System.out.println(_passwordHash);

        if (_adminID.isEmpty() || _passwordHash.isEmpty())
        {
            return "redirect:/login";
        }

        Integer adminID = Integer.valueOf(_adminID);

        AdminModel admin = adminsRepository.findByAdminId(adminID);
        if (admin == null)
        {
            return "redirect:/login";
        }

        Integer passwordHash = Integer.valueOf(_passwordHash);

        if (!admin.getPasswordHash().equals(passwordHash))
        {
            return "redirect:/login";
        }

        VisaApplicationModel visaData = visaRepository.findById(applicationID);

        if (visaData == null)
        {
            return "redirect:/admin_doc";
        }

        if (!this.isInteger(_applicationStatus))
        {
            return "redirect:/admin_doc";
        }

        Integer applicationStatus = Integer.valueOf(_applicationStatus);

        if (applicationStatus > 2 || applicationStatus < 0)
        {
            return "redirect:/admin_doc";
        }

        visaData.setComment(comment);
        visaData.setState(applicationStatus.shortValue());

        visaRepository.save(visaData);

        return "redirect:/admin_doc";
    }
}


