package spring.rakscode;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import spring.excel.ExcelParserImpl;

@Controller
public class RaksCodeController {

    private ExcelParserImpl excelParser;

    public RaksCodeController() {
        excelParser = new ExcelParserImpl();
    }

    @RequestMapping(value = "/raksform", method = RequestMethod.GET)
    public ModelAndView showform(Model model) {
        return new ModelAndView("rakscode/raksform","rakscode", new RaksCode());
    }

    @RequestMapping(value = "/givefiles")
    public ModelAndView giveFiles(@ModelAttribute(value = "rakscode") RaksCode raksCode) {
        excelParser.getSetOfPlikiZrodlowe(raksCode);
        return null;
    }

    @RequestMapping(value = "/viewfiles")
    public ModelAndView viewFiles(RaksCode raksCode) {
        return new ModelAndView("rakscode/viewfiles", "plikiZrodlowe", excelParser.getSetOfPlikiZrodlowe(raksCode));
    }

}
