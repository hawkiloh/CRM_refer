package tk.mybatis.springboot.modules.customer.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReportForward {

    @RequestMapping(value = "/report/info")
    public String market_develop() {
        return "/customer/report/info_report";
    }

}
