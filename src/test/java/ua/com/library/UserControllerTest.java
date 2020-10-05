package ua.com.library;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.library.config.LoginHandler;
import ua.com.library.controller.LoginFormController;
import ua.com.library.controller.RegistrationController;
import ua.com.library.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginHandler handler;

    @Test
    public void mainShouldReturnMainPage() throws Exception {
        mvc.perform(get("/registration"))
                .andExpect(view().name("/registration"));
    }
}