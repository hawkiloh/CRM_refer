package tk.mybatis.springboot.modules.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.modules.sys.model.Pwd;
import tk.mybatis.springboot.modules.sys.model.User;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.modules.sys.service.UserService;
import tk.mybatis.springboot.common.util.JsonResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public Map<String, Object> findAll(User user, Integer pageIndex, Integer pageSize) {
        user.setPage(pageIndex + 1);
        user.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<UserVo> users = userService.findAll(user);
        Integer total = userService.countAll(user);
        resultMap.put("data", users);
        resultMap.put("total", total);
        return resultMap;
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public JsonResult<UserVo> findById(String id) {
        UserVo user = new UserVo();
        try {
            user = userService.findById(id);
        } catch (Exception e) {
            return new JsonResult<UserVo>(e);
        }
        return new JsonResult<UserVo>(user);
    }

    //找所有角色为customer的用户
    @RequestMapping(value = "/findAllManager")
    @ResponseBody
    public List<Map<String, Object>> findAllManager() {
        List<Map<String, Object>> list = userService.findAllManager();
        return list;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public JsonResult<Integer> saveUser(UserVo user) {
        int i = 0;
        try {
            i = userService.saveUser(user);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }


    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult<Integer> delete(String id) {
        int i = 0;
        try {
            i = userService.delete(id);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/findSalesAll")
    @ResponseBody
    public List<Map<String, Object>> findSalesAll() {
        List<Map<String, Object>> users = userService.findSalesAll();
        return users;
    }


    @RequestMapping(value = "/setPwd")
    @ResponseBody
    public JsonResult<Integer> setPwd(Pwd pwd) {
        JsonResult<Integer> i = userService.setPwd(pwd);
        return i;
    }

}
