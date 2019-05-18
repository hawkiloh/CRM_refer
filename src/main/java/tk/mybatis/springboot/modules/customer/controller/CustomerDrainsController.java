package tk.mybatis.springboot.modules.customer.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.common.util.EmailUtil;
import tk.mybatis.springboot.common.util.StringUtil;
import tk.mybatis.springboot.modules.customer.model.Customer;
import tk.mybatis.springboot.modules.customer.model.CustomerDrains;
import tk.mybatis.springboot.modules.customer.service.CustomerDrainsService;
import tk.mybatis.springboot.common.util.JsonResult;
import tk.mybatis.springboot.modules.customer.service.CustomerService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/customerDrains")
public class CustomerDrainsController {

    @Autowired
    private CustomerDrainsService customerDrainsService;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private CustomerService customerService;

    /*找所有流失的客户*/
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public Map<String, Object> findAll(CustomerDrains customerDrains, Integer pageIndex, Integer pageSize,
                                       @RequestParam(value = "showReview", required = false, defaultValue = "false") Boolean showReview) {
        customerDrains.setPage(pageIndex + 1);
        customerDrains.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageInfo<CustomerDrains> customerPageInfo;
        if (showReview) {
            //查询回访的客户
            customerPageInfo = customerDrainsService.findAllByReview(customerDrains);
        } else {
            customerPageInfo = customerDrainsService.findAll(customerDrains);
        }

        resultMap.put("data", customerPageInfo.getList());
        resultMap.put("total", customerPageInfo.getTotal());
        return resultMap;
    }

    @RequestMapping(value = "/waitDrains")
    @ResponseBody
    public JsonResult<Integer> waitDrains(String id) {
        int i = 0;
        try {
            i = customerDrainsService.waitDrains(id);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public JsonResult<CustomerDrains> findById(String id) {
        CustomerDrains customer = new CustomerDrains();
        try {
            customer = customerDrainsService.findById(id);
        } catch (Exception e) {
            return new JsonResult<CustomerDrains>(e);
        }
        return new JsonResult<CustomerDrains>(customer);
    }

    @RequestMapping(value = "/okDrains")
    @ResponseBody
    public JsonResult<Integer> okDrains(CustomerDrains customerDrains) {
        int i = 0;
        try {
            i = customerDrainsService.okDrains(customerDrains);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/saveDrains")
    @ResponseBody
    public JsonResult<Integer> saveDrains(CustomerDrains customerDrains) {
        customerDrains.setCustomerName(null);

        int i = 0;
        try {
            i = customerDrainsService.saveDrains(customerDrains);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }



    @RequestMapping(value = "/sendEmail")
    @ResponseBody
    public JsonResult<Integer> sendEmail(@RequestBody List<CustomerDrains> customerDrainsList) {

        //构建目标用户的列表
        ArrayList<String> userList = new ArrayList<>();
        System.out.println("size: " + customerDrainsList.size());
//        System.out.println(customerDrainsList.toString());
        for (CustomerDrains customerDrains : customerDrainsList) {

            String customerId = customerDrains.getCustomerId();
            Customer customer = customerService.findById(customerId);
            String email=customer.getEmail();
            if (!StringUtil.isNullOrEmpty(email)) {

                userList.add(email);
            }
        }
        String[] toUsers = userList.toArray(new String[0]);
        try {
            emailUtil.sendEmail(toUsers, emailUtil.getSubjectForDrain(), emailUtil.getContentForDrain());
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new JsonResult<>(e);
        }
        return new JsonResult<Integer>(userList.size());    //返回邮件发送成功的数量
    }
}
