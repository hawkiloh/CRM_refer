package tk.mybatis.springboot.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.modules.sys.model.Resource;
import tk.mybatis.springboot.modules.sys.vo.MenuListVo;
import tk.mybatis.springboot.modules.sys.vo.MenuRightVo;
import tk.mybatis.springboot.modules.sys.vo.MenuVo;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.modules.sys.service.MenuService;
import tk.mybatis.springboot.common.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/menu/getMenu")
    @ResponseBody
    public List<MenuVo> findMenu(HttpServletRequest request) {
        UserVo userVo = (UserVo) request.getSession().getAttribute("user");

        return menuService.findMenu(userVo);
    }

    @RequestMapping(value = "/menu/getMenuList")
    @ResponseBody
    public Map<String, Object> findMenuList() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<MenuListVo> menuVos = menuService.findMenuList();
        resultMap.put("rows", menuVos);
        return resultMap;
    }

    @RequestMapping(value = "/menu/findRightList")
    @ResponseBody
    public List<MenuRightVo> findRightList(String id) {
        List<MenuRightVo> menuVos = menuService.findRightList(id);
        return menuVos;
    }

    @RequestMapping(value = "/menu/save")
    @ResponseBody
    public JsonResult<Integer> saveMenu(Resource res) {
        int i = 0;
        try {
            i = menuService.saveMenu(res);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/menu/findById")
    @ResponseBody
    public JsonResult<Resource> findById(String id) {
        Resource resource = new Resource();
        try {
            resource = menuService.findById(id);
        } catch (Exception e) {
            return new JsonResult<Resource>(e);
        }
        return new JsonResult<Resource>(resource);
    }

    @RequestMapping(value = "/menu/delete")
    @ResponseBody
    public JsonResult<Integer> deleteMenu(String ids) {
        int i = 0;
        try {
            i = menuService.deleteMenu(ids);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @GetMapping("/menu/getSelectedMenuIds")
    @ResponseBody
    public List<String> getSelectedMenuIds(String roleId) {
        return menuService.getSelectedMenuIds(roleId);
    }
}
