package tk.mybatis.springboot.modules.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.springboot.modules.sys.mapper.SysLogMapper;
import tk.mybatis.springboot.modules.sys.model.SysLogModel;
import tk.mybatis.springboot.modules.sys.vo.UserVo;

import java.util.List;

@Service
public class SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    public PageInfo<SysLogModel> findAll(SysLogModel sysLog, UserVo userVo) {
        PageHelper.startPage(sysLog.getPage(), sysLog.getRows());
        Example example = new Example(SysLogModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (sysLog.getCreateTime() != null) {
            criteria.andEqualTo("createTime", sysLog.getCreateTime());
        }
        if (userVo != null) {
            if (!"admin".equalsIgnoreCase(userVo.getRoles())) {
                criteria.andEqualTo("userName", userVo.getName());
            }
        }
        Example.OrderBy orderBy = example.orderBy("createTime");
        orderBy.desc();
        List<SysLogModel> list = sysLogMapper.selectByExample(example);
        return new PageInfo<SysLogModel>(list);
    }

    @Transactional
    public void save(SysLogModel sysLog) {
        sysLogMapper.insert(sysLog);
    }

}
