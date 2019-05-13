package tk.mybatis.springboot.modules.sys.mapper;


import tk.mybatis.springboot.modules.sys.model.Resource;
import tk.mybatis.springboot.modules.sys.model.RoleResource;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;

public interface RoleResourceMapper extends MyMapper<RoleResource> {

    int remove(String roleId);

    List<String> batchSelect(List<String> list);

    List<Resource> getMenusNotButtonByRoleIds(List<String> list);
}