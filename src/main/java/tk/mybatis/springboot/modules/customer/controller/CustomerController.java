package tk.mybatis.springboot.modules.customer.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.common.util.EmailUtil;
import tk.mybatis.springboot.common.util.StringUtil;
import tk.mybatis.springboot.modules.customer.model.Customer;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.modules.customer.service.CustomerService;
import tk.mybatis.springboot.common.util.ExcelUtil;
import tk.mybatis.springboot.common.util.JsonResult;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExcelUtil excelUtil;

    @Autowired
    private EmailUtil emailUtil;


    @RequestMapping(value = "/findAll")
    @ResponseBody
    public Map<String, Object> findAll(Customer customer, Integer pageIndex, Integer pageSize, HttpServletRequest request,
                                       @RequestParam(value = "showBirthday", required = false, defaultValue = "false") Boolean showBirthday) {
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        customer.setPage(pageIndex + 1);
        customer.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageInfo<Customer> customerPageInfo;

        System.out.println("customer name: " + customer.getName());

        if (showBirthday) {
            //查询今天生日的客户
            customerPageInfo = customerService.findAllByBirthDay(customer);
        } else {
            //查询所有客户
            customerPageInfo = customerService.findAll(customer, user);


        }

        resultMap.put("data", customerPageInfo.getList());
        resultMap.put("total", customerPageInfo.getTotal());
        return resultMap;

    }


    @RequestMapping(value = "/sendEmail")
    @ResponseBody
    public JsonResult<Integer> sendEmail(@RequestBody List<Customer> customerList) {

        //构建目标用户的列表
        ArrayList<String> userList = new ArrayList<>();
        System.out.println("size: " + customerList.size());
//        System.out.println(customerList.toString());
        for (Customer customer : customerList) {

            String email = customer.getEmail();
            if (!StringUtil.isNullOrEmpty(email)) {

                userList.add(email);
            }
        }
        String[] toUsers = userList.toArray(new String[userList.size()]);
        try {
            emailUtil.sendEmail(toUsers, emailUtil.getSubjectForBirthday(), emailUtil.getContentForBirthday());
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new JsonResult<>(e);
        }
        return new JsonResult<Integer>(userList.size());    //返回邮件发送成功的数量
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult<Integer> delete(String id) {
        int i = 0;
        try {
            i = customerService.delete(id);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public JsonResult<Customer> findById(String id) {
        Customer customer = new Customer();
        try {
            customer = customerService.findById(id);
        } catch (Exception e) {
            return new JsonResult<Customer>(e);
        }
        return new JsonResult<Customer>(customer);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public JsonResult<Integer> savecustomer(Customer customer) {
        int i = 0;
        try {
            i = customerService.savecustomer(customer);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }


    @RequestMapping(value = "/exportExcel", method = {RequestMethod.GET})
    public String downLoginLogExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("type") int type) {
        downloadFile(response, excelUtil.getExcel(type));
        return null;
    }

    /**
     * 下载文件
     *
     * @param response
     * @param file
     */
    protected void downloadFile(HttpServletResponse response, File file) {

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
