package tk.mybatis.springboot.modules.customer.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.modules.customer.mapper.ReportMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;


    public List<List<Map<String, Object>>> CountCustomer() {
        List<List<Map<String, Object>>> list = new ArrayList<List<Map<String, Object>>>();
        List<Map<String, Object>> list1 = reportMapper.CountCustomerlevel();
        List<Map<String, Object>> list2 = reportMapper.CountCustomerSatify();
        List<Map<String, Object>> list3 = reportMapper.CountCustomerCredit();
        list.add(list1);
        list.add(list2);
        list.add(list3);
        return list;
    }
}
