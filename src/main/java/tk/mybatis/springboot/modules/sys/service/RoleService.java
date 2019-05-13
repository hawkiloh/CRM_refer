package tk.mybatis.springboot.modules.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.springboot.modules.sys.mapper.RoleMapper;
import tk.mybatis.springboot.modules.sys.mapper.RoleResourceMapper;
import tk.mybatis.springboot.modules.sys.model.Role;
import tk.mybatis.springboot.modules.sys.model.RoleResource;
import tk.mybatis.springboot.common.util.UUIDGenerator;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    public PageInfo<Role> findRoleList(Role role) {
        PageHelper.startPage(role.getPage(), role.getRows());
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        if (role.getName() != null && role.getName().length() > 0) {
            criteria.andLike("name", "%" + role.getName() + "%");
        }
        List<Role> list = roleMapper.selectByExample(example);
        return new PageInfo<Role>(list);
    }

    public int saveRole(Role role) {
        int i = 0;
        if ("".equalsIgnoreCase(role.getId()) || role.getId() == null) {//新增
            role.setId(UUIDGenerator.getUUID());
            i = roleMapper.insert(role);
        } else {//编辑
            i = roleMapper.updateByPrimaryKeySelective(role);
        }
        return i;
    }

    public Role findById(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public int delete(String id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    public List<Role> getAll(Role role) {
        return roleMapper.select(role);
    }

    public int saveRightList(String ids, String roleId) {
        int i = 0;
        //先删除roleId的所有权限，再添加
        int k = roleResourceMapper.remove(roleId);
        if (ids.length() > 0) {
            String[] idArr = ids.split(",");
            for (int n = 0; n < idArr.length; n++) {
                String resourceId = idArr[n];
                i += roleResourceMapper.insert(new RoleResource(UUIDGenerator.getUUID(), roleId, resourceId));
            }
        }
        return i;
    }

}
