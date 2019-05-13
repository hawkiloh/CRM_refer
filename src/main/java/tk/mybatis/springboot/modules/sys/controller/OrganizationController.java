package tk.mybatis.springboot.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.modules.sys.model.Organization;
import tk.mybatis.springboot.modules.sys.vo.OrganizationVo;
import tk.mybatis.springboot.modules.sys.service.OrganizationService;
import tk.mybatis.springboot.common.util.JsonResult;

import java.util.List;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/getAll")
    @ResponseBody
    public List<OrganizationVo> findAll() {
        return organizationService.findAll();
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public JsonResult<Integer> saveOrganization(Organization organization) {
        int i = 0;
        try {
            i = organizationService.saveOrganization(organization);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public JsonResult<Organization> findById(String id) {
        Organization org = new Organization();
        try {
            org = organizationService.findById(id);
        } catch (Exception e) {
            return new JsonResult<Organization>(e);
        }
        return new JsonResult<Organization>(org);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public JsonResult<Integer> delete(String id) {
        int i = 0;
        try {
            i = organizationService.delete(id);
        } catch (Exception e) {
            return new JsonResult<Integer>(e);
        }
        return new JsonResult<Integer>(i);
    }
}
