package tk.mybatis.springboot.modules.customer.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerDrainsForward {

    @RequestMapping(value = "/customerDrains/addUI")
    public String customer_add() {
        return "/customer/custom/addDrains";
    }

}
