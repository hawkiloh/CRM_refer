package tk.mybatis.springboot.modules.customer.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.common.util.StringUtil;
import tk.mybatis.springboot.modules.customer.model.CustomerDrains;
import tk.mybatis.springboot.modules.customer.service.CustomerDrainsService;
import tk.mybatis.springboot.common.util.JsonResult;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/customerDrains")
public class CustomerDrainsController {

    @Autowired
    private CustomerDrainsService customerDrainsService;

    /*找所有流失的客户*/
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public Map<String, Object> findAll(CustomerDrains customerDrains, Integer pageIndex, Integer pageSize) {
        customerDrains.setPage(pageIndex + 1);
        customerDrains.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageInfo<CustomerDrains> customerPageInfo = customerDrainsService.findAll(customerDrains);
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

        int i = 0;
        try {
            i = customerDrainsService.saveDrains(customerDrains);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

}
