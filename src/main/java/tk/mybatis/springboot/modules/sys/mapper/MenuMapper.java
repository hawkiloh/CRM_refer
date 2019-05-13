package tk.mybatis.springboot.modules.sys.mapper;


import org.apache.ibatis.annotations.Param;
import tk.mybatis.springboot.modules.sys.model.Resource;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;

public interface MenuMapper extends MyMapper<Resource> {

    List<String> findChildrenMenuId(@Param("id") String id);

    int batchRemoveMenus(List<String> list);

    List<String> findMenuIds(String id);

    List<Resource> batchSelect(List<String> list);
}