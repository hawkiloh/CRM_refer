package tk.mybatis.springboot.modules.customer.mapper;

import tk.mybatis.springboot.modules.customer.model.Customer;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;


public interface CustomerMapper extends MyMapper<Customer> {

    List<Customer> findAll(Customer customer);

    List<Customer> findAllByBirthday(Customer customer);
}