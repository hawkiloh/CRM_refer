package tk.mybatis.springboot.modules.customer.mapper;


import tk.mybatis.springboot.modules.customer.model.CustomerDrains;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;

public interface CustomerDrainsMapper extends MyMapper<CustomerDrains> {
    List<CustomerDrains> findAll(CustomerDrains customerDrains);

    CustomerDrains findById(String id);
}