package tk.mybatis.springboot.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.modules.sys.model.Role;
import tk.mybatis.springboot.modules.sys.service.RoleService;
import tk.mybatis.springboot.common.util.JsonResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "/getRoleList")
    @ResponseBody
    public Map<String, Object> findRoleList(Role role, Integer pageIndex, Integer pageSize) {
        role.setPage(pageIndex + 1);
        role.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageInfo<Role> roles = roleService.findRoleList(role);
        resultMap.put("data", roles.getList());
        resultMap.put("total", roles.getTotal());
        return resultMap;
    }

    @RequestMapping(value = "/getAll")
    @ResponseBody
    public List<Role> getAll(Role role) {
        return roleService.getAll(role);
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public JsonResult<Integer> saveRole(Role role) {
        int i = 0;
        try {
            i = roleService.saveRole(role);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public JsonResult<Role> findById(String id) {
        Role Role = new Role();
        try {
            Role = roleService.findById(id);
        } catch (Exception e) {
            return new JsonResult<Role>(e);
        }
        return new JsonResult<Role>(Role);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult<Integer> delete(String id) {
        int i = 0;
        try {
            i = roleService.delete(id);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/saveRightList")
    @ResponseBody
    public JsonResult<Integer> saveRightList(String ids, String roleId) {
        int i = 0;
        try {
            i = roleService.saveRightList(ids, roleId);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

}
