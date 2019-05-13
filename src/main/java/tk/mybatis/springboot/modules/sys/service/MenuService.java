package tk.mybatis.springboot.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.springboot.modules.sys.mapper.MenuMapper;
import tk.mybatis.springboot.modules.sys.mapper.RoleResourceMapper;
import tk.mybatis.springboot.modules.sys.model.Resource;
import tk.mybatis.springboot.modules.sys.vo.*;
import tk.mybatis.springboot.common.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 菜单列表
     *
     * @return
     */
    public List<MenuVo> findMenu(UserVo userVo) {
        List<MenuVo> menuVos = new ArrayList<MenuVo>();
        String roles = userVo.getRoles();
        String roleIds = userVo.getRoleIds();
        if ("".equalsIgnoreCase(roles) || roles == null) {
            return menuVos;
        } else if (roles.contains("admin")) {
            Integer resourceType = 0;
            Resource resourceExample = new Resource();
            resourceExample.setResourceType(resourceType);
            List<Resource> resourceList = menuMapper.select(resourceExample);
            for (Resource resource : resourceList) {
                MenuVo menuVo = new MenuVo(resource);
                menuVos.add(menuVo);
            }
            return menuVos;
        } else {
            //根据roleId列表找到对应权限的资源列表
            List<String> listStr = Arrays.asList(roleIds);
            //List<String> btnList=roleResourceMapper.batchSelect(listStr);

            //再根据资源列表找到对应的父级菜单
            List<Resource> resources = roleResourceMapper.getMenusNotButtonByRoleIds(listStr);//查询子菜单列表
            //List<String> menuIds=new ArrayList<String>();
            /*for(Resource resource:resources){
                menuIds.add(resource.getId());
            }*/
            //List<Resource> menus=menuMapper.batchSelect(menuIds);//查询根菜单列表
            //resources.addAll(menus);
            for (Resource resource : resources) {
                MenuVo menuVo = new MenuVo(resource);
                menuVos.add(menuVo);
            }
            return menuVos;
        }
    }

    public List<MenuListVo> findMenuList() {
        List<Resource> resourceList = menuMapper.selectAll();
        List<MenuListVo> mainList = new ArrayList<MenuListVo>();//主菜单
        List<MenuListVo> subList = new ArrayList<MenuListVo>();//子菜单
        List<MenuListVo> funcList = new ArrayList<MenuListVo>();//功能菜单
        //分类别
        for (Resource resource : resourceList) {
            if ("".equalsIgnoreCase(resource.getPid()) || resource.getPid() == null) {//主菜单
                mainList.add(new MenuListVo(resource));
            } else if (resource.getResourceType() == 0) {//子菜单
                subList.add(new MenuListVo(resource));
            } else {//功能菜单
                funcList.add(new MenuListVo(resource));
            }
        }
        parseParentAndChilren(subList, funcList);
        parseParentAndChilren(mainList, subList);
        return mainList;
    }

    public List<MenuRightVo> findRightList(String id) {
        List<Resource> resourceList = menuMapper.selectAll();
        List<MenuRightVo> mainList = new ArrayList<MenuRightVo>();//主菜单
        List<RightChildVo> funcList = new ArrayList<RightChildVo>();//功能菜单
        //分类别
        for (Resource resource : resourceList) {
            if ("".equalsIgnoreCase(resource.getPid()) || resource.getPid() == null || resource.getResourceType() == 0) {//主菜单 //子菜单
                mainList.add(new MenuRightVo(resource));
            } else {//功能菜单
                funcList.add(new RightChildVo(resource));
            }
        }

        //根据角色ID查询具有权限的菜单IDs
        List<String> menuIds = menuMapper.findMenuIds(id);
        parseRightParentAndChilren(mainList, funcList, menuIds);
        return mainList;
    }


    public int saveMenu(Resource r) {
        int i = 0;
        if ("".equalsIgnoreCase(r.getId()) || r.getId() == null) {//新增
            //根据pid查询最大的Id，暂不做

            r.setId(UUIDGenerator.getUUID());
            r.setDescription(r.getName());
            r.setCreateTime(new Date());
            i = menuMapper.insert(r);
        } else {//编辑
            r.setCreateTime(new Date());
            i = menuMapper.updateByPrimaryKeySelective(r);
        }
        return i;
    }

    public Resource findById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public int deleteMenu(String ids) {
        List<String> idList = new ArrayList<String>();
        String[] arr = ids.split(",");
        for (int i = 0; i < arr.length; i++) {
            String id = arr[i];
            if (!idList.contains(id)) {
                idList.add(id);
            }
            List<String> childrenMenuIds = menuMapper.findChildrenMenuId(id);
            if (childrenMenuIds.size() > 0) {
                idList = parseMenuIsContains(idList, childrenMenuIds);
            }
        }
        return menuMapper.batchRemoveMenus(idList);
    }


    private List<String> parseMenuIsContains(List<String> idList, List<String> childrenMenuIds) {
        for (String child : childrenMenuIds) {
            if (!idList.contains(child)) {
                idList.add(child);
            }
        }
        return idList;
    }

    private void parseParentAndChilren(List<MenuListVo> mainList, List<MenuListVo> subList) {
        for (MenuListVo menuVo : mainList) {
            List<MenuListVo> list = new ArrayList<MenuListVo>();

            String id = menuVo.getId();

            for (MenuListVo menu : subList) {

                if (id.equalsIgnoreCase(menu.getPid())) {
                    list.add(menu);
                }
            }
            menuVo.setChildren(list);
        }
    }

    private void parseRightParentAndChilren(List<MenuRightVo> mainList, List<RightChildVo> subList, List<String> menuIds) {
        for (MenuRightVo menuVo : mainList) {
            if (subList.size() > 0) {
                List<RightChildVo> list = new ArrayList<RightChildVo>();
                String id = menuVo.getId();
                for (RightChildVo menu : subList) {
                    if (id.equalsIgnoreCase(menu.getPid())) {
                        if (menuIds.contains(menu.getId())) {
                            menu.setChecked(true);
                        }
                        list.add(menu);
                    }
                }
                menuVo.setFunctions(list);
            }
        }
    }

    public List<String> getSelectedMenuIds(String roleId) {
        List<String> roleIds = new ArrayList();
        roleIds.add(roleId);
        List<String> menuIds = new ArrayList<>();

        List<Resource> list = roleResourceMapper.getMenusNotButtonByRoleIds(roleIds);
        if (list.size() > 0) {
            for (Resource rr : list) {
                menuIds.add(rr.getId());
            }
        }
        return menuIds;
    }
}
