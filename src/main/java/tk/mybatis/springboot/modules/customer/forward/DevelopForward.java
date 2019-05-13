package tk.mybatis.springboot.modules.customer.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DevelopForward {

    @RequestMapping(value = "/market/develop")
    public String market_develop() {
        return "/customer/market/customer_develop";
    }

    @RequestMapping(value = "/market/chance")
    public String market_chance() {
        return "/customer/market/customer_chance";
    }

    @RequestMapping(value = "/market/addUI")
    public String market_addUI() {
        return "/customer/market/add";
    }

    @RequestMapping(value = "/market/giveUI")
    public String market_giveUI() {
        return "/customer/market/designee";
    }

    @RequestMapping(value = "/market/exploitUI")
    public String market_exploitUI() {
        return "/customer/market/exploit";
    }

}
