package tk.mybatis.springboot.modules.customer.mapper;


import tk.mybatis.springboot.modules.customer.model.Plan;
import tk.mybatis.springboot.common.util.MyMapper;

import java.util.List;

public interface PlanMapper extends MyMapper<Plan> {
    List<Plan> findAllPlan(Plan plan);

}