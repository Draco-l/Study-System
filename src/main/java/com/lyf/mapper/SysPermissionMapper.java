    package com.lyf.mapper;

    import com.github.pagehelper.Page;
    import com.lyf.entity.SysPermission;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    /**
     * 权限数据的增删改查
     */
    public interface SysPermissionMapper {
        /**
         * 添加权限信息
         * @param permission 权限数据
         */
        void insert(SysPermission permission);

        /**
         * 修改权限数据
         * @param permission
         */
        void update(SysPermission permission);

        /**
         * 删除权限
         * @param id
         */
        void delete(Long id);

        /**
         * 分页查询
         * @param queryString
         * @return
         */
        Page<SysPermission> findPage(String queryString);

        /**
         * 根据角色id查询该角色下的权限信息
         * @param id
         * @return
         */
        List<SysPermission> finByRoleId(@Param("roleId") Long id);

        /**
         * 查询所有权限数据
         * @return
         */
        List<SysPermission> findAll();
    }
