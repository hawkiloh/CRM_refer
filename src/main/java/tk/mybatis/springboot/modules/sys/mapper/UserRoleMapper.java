package tk.mybatis.springboot.modules.sys.mapper;


import org.apache.ibatis.annotations.Param;
import tk.mybatis.springboot.modules.sys.model.UserRole;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper extends MyMapper<UserRole> {

    List<Map<String, Object>> findById(@Param("id") String id);

    int deleteByUserId(String id);
}