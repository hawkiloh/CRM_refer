package tk.mybatis.springboot.modules.sys.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.modules.sys.model.SysLogModel;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.modules.sys.service.SysLogService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;


    @RequestMapping(value = "/findAll")
    @ResponseBody
    public Map<String, Object> findAll(SysLogModel sysLog, Integer pageIndex, Integer pageSize, HttpServletRequest request) {
        UserVo userVo = (UserVo) request.getSession().getAttribute("user");
        sysLog.setPage(pageIndex + 1);
        sysLog.setRows(pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageInfo<SysLogModel> sysLogPageInfo = sysLogService.findAll(sysLog, userVo);
        resultMap.put("data", sysLogPageInfo.getList());
        resultMap.put("total", sysLogPageInfo.getTotal());
        return resultMap;
    }
}
