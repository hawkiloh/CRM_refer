package tk.mybatis.springboot.modules.sys.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.springboot.modules.sys.mapper.OrganizationMapper;
import tk.mybatis.springboot.modules.sys.mapper.UserMapper;
import tk.mybatis.springboot.modules.sys.mapper.UserRoleMapper;
import tk.mybatis.springboot.modules.sys.model.Pwd;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.modules.sys.model.Organization;
import tk.mybatis.springboot.modules.sys.model.User;
import tk.mybatis.springboot.modules.sys.model.UserRole;
import tk.mybatis.springboot.common.util.JsonResult;
import tk.mybatis.springboot.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private OrganizationMapper organizationMapper;

    public JsonResult<User> login(User user) {
        JsonResult<User> result = new JsonResult<User>();
		/*Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		if (user.getLoginName() != null && user.getLoginName().length() > 0) {
			criteria.andLike("loginName", "%" + user.getLoginName() + "%");
		}
		if (user.getPassword() != null && user.getPassword().length() > 0) {
			criteria.andLike("password", "%" + user.getPassword() + "%");
		}*/
        User u = userMapper.selectOne(user);
        if (u == null) {
            result.setState(1);
            result.setMessage("用户名或密码错误");
        } else {
            result.setState(0);
            result.setData(u);
        }
        return result;
    }

    public List<UserVo> findAll(User user) {
        PageHelper.startPage(user.getPage(), user.getRows());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (user.getLoginName() != null && user.getLoginName().length() > 0) {
            criteria.andLike("loginName", "%" + user.getLoginName() + "%");
        }
        if (user.getName() != null && user.getName().length() > 0) {
            criteria.andLike("name", "%" + user.getName() + "%");
        }
        List<User> list = userMapper.selectByExample(example);
        List<UserVo> userVoList = new ArrayList<UserVo>();
        for (User u : list) {
            UserVo userVo = getUserVo(u);
            userVoList.add(userVo);
        }
        return userVoList;
    }

    private UserVo getUserVo(User u) {
        UserVo userVo = new UserVo(u);
        List<Map<String, Object>> userRoles = userRoleMapper.findById(u.getId());//一个用户可以多个角色
        userVo.setRoles(parseRoleNames(userRoles));
        userVo.setRoleIds(parseRoleIds(userRoles));
        Organization org = organizationMapper.selectByPrimaryKey(u.getOrganizationId());//一个用户只能一个部门
        if (org != null) {
            userVo.setOrganizationName(org.getName());
        }
        return userVo;
    }


    public Integer countAll(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (user.getLoginName() != null && user.getLoginName().length() > 0) {
            criteria.andLike("loginName", "%" + user.getLoginName() + "%");
        }
        if (user.getName() != null && user.getName().length() > 0) {
            criteria.andLike("name", "%" + user.getName() + "%");
        }
        return userMapper.selectCountByExample(example);
    }

    public int saveUser(UserVo user) {
        if (user.getId() == null || "".equalsIgnoreCase(user.getId())) {//插入
            String userId = UUIDGenerator.getUUID();
            //保存user信息
            User u = new User(user);
            u.setId(userId);
            u.setSalt(user.getPassword());
            u.setCreateTime(new Date());
            int w = userMapper.insert(u);
            //保存user-role
            user.setId(u.getId());
            saveUserRole(user);
            return w;
        } else {//编辑
            User u = new User(user);
            u.setId(user.getId());
            u.setCreateTime(new Date());
            int k = userMapper.updateByPrimaryKeySelective(u);

            //user_role
            //先删除后添加
            userRoleMapper.deleteByUserId(user.getId());
            saveUserRole(user);
            return k;
        }
    }

    private void saveUserRole(UserVo user) {
        String[] roles = user.getRoleIds().split(",");
        if (roles.length > 0) {
            for (int i = 0; i < roles.length; i++) {
                String id = UUIDGenerator.getUUID();
                int n = userRoleMapper.insert(new UserRole(id, user.getId(), roles[i]));
            }
        }
    }

    public int delete(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public UserVo findById(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        return getUserVo(user);
    }

    public List<Map<String, Object>> findSalesAll() {
        List<Map<String, Object>> maps = userMapper.findSalesAll();
        for (Map<String, Object> map : maps) {
            map.put("checked", true);
        }
        return maps;
    }

    public List<Map<String, Object>> findAllManager() {
        List<Map<String, Object>> maps = userMapper.findAllManager();
        return maps;
    }

    public JsonResult<Integer> setPwd(Pwd pwd) {
        JsonResult<Integer> result = new JsonResult<Integer>();
        User user = userMapper.selectByPrimaryKey(pwd.getId());
        if (!pwd.getOldPwd().equalsIgnoreCase(user.getPassword())) {
            result.setState(1);
            result.setMessage("密码输入有误");
        } else {
            User user1 = new User();
            user1.setId(pwd.getId());
            user1.setPassword(pwd.getNewPwd());
            int i = userMapper.updateByPrimaryKeySelective(user1);
            result.setData(i);
            result.setState(0);
        }
        return result;
    }


    private String parseRoleNames(List<Map<String, Object>> userRoles) {
        String roleStr = "";
        if (userRoles.size() > 0) {
            for (Map<String, Object> role : userRoles) {
                String name = (String) role.get("name");
                roleStr = roleStr + name + ",";
            }
            roleStr = roleStr.substring(0, roleStr.length() - 1);
        }
        return roleStr;
    }

    private String parseRoleIds(List<Map<String, Object>> userRoles) {
        String idStr = "";
        if (userRoles.size() > 0) {
            for (Map<String, Object> role : userRoles) {
                String id = (String) role.get("role_id");
                idStr = idStr + id + ",";
            }
            idStr = idStr.substring(0, idStr.length() - 1);
        }
        return idStr;
    }
}
