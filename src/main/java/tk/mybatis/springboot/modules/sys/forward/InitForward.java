package tk.mybatis.springboot.modules.sys.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitForward {
    //页面跳转

	/*//
	@RequestMapping(value="/toLimit")
	public String toLimit(){
		return "limit";
	}*/

    //去登录页面
    @RequestMapping(value = {"/toLogin", "/", ""})
    public String toLog() {
        return "login";
    }

    //去首页
    @RequestMapping(value = "/toIndex")
    public String toIndex() {
        return "index";
    }

    //欢迎页面

    @RequestMapping(value = "/toWelcome")
    public String toWelcome() {
        return "welcome";
    }

    //设置密码

    @RequestMapping(value = "/setPWD")
    public String setPWD() {
        return "/sys/password/password";
    }


}
