package ua.com.library.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.library.entity.User;
import ua.com.library.service.UserService;

import javax.servlet.http.HttpSession;
@Slf4j
@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final UserService userService;
    @GetMapping("/registration")
    public String registrationView(Model model) {
        model.addAttribute("user", new User());
        return "/registration";
    }



    @PostMapping("/registerUser")
    public ModelAndView registration(HttpSession session, Model model, @ModelAttribute User user) {

        log.info("\n\n in the registerUser controller");

        try {
            user = userService.successfullyRegistered(user);
            model.addAttribute("user", user);
            return new ModelAndView("/reader");

        } catch (RuntimeException e){  //todo: create custom exeption
            model.addAttribute("badCredentials", true);
            return new ModelAndView("/registration");
        }

    }
}
