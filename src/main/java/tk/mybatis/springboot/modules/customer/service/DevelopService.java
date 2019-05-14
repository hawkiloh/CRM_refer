package tk.mybatis.springboot.modules.customer.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.springboot.modules.customer.mapper.DevelopMapper;
import tk.mybatis.springboot.modules.customer.mapper.PlanMapper;
import tk.mybatis.springboot.modules.sys.mapper.UserMapper;
import tk.mybatis.springboot.modules.customer.model.Develop;
import tk.mybatis.springboot.modules.customer.model.Plan;
import tk.mybatis.springboot.modules.sys.model.User;
import tk.mybatis.springboot.modules.sys.vo.UserVo;
import tk.mybatis.springboot.common.util.UUIDGenerator;

import java.util.Date;
import java.util.List;

@Service
public class DevelopService {

    @Autowired
    private DevelopMapper developMapper;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private UserMapper userMapper;

    public PageInfo<Develop> findAll(Develop develop) {
        PageHelper.startPage(develop.getPage(), develop.getRows());
        Example example = new Example(Develop.class);
        Example.Criteria criteria = example.createCriteria();
        if (develop.getCustName() != null && develop.getCustName().length() > 0) {
            criteria.andLike("custName", "%" + develop.getCustName() + "%");
        }
        List<Develop> list = developMapper.selectByExample(example);
        return new PageInfo<Develop>(list);
    }

    public PageInfo<Plan> findAllPlan(Plan plan) {
        PageHelper.startPage(plan.getPage(), plan.getRows());
        List<Plan> list = planMapper.findAllPlan(plan);
        return new PageInfo<Plan>(list);
    }

    public Plan findPlanById(String id) {
        return planMapper.selectByPrimaryKey(id);
    }

    public int savePlan(Plan plan) {
        plan.setResult("开发中");
        int i = planMapper.updateByPrimaryKeySelective(plan);
        return i;
    }

    public int delete(String id) {
        return developMapper.deleteByPrimaryKey(id);
    }

    public Develop findById(String id) {
        return developMapper.selectByPrimaryKey(id);
    }

    public int savedevelop(Develop develop, UserVo user) {

        int i;
        if (develop.getId() != null && !"".equalsIgnoreCase(develop.getId())) {//编辑

            if (develop.getDesignee() != null && !"".equalsIgnoreCase(develop.getDesignee())) {
                develop.setDesigneeDate(new Date());
                develop.setStatus("已分配");
                i = developMapper.updateByPrimaryKeySelective(develop);
                if (i > 0) {
                    Plan plan = new Plan();
                    plan.setId(UUIDGenerator.getUUID());
                    plan.setResult("未开发");
                    plan.setSaleChanceId(develop.getId());
                    planMapper.insert(plan);
                }
            } else {
                i = developMapper.updateByPrimaryKeySelective(develop);
            }

        } else {//新增
            develop.setId(UUIDGenerator.getUUID());
            develop.setCreateBy(user.getLoginName());
//            前端编辑日期
//            develop.setCreateDate(new Date());
            develop.setStatus("未分配");
            i = developMapper.insert(develop);
        }
        return i;
    }

    public String findUserId(String id) {
        Develop develop = developMapper.selectByPrimaryKey(id);
        String loginName = develop.getDesignee();
        if ("".equalsIgnoreCase(loginName) || loginName == null) {
            return "";
        }
        User user = new User();
        user.setLoginName(loginName);
        List<User> users = userMapper.select(user);
        String userId = users.get(0).getId();
        return userId;
    }

    public int verRow(String id, String op) {
        String status = "开发失败";
        if ("开发成功".equalsIgnoreCase(op)) {
            status = "开发成功";
        }
        Plan plan = new Plan();
        plan.setId(id);
        plan.setResult(status);
        return planMapper.updateByPrimaryKeySelective(plan);
    }


    /**
     * 根据创建日期查找订单
     * @param develop
     * @return
     */
    public PageInfo<Develop> findAllByReview(Develop develop) {
        PageHelper.startPage(develop.getPage(), develop.getRows());
        List<Develop> list = developMapper.findAllByReview(develop);

        return new PageInfo<Develop>(list);
    }
}
