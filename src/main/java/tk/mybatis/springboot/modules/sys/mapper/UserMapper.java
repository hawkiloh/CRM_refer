package tk.mybatis.springboot.modules.sys.mapper;


import tk.mybatis.springboot.modules.sys.model.User;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends MyMapper<User> {

    List<Map<String, Object>> findSalesAll();

    List<Map<String, Object>> findAllManager();
}