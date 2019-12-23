package spring.rakscode;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RaksCodeController {

    public RaksCodeController() {
    }

    @RequestMapping(value = "/raksform", method = RequestMethod.GET)
    public ModelAndView showform(Model model) {
        return new ModelAndView("rakscode/raksform","rakscode", new RaksCode());
    }

    @RequestMapping(value = "/give_files")
    public ModelAndView giveFiles(@ModelAttribute(value = "rakscode") RaksCode raksCode) {
        return null;
    };

}
