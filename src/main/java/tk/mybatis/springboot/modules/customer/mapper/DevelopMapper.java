package tk.mybatis.springboot.modules.customer.mapper;


import tk.mybatis.springboot.modules.customer.model.Develop;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;

public interface DevelopMapper extends MyMapper<Develop> {
    List<Develop> findAllByReview(Develop develop);

    /*List<Develop> findAll(Develop develop);

    int delete(String id);

    Develop findById(String id);

    int editDevelop(Develop develop);

    int saveDevelop(Develop develop);*/
}