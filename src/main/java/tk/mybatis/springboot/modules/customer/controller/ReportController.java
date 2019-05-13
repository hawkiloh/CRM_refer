package tk.mybatis.springboot.modules.customer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.modules.customer.service.ReportService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/CountCustomer")
    @ResponseBody
    public List<List<Map<String, Object>>> CountCustomer() {
        return reportService.CountCustomer();
    }
}
