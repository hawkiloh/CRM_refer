package tk.mybatis.springboot.modules.customer.mapper;

import java.util.List;
import java.util.Map;

public interface ReportMapper {


    List<Map<String, Object>> CountCustomerlevel();

    List<Map<String, Object>> CountCustomerSatify();

    List<Map<String, Object>> CountCustomerCredit();
}