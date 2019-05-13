package tk.mybatis.springboot.modules.sys.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.springboot.common.annotation.SysLog;
import tk.mybatis.springboot.modules.sys.model.User;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.modules.sys.service.UserService;
import tk.mybatis.springboot.common.util.JsonResult;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    //protected Logger logger = LogManager.getLogger(getClass());
    private Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @SysLog("用户登录")
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public JsonResult<User> login(User user, HttpServletRequest request) {
        //HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        JsonResult<User> result = userService.login(user);
        if (result.getState() == 0) {
            UserVo userVo = userService.findById(result.getData().getId());
            request.getSession().setAttribute("user", userVo);
            request.getSession().setAttribute("role", userVo.getRoles());
            request.getSession().setAttribute("loginName", userVo.getLoginName());
            //request.getSession().setAttribute("roles",userVo.getRoles());
            //request.getSession().setAttribute("");
            logger.warn(userVo.getLoginName() + "【" + userVo.getRoles() + "】登陆成功");
        } else {
            logger.warn(user.getLoginName() + "【" + user.getPassword() + "】登陆失败," + result.getMessage());
        }
        return result;
    }

    /**
     * 退出
     * @return {Result}
     */
	/*@RequestMapping("/logout")
	@ResponseBody
	public Object logout() {
		logger.info("登出");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return renderSuccess();
	}*/

}
