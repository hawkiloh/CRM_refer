package tk.mybatis.springboot.modules.customer.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.modules.customer.model.Develop;
import tk.mybatis.springboot.modules.customer.model.Plan;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.modules.customer.service.DevelopService;
import tk.mybatis.springboot.common.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/market")
public class DevelopController {

    @Autowired
    private DevelopService developService;

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public Map<String, Object> findAll(Develop develop, Integer pageIndex, Integer pageSize) {
        develop.setPage(pageIndex + 1);
        develop.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageInfo<Develop> develops = developService.findAll(develop);
        resultMap.put("data", develops.getList());
        resultMap.put("total", develops.getTotal());
        return resultMap;
    }

    @RequestMapping(value = "/findAllPlan")
    @ResponseBody
    public Map<String, Object> findAllPlan(Plan plan, Integer pageIndex, Integer pageSize, HttpServletRequest request) {
        UserVo user = (UserVo) request.getSession().getAttribute("user");
        plan.setPage(pageIndex + 1);
        plan.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        plan.setDesignee(user.getLoginName());
        PageInfo<Plan> plans = developService.findAllPlan(plan);
        resultMap.put("data", plans.getList());
        resultMap.put("total", plans.getTotal());
        return resultMap;
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult<Integer> delete(String id) {
        int i = 0;
        try {
            i = developService.delete(id);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public JsonResult<Develop> findById(String id) {
        Develop develop = new Develop();
        try {
            develop = developService.findById(id);
        } catch (Exception e) {
            return new JsonResult<Develop>(e);
        }
        return new JsonResult<Develop>(develop);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public JsonResult<Integer> savedevelop(Develop develop, HttpServletRequest request) {
        int i = 0;
        try {
            UserVo user = (UserVo) request.getSession().getAttribute("user");
            i = developService.savedevelop(develop, user);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/findUserId")
    @ResponseBody
    public JsonResult<String> findUserId(String id) {
        String i = "";
        try {
            i = developService.findUserId(id);
        } catch (Exception e) {
            return new JsonResult<String>(e);
        }
        return new JsonResult<String>(i);
    }

    @RequestMapping(value = "/verRow")
    @ResponseBody
    public JsonResult<Integer> verRow(String id, String op) {
        int i = 0;
        try {
            i = developService.verRow(id, op);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/ findPlanById")
    @ResponseBody
    public JsonResult<Plan> findPlanById(String id) {
        Plan plan = new Plan();
        try {
            plan = developService.findPlanById(id);
        } catch (Exception e) {
            return new JsonResult<Plan>(e);
        }
        return new JsonResult<Plan>(plan);
    }

    @RequestMapping(value = "/savePlan")
    @ResponseBody
    public JsonResult<Integer> savePlan(Plan plan) {
        int i = 0;
        try {
            i = developService.savePlan(plan);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }
}
