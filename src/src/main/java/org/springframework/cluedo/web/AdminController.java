package org.springframework.cluedo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final String ADMIN_PANEL="admin/adminPanel";
    @GetMapping
    public ModelAndView getAdminPanel(){
        ModelAndView result=new ModelAndView(ADMIN_PANEL);
        return result;
    }
}
