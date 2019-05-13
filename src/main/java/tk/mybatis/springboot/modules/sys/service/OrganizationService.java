package tk.mybatis.springboot.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.modules.sys.mapper.OrganizationMapper;
import tk.mybatis.springboot.modules.sys.model.Organization;
import tk.mybatis.springboot.modules.sys.vo.OrganizationVo;
import tk.mybatis.springboot.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;


    public List<OrganizationVo> findAll() {
        List<OrganizationVo> vos = new ArrayList<OrganizationVo>();
        List<Organization> list = organizationMapper.selectAll();
        for (Organization org : list) {
            vos.add(new OrganizationVo(org));
        }
        return vos;
    }

    public int saveOrganization(Organization organization) {
        int n = 0;
        if (organization.getId() == null || "".equalsIgnoreCase(organization.getId())) {
            String id = UUIDGenerator.getUUID();
            organization.setId(id);
            organization.setCreateTime(new Date());
            n = organizationMapper.insert(organization);

        } else {
            n = organizationMapper.updateByPrimaryKeySelective(organization);
        }
        return n;
    }

    public Organization findById(String id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

    public int delete(String id) {
        return organizationMapper.deleteByPrimaryKey(id);
    }
}
