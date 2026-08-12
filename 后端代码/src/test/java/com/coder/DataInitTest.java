package com.coder;

import com.coder.rental.AutoRental1Application;
import com.coder.rental.entity.*;
import com.coder.rental.mapper.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 数据库初始化测试类 - 先清空再插入，确保数据不重复
 */
@SpringBootTest(classes = AutoRental1Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataInitTest {

    @Autowired private UserMapper userMapper;
    @Autowired private DeptMapper deptMapper;
    @Autowired private PermissionMapper permissionMapper;
    @Autowired private RoleMapper roleMapper;
    @Autowired private RolePermissionMapper rolePermissionMapper;
    @Autowired private UserRoleMapper userRoleMapper;
    @Autowired private AutoMakerMapper autoMakerMapper;
    @Autowired private AutoBrandMapper autoBrandMapper;
    @Autowired private RentalTypeMapper rentalTypeMapper;
    @Autowired private CustomerMapper customerMapper;
    @Autowired private AutoInfoMapper autoInfoMapper;
    @Autowired private OrderMapper orderMapper;
    @Autowired private MaintainMapper maintainMapper;
    @Autowired private ViolationMapper violationMapper;
    @Autowired private JdbcTemplate jdbcTemplate;

        // ==================== 第1步：清空所有数据（使用TRUNCATE重置自增ID） ====================

    @Test @Order(0)
    void cleanAll() {
        System.out.println("========== 清空所有表数据 ==========");

        // 禁用外键检查，然后TRUNCATE所有表（重置自增ID）
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.execute("TRUNCATE TABLE sys_user_role");
        jdbcTemplate.execute("TRUNCATE TABLE sys_role_permission");
        jdbcTemplate.execute("TRUNCATE TABLE busi_order");
        jdbcTemplate.execute("TRUNCATE TABLE busi_maintain");
        jdbcTemplate.execute("TRUNCATE TABLE busi_violation");
        jdbcTemplate.execute("TRUNCATE TABLE auto_info");
        jdbcTemplate.execute("TRUNCATE TABLE auto_brand");
        jdbcTemplate.execute("TRUNCATE TABLE sys_user");
        jdbcTemplate.execute("TRUNCATE TABLE busi_customer");
        jdbcTemplate.execute("TRUNCATE TABLE auto_maker");
        jdbcTemplate.execute("TRUNCATE TABLE busi_rental_type");
        jdbcTemplate.execute("TRUNCATE TABLE sys_dept");
        jdbcTemplate.execute("TRUNCATE TABLE sys_role");
        jdbcTemplate.execute("TRUNCATE TABLE sys_permission");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");

        System.out.println("所有表数据已清空，自增ID已重置");
    }

    // ==================== 第2步：重新初始化 ====================

    @Test @Order(1)
    void initDept() {
        System.out.println("========== 初始化部门表 ==========");
        deptMapper.insert(new Dept().setDeptName("总经办").setPhone("010-88888888")
                .setAddress("北京市朝阳区XX大厦18层").setPid(0).setParentName("无")
                .setOrderNum(1).setDeleted(false));
        deptMapper.insert(new Dept().setDeptName("销售部").setPhone("010-88888801")
                .setAddress("北京市朝阳区XX大厦15层").setPid(1).setParentName("总经办")
                .setOrderNum(2).setDeleted(false));
        deptMapper.insert(new Dept().setDeptName("技术部").setPhone("010-88888802")
                .setAddress("北京市朝阳区XX大厦12层").setPid(1).setParentName("总经办")
                .setOrderNum(3).setDeleted(false));
        deptMapper.insert(new Dept().setDeptName("客服部").setPhone("010-88888803")
                .setAddress("北京市朝阳区XX大厦10层").setPid(1).setParentName("总经办")
                .setOrderNum(4).setDeleted(false));
        deptMapper.insert(new Dept().setDeptName("财务部").setPhone("010-88888804")
                .setAddress("北京市朝阳区XX大厦11层").setPid(1).setParentName("总经办")
                .setOrderNum(5).setDeleted(false));
        System.out.println("部门数据初始化完成，共5条");
    }

    @Test @Order(2)
    void initUser() {
        System.out.println("========== 初始化用户表 ==========");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("123456");
        LocalDateTime now = LocalDateTime.now();

        userMapper.insert(new User().setUsername("admin").setPassword(pwd)
                .setAccountNonExpired(true).setAccountNonLocked(true)
                .setCredentialsNonExpired(true).setEnabled(true)
                .setRealname("系统管理员").setNickname("超级管理员")
                .setDeptId(1).setDeptName("总经办").setGender(true)
                .setPhone("13800000001").setEmial("admin@rental.com")
                .setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=admin")
                .setIsAdmin(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        userMapper.insert(new User().setUsername("zhangsan").setPassword(pwd)
                .setAccountNonExpired(true).setAccountNonLocked(true)
                .setCredentialsNonExpired(true).setEnabled(true)
                .setRealname("张三").setNickname("小张")
                .setDeptId(2).setDeptName("销售部").setGender(true)
                .setPhone("13800000002").setEmial("zhangsan@rental.com")
                .setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=zhangsan")
                .setIsAdmin(false).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        userMapper.insert(new User().setUsername("lisi").setPassword(pwd)
                .setAccountNonExpired(true).setAccountNonLocked(true)
                .setCredentialsNonExpired(true).setEnabled(true)
                .setRealname("李四").setNickname("小李")
                .setDeptId(2).setDeptName("销售部").setGender(true)
                .setPhone("13800000003").setEmial("lisi@rental.com")
                .setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=lisi")
                .setIsAdmin(false).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        userMapper.insert(new User().setUsername("wangwu").setPassword(pwd)
                .setAccountNonExpired(true).setAccountNonLocked(true)
                .setCredentialsNonExpired(true).setEnabled(true)
                .setRealname("王五").setNickname("小王")
                .setDeptId(3).setDeptName("技术部").setGender(true)
                .setPhone("13800000004").setEmial("wangwu@rental.com")
                .setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=wangwu")
                .setIsAdmin(false).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        userMapper.insert(new User().setUsername("zhaoliu").setPassword(pwd)
                .setAccountNonExpired(true).setAccountNonLocked(true)
                .setCredentialsNonExpired(true).setEnabled(true)
                .setRealname("赵六").setNickname("小赵")
                .setDeptId(4).setDeptName("客服部").setGender(false)
                .setPhone("13800000005").setEmial("zhaoliu@rental.com")
                .setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=zhaoliu")
                .setIsAdmin(false).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        userMapper.insert(new User().setUsername("manager").setPassword(pwd)
                .setAccountNonExpired(true).setAccountNonLocked(true)
                .setCredentialsNonExpired(true).setEnabled(true)
                .setRealname("孙经理").setNickname("孙经理")
                .setDeptId(1).setDeptName("总经办").setGender(true)
                .setPhone("13800000006").setEmial("manager@rental.com")
                .setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=manager")
                .setIsAdmin(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        System.out.println("用户数据初始化完成，共6条，默认密码: 123456");
    }

    @Test @Order(3)
    void initPermission() {
        System.out.println("========== 初始化权限表 ==========");
        LocalDateTime now = LocalDateTime.now();

        // =====================================================
        // 注意：sys_permission 表使用自增ID，插入顺序必须与截图中的 id 完全一致，
        // 这样角色权限关联表(sys_role_permission)中引用的 permission_id 才能正确对应。
        // =====================================================

        // id=1 权限管理（一级菜单）
        permissionMapper.insert(new Permission().setPermissionLable("权限管理").setPid(0).setParentLabel("根目录")
                .setPermissionCode("sys:manager").setRoutePath("/system").setRouteName("system")
                .setRouteUrl("/system/system").setPermissionType(0).setIcon("component")
                .setOrderNum(1).setCreateTime(now).setUpdateTime(now).setRemark("权限管理模块").setDeleted(false));
        // id=2 用户管理（二级菜单，pid=1）
        permissionMapper.insert(new Permission().setPermissionLable("用户管理").setPid(1).setParentLabel("权限管理")
                .setPermissionCode("sys:user").setRoutePath("/userList").setRouteName("userList")
                .setRouteUrl("/system/user/userList").setPermissionType(1).setIcon("guide")
                .setOrderNum(1).setCreateTime(now).setUpdateTime(now).setRemark("用户管理页面").setDeleted(false));
        // id=3 部门管理（二级菜单，pid=1）
        permissionMapper.insert(new Permission().setPermissionLable("部门管理").setPid(1).setParentLabel("权限管理")
                .setPermissionCode("sys:dept").setRoutePath("/deptList").setRouteName("deptList")
                .setRouteUrl("/system/dept/deptList").setPermissionType(1).setIcon("guide")
                .setOrderNum(2).setCreateTime(now).setUpdateTime(now).setRemark("部门管理页面").setDeleted(false));
        // id=4 角色管理（二级菜单，pid=1）
        permissionMapper.insert(new Permission().setPermissionLable("角色管理").setPid(1).setParentLabel("权限管理")
                .setPermissionCode("sys:role").setRoutePath("/roleList").setRouteName("roleList")
                .setRouteUrl("/system/role/roleList").setPermissionType(1).setIcon("guide")
                .setOrderNum(3).setCreateTime(now).setUpdateTime(now).setRemark("角色管理页面").setDeleted(false));

        // ============ 用户管理按钮 (pid=2): id=5~8 ============
        // id=5 新增
        permissionMapper.insert(new Permission().setPermissionLable("新增").setPid(2).setParentLabel("用户管理")
                .setPermissionCode("sys:user:add").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("aa")
                .setOrderNum(1).setCreateTime(now).setUpdateTime(now).setRemark("新增按钮").setDeleted(false));
        // id=6 删除
        permissionMapper.insert(new Permission().setPermissionLable("删除").setPid(2).setParentLabel("用户管理")
                .setPermissionCode("sys:user:delete").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("dd")
                .setOrderNum(2).setCreateTime(now).setUpdateTime(now).setRemark("删除按钮").setDeleted(false));
        // id=7 修改
        permissionMapper.insert(new Permission().setPermissionLable("修改").setPid(2).setParentLabel("用户管理")
                .setPermissionCode("sys:user:edit").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("dd")
                .setOrderNum(3).setCreateTime(now).setUpdateTime(now).setRemark("修改按钮").setDeleted(false));
        // id=8 查询
        permissionMapper.insert(new Permission().setPermissionLable("查询").setPid(2).setParentLabel("用户管理")
                .setPermissionCode("sys:user:select").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("cc")
                .setOrderNum(4).setCreateTime(now).setUpdateTime(now).setRemark("查询按钮").setDeleted(false));

        // ============ 部门管理按钮 (pid=3): id=9~12 ============
        // id=9 新增
        permissionMapper.insert(new Permission().setPermissionLable("新增").setPid(3).setParentLabel("部门管理")
                .setPermissionCode("sys:dept:add").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("aa")
                .setOrderNum(1).setCreateTime(now).setUpdateTime(now).setRemark("新增按钮").setDeleted(false));
        // id=10 删除
        permissionMapper.insert(new Permission().setPermissionLable("删除").setPid(3).setParentLabel("部门管理")
                .setPermissionCode("sys:dept:delete").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("dd")
                .setOrderNum(2).setCreateTime(now).setUpdateTime(now).setRemark("删除按钮").setDeleted(false));
        // id=11 修改
        permissionMapper.insert(new Permission().setPermissionLable("修改").setPid(3).setParentLabel("部门管理")
                .setPermissionCode("sys:dept:edit").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("uu")
                .setOrderNum(3).setCreateTime(now).setUpdateTime(now).setRemark("修改按钮").setDeleted(false));
        // id=12 查询
        permissionMapper.insert(new Permission().setPermissionLable("查询").setPid(3).setParentLabel("部门管理")
                .setPermissionCode("sys:dept:select").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("cc")
                .setOrderNum(4).setCreateTime(now).setUpdateTime(now).setRemark("查询按钮").setDeleted(false));

        // id=13 日常业务（一级菜单）
        permissionMapper.insert(new Permission().setPermissionLable("日常业务").setPid(0).setParentLabel("根目录")
                .setPermissionCode("busi:manager").setRoutePath("/busi").setRouteName("busi")
                .setRouteUrl("/busi/busi").setPermissionType(0).setIcon("component")
                .setOrderNum(2).setCreateTime(now).setUpdateTime(now).setRemark("日常业务模块").setDeleted(false));
        // id=14 汽车出租（二级菜单，pid=13）
        permissionMapper.insert(new Permission().setPermissionLable("汽车出租").setPid(13).setParentLabel("日常业务")
                .setPermissionCode("sys:rental").setRoutePath("/rentalList").setRouteName("rentalList")
                .setRouteUrl("/busi/rental/rentalList").setPermissionType(1).setIcon("guide")
                .setOrderNum(1).setCreateTime(now).setUpdateTime(now).setRemark("汽车出租页面").setDeleted(false));

        // id=15 数据初始（一级菜单）
        permissionMapper.insert(new Permission().setPermissionLable("数据初始").setPid(0).setParentLabel("根目录")
                .setPermissionCode("auto:manager").setRoutePath("/auto").setRouteName("auto")
                .setRouteUrl("/auto/auto").setPermissionType(0).setIcon("component")
                .setOrderNum(3).setCreateTime(now).setUpdateTime(now).setRemark("数据初始模块").setDeleted(false));
        // id=16 车辆厂商（二级菜单，pid=15）
        permissionMapper.insert(new Permission().setPermissionLable("车辆厂商").setPid(15).setParentLabel("数据初始")
                .setPermissionCode("auto:maker").setRoutePath("/makerList").setRouteName("makerList")
                .setRouteUrl("/auto/maker/makerList").setPermissionType(1).setIcon("guide")
                .setOrderNum(1).setCreateTime(now).setUpdateTime(now).setRemark("车辆厂商页面").setDeleted(false));

        // ============ 车辆厂商按钮 (pid=16): id=17~20 ============
        // id=17 新增
        permissionMapper.insert(new Permission().setPermissionLable("新增").setPid(16).setParentLabel("车辆厂商")
                .setPermissionCode("auto:maker:add").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("aa")
                .setOrderNum(1).setCreateTime(now).setUpdateTime(now).setRemark("新增按钮").setDeleted(false));
        // id=18 删除
        permissionMapper.insert(new Permission().setPermissionLable("删除").setPid(16).setParentLabel("车辆厂商")
                .setPermissionCode("auto:maker:delete").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("dd")
                .setOrderNum(2).setCreateTime(now).setUpdateTime(now).setRemark("删除按钮").setDeleted(false));
        // id=19 修改
        permissionMapper.insert(new Permission().setPermissionLable("修改").setPid(16).setParentLabel("车辆厂商")
                .setPermissionCode("auto:maker:edit").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("uu")
                .setOrderNum(3).setCreateTime(now).setUpdateTime(now).setRemark("修改按钮").setDeleted(false));
        // id=20 查询
        permissionMapper.insert(new Permission().setPermissionLable("查询").setPid(16).setParentLabel("车辆厂商")
                .setPermissionCode("auto:maker:select").setRoutePath("").setRouteName("")
                .setRouteUrl("").setPermissionType(2).setIcon("cc")
                .setOrderNum(4).setCreateTime(now).setUpdateTime(now).setRemark("查询按钮").setDeleted(false));
        System.out.println("权限数据初始化完成，共20条");
    }

    @Test @Order(4)
    void initRole() {
        System.out.println("========== 初始化角色表 ==========");
        LocalDateTime now = LocalDateTime.now();
        roleMapper.insert(new Role().setRoleCode("ROLE_ADMIN").setRoleName("超级管理员")
                .setCreaterId(1).setCreateTime(now).setUpdateTime(now)
                .setRemark("系统最高权限角色").setDeleted(false));
        roleMapper.insert(new Role().setRoleCode("ROLE_MANAGER").setRoleName("部门经理")
                .setCreaterId(1).setCreateTime(now).setUpdateTime(now)
                .setRemark("部门管理权限角色").setDeleted(false));
        roleMapper.insert(new Role().setRoleCode("ROLE_STAFF").setRoleName("普通员工")
                .setCreaterId(1).setCreateTime(now).setUpdateTime(now)
                .setRemark("日常工作权限角色").setDeleted(false));
        System.out.println("角色数据初始化完成，共3条");
    }

    @Test @Order(5)
    void initRolePermission() {
        System.out.println("========== 初始化角色-权限关联表 ==========");
        // role_id=1 超级管理员：拥有全部 1~20 条权限
        for (int i = 1; i <= 20; i++) {
            rolePermissionMapper.insert(new RolePermission().setRoleId(1).setPermissionId(i));
        }
        // role_id=2 部门经理：权限管理 + 用户管理的新增/删除/修改
        // 拥有 1(权限管理), 2(用户管理), 5(新增), 6(删除), 7(修改)
        int[] managerPerms = {1, 2, 5, 6, 7};
        for (int pid : managerPerms) {
            rolePermissionMapper.insert(new RolePermission().setRoleId(2).setPermissionId(pid));
        }
        // role_id=3 普通员工：日常业务 + 数据初始相关（汽车出租、车辆厂商）
        // 拥有 13(日常业务), 14(汽车出租), 15(数据初始), 16(车辆厂商) 及对应查询按钮
        int[] staffPerms = {13, 14, 15, 16};
        for (int pid : staffPerms) {
            rolePermissionMapper.insert(new RolePermission().setRoleId(3).setPermissionId(pid));
        }
        System.out.println("角色权限关联数据初始化完成");
    }

    @Test @Order(6)
    void initUserRole() {
        System.out.println("========== 初始化用户-角色关联表 ==========");
        userRoleMapper.insert(new UserRole().setUserId(1).setRoleId(1));
        userRoleMapper.insert(new UserRole().setUserId(6).setRoleId(2));
        userRoleMapper.insert(new UserRole().setUserId(2).setRoleId(3));
        userRoleMapper.insert(new UserRole().setUserId(3).setRoleId(3));
        userRoleMapper.insert(new UserRole().setUserId(4).setRoleId(3));
        userRoleMapper.insert(new UserRole().setUserId(5).setRoleId(3));
        System.out.println("用户角色关联数据初始化完成，共6条");
    }

    @Test @Order(7)
    void initAutoMaker() {
        System.out.println("========== 初始化汽车厂商表 ==========");
        autoMakerMapper.insert(new AutoMaker().setName("丰田").setOrderLetter("F").setDeleted(false));
        autoMakerMapper.insert(new AutoMaker().setName("本田").setOrderLetter("B").setDeleted(false));
        autoMakerMapper.insert(new AutoMaker().setName("大众").setOrderLetter("D").setDeleted(false));
        autoMakerMapper.insert(new AutoMaker().setName("宝马").setOrderLetter("B").setDeleted(false));
        autoMakerMapper.insert(new AutoMaker().setName("奔驰").setOrderLetter("B").setDeleted(false));
        autoMakerMapper.insert(new AutoMaker().setName("比亚迪").setOrderLetter("B").setDeleted(false));
        System.out.println("汽车厂商数据初始化完成，共6条");
    }

    @Test @Order(8)
    void initAutoBrand() {
        System.out.println("========== 初始化汽车品牌表 ==========");
        autoBrandMapper.insert(new AutoBrand().setMid(1).setBrandName("卡罗拉").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(1).setBrandName("凯美瑞").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(1).setBrandName("RAV4").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(2).setBrandName("思域").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(2).setBrandName("雅阁").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(2).setBrandName("CR-V").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(3).setBrandName("朗逸").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(3).setBrandName("帕萨特").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(3).setBrandName("途观").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(4).setBrandName("3系").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(4).setBrandName("5系").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(4).setBrandName("X3").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(5).setBrandName("C级").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(5).setBrandName("E级").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(6).setBrandName("汉").setDeleted(false));
        autoBrandMapper.insert(new AutoBrand().setMid(6).setBrandName("唐").setDeleted(false));
        System.out.println("汽车品牌数据初始化完成，共16条");
    }

    @Test @Order(9)
    void initAutoInfo() {
        System.out.println("========== 初始化车辆信息表 ==========");
        LocalDateTime now = LocalDateTime.now();

        autoInfoMapper.insert(new AutoInfo().setAutoNum("京A88888").setMakerId(4).setBrandId(10)
                .setInfoType(false).setColor("白色").setDisplacement(2.0).setUnit("L")
                .setMileage(35000).setRent(500).setRegistrationDate(LocalDate.of(2022, 3, 15))
                .setPic("https://example.com/car/bmw3.jpg").setDeposit(5000)
                .setStatus(false).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(12).setActualNum(8).setDeleted(false));
        autoInfoMapper.insert(new AutoInfo().setAutoNum("京B66666").setMakerId(5).setBrandId(13)
                .setInfoType(false).setColor("黑色").setDisplacement(2.0).setUnit("L")
                .setMileage(28000).setRent(600).setRegistrationDate(LocalDate.of(2022, 6, 20))
                .setPic("https://example.com/car/benze.jpg").setDeposit(6000)
                .setStatus(false).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(12).setActualNum(10).setDeleted(false));
        autoInfoMapper.insert(new AutoInfo().setAutoNum("京C12345").setMakerId(1).setBrandId(2)
                .setInfoType(false).setColor("银色").setDisplacement(2.5).setUnit("L")
                .setMileage(52000).setRent(400).setRegistrationDate(LocalDate.of(2021, 11, 8))
                .setPic("https://example.com/car/camry.jpg").setDeposit(4000)
                .setStatus(false).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(12).setActualNum(12).setDeleted(false));
        autoInfoMapper.insert(new AutoInfo().setAutoNum("京D55555").setMakerId(2).setBrandId(5)
                .setInfoType(false).setColor("蓝色").setDisplacement(1.5).setUnit("L")
                .setMileage(42000).setRent(350).setRegistrationDate(LocalDate.of(2023, 1, 10))
                .setPic("https://example.com/car/accord.jpg").setDeposit(3500)
                .setStatus(false).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(8).setActualNum(6).setDeleted(false));
        autoInfoMapper.insert(new AutoInfo().setAutoNum("京E77777").setMakerId(3).setBrandId(8)
                .setInfoType(false).setColor("黑色").setDisplacement(1.8).setUnit("L")
                .setMileage(18000).setRent(450).setRegistrationDate(LocalDate.of(2023, 8, 5))
                .setPic("https://example.com/car/passat.jpg").setDeposit(4500)
                .setStatus(true).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(6).setActualNum(5).setDeleted(false));
        autoInfoMapper.insert(new AutoInfo().setAutoNum("京F99999").setMakerId(3).setBrandId(7)
                .setInfoType(false).setColor("白色").setDisplacement(1.6).setUnit("L")
                .setMileage(65000).setRent(300).setRegistrationDate(LocalDate.of(2019, 5, 22))
                .setPic("https://example.com/car/lavida.jpg").setDeposit(3000)
                .setStatus(false).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(20).setActualNum(16).setDeleted(false));
        autoInfoMapper.insert(new AutoInfo().setAutoNum("京G11111").setMakerId(6).setBrandId(16)
                .setInfoType(true).setColor("红色").setDisplacement(0.0).setUnit("电动")
                .setMileage(12000).setRent(400).setRegistrationDate(LocalDate.of(2024, 3, 1))
                .setPic("https://example.com/car/tang.jpg").setDeposit(4000)
                .setStatus(false).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(4).setActualNum(3).setDeleted(false));
        autoInfoMapper.insert(new AutoInfo().setAutoNum("京H22222").setMakerId(1).setBrandId(3)
                .setInfoType(false).setColor("绿色").setDisplacement(2.0).setUnit("L")
                .setMileage(8000).setRent(380).setRegistrationDate(LocalDate.of(2024, 10, 18))
                .setPic("https://example.com/car/rav4.jpg").setDeposit(3800)
                .setStatus(false).setCreateTime(now).setUpdateTime(now)
                .setExpectedNum(2).setActualNum(2).setDeleted(false));
        System.out.println("车辆信息数据初始化完成，共8条");
    }

    @Test @Order(10)
    void initCustomer() {
        System.out.println("========== 初始化客户表 ==========");
        LocalDateTime now = LocalDateTime.now();
        customerMapper.insert(new Customer().setName("赵强").setAge(28).setTel("13900000001")
                .setBirthday(LocalDate.of(1996, 5, 12)).setIdNum("110101199605121234")
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        customerMapper.insert(new Customer().setName("钱丽").setAge(32).setTel("13900000002")
                .setBirthday(LocalDate.of(1992, 8, 25)).setIdNum("110101199208251235")
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        customerMapper.insert(new Customer().setName("孙明").setAge(25).setTel("13900000003")
                .setBirthday(LocalDate.of(1999, 1, 6)).setIdNum("110101199901061236")
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        customerMapper.insert(new Customer().setName("李华").setAge(35).setTel("13900000004")
                .setBirthday(LocalDate.of(1989, 11, 30)).setIdNum("110101198911301237")
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        customerMapper.insert(new Customer().setName("周杰").setAge(40).setTel("13900000005")
                .setBirthday(LocalDate.of(1984, 3, 18)).setIdNum("110101198403181238")
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        customerMapper.insert(new Customer().setName("吴芳").setAge(22).setTel("13900000006")
                .setBirthday(LocalDate.of(2002, 7, 9)).setIdNum("110101200207091239")
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        customerMapper.insert(new Customer().setName("郑黑").setAge(30).setTel("13900000007")
                .setBirthday(LocalDate.of(1994, 4, 1)).setIdNum("110101199404011240")
                .setStatus(false).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        System.out.println("客户数据初始化完成，共7条");
    }

    @Test @Order(11)
    void initRentalType() {
        System.out.println("========== 初始化出租类型表 ==========");
        LocalDateTime now = LocalDateTime.now();
        rentalTypeMapper.insert(new RentalType().setTypeName("日租").setTypeDiscout(1.00)
                .setRemark("按天计费，无折扣").setCreateTime(now).setUpdateTime(now).setDeleted(false));
        rentalTypeMapper.insert(new RentalType().setTypeName("周租").setTypeDiscout(0.88)
                .setRemark("7天以上享受88折优惠").setCreateTime(now).setUpdateTime(now).setDeleted(false));
        rentalTypeMapper.insert(new RentalType().setTypeName("月租").setTypeDiscout(0.75)
                .setRemark("30天以上享受75折优惠").setCreateTime(now).setUpdateTime(now).setDeleted(false));
        rentalTypeMapper.insert(new RentalType().setTypeName("长租").setTypeDiscout(0.60)
                .setRemark("90天以上享受6折优惠").setCreateTime(now).setUpdateTime(now).setDeleted(false));
        rentalTypeMapper.insert(new RentalType().setTypeName("VIP租").setTypeDiscout(0.50)
                .setRemark("VIP客户专属5折优惠").setCreateTime(now).setUpdateTime(now).setDeleted(false));
        System.out.println("出租类型数据初始化完成，共5条");
    }

    @Test @Order(12)
    void initOrder() {
        System.out.println("========== 初始化订单表 ==========");
        LocalDateTime now = LocalDateTime.now();

        orderMapper.insert(new com.coder.rental.entity.Order().setOrderNum("ORD20240601001").setAutoId(1).setCustomerId(1)
                .setRentalTime(LocalDateTime.of(2024, 6, 1, 10, 0))
                .setTypeId(1).setRent(500).setDeposit(5000).setMileage(35000)
                .setReturnTime(LocalDateTime.of(2024, 6, 3, 10, 0))
                .setReturnMileage(35500).setRentPayable(1000).setRentActual(1000)
                .setDepositReturn(true).setCreateTime(now.minusMonths(2))
                .setUpdateTime(now.minusMonths(2)).setDeleted(false));
        orderMapper.insert(new com.coder.rental.entity.Order().setOrderNum("ORD20240602002").setAutoId(2).setCustomerId(2)
                .setRentalTime(LocalDateTime.of(2024, 6, 2, 14, 0))
                .setTypeId(1).setRent(600).setDeposit(6000).setMileage(28000)
                .setReturnTime(LocalDateTime.of(2024, 6, 5, 14, 0))
                .setReturnMileage(28500).setRentPayable(1800).setRentActual(1800)
                .setDepositReturn(false).setCreateTime(now.minusMonths(2))
                .setUpdateTime(now.minusMonths(2)).setDeleted(false));
        orderMapper.insert(new com.coder.rental.entity.Order().setOrderNum("ORD20240703003").setAutoId(5).setCustomerId(3)
                .setRentalTime(LocalDateTime.of(2024, 7, 3, 9, 0))
                .setTypeId(2).setRent(450).setDeposit(4500).setMileage(18000)
                .setReturnTime(null).setReturnMileage(null)
                .setRentPayable(null).setRentActual(null)
                .setDepositReturn(null).setCreateTime(now.minusMonths(1))
                .setUpdateTime(now.minusMonths(1)).setDeleted(false));
        orderMapper.insert(new com.coder.rental.entity.Order().setOrderNum("ORD20240710004").setAutoId(3).setCustomerId(4)
                .setRentalTime(LocalDateTime.of(2024, 7, 10, 11, 0))
                .setTypeId(2).setRent(400).setDeposit(4000).setMileage(52000)
                .setReturnTime(LocalDateTime.of(2024, 7, 20, 11, 0))
                .setReturnMileage(53500).setRentPayable(3520).setRentActual(3520)
                .setDepositReturn(true).setCreateTime(now.minusMonths(1))
                .setUpdateTime(now.minusWeeks(2)).setDeleted(false));
        orderMapper.insert(new com.coder.rental.entity.Order().setOrderNum("ORD20240715005").setAutoId(4).setCustomerId(5)
                .setRentalTime(LocalDateTime.of(2024, 7, 15, 8, 0))
                .setTypeId(3).setRent(350).setDeposit(3500).setMileage(42000)
                .setReturnTime(LocalDateTime.of(2024, 8, 18, 8, 0))
                .setReturnMileage(45500).setRentPayable(8925).setRentActual(8925)
                .setDepositReturn(true).setCreateTime(now.minusMonths(1))
                .setUpdateTime(now.minusMonths(1)).setDeleted(false));
        orderMapper.insert(new com.coder.rental.entity.Order().setOrderNum("ORD20250106006").setAutoId(7).setCustomerId(6)
                .setRentalTime(LocalDateTime.of(2025, 1, 6, 9, 30))
                .setTypeId(4).setRent(400).setDeposit(4000).setMileage(12000)
                .setReturnTime(null).setReturnMileage(null)
                .setRentPayable(null).setRentActual(null)
                .setDepositReturn(null).setCreateTime(now.minusMonths(7))
                .setUpdateTime(now.minusMonths(1)).setDeleted(false));
        System.out.println("订单数据初始化完成，共6条");
    }

    @Test @Order(13)
    void initMaintain() {
        System.out.println("========== 初始化维保记录表 ==========");
        LocalDateTime now = LocalDateTime.now();
        maintainMapper.insert(new Maintain().setAutoId(1).setMaintainTime(LocalDate.of(2024, 3, 15))
                .setLocation("宝马4S店-朝阳店").setItem("常规保养（机油+机滤+空滤）")
                .setCost(1200).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        maintainMapper.insert(new Maintain().setAutoId(1).setMaintainTime(LocalDate.of(2024, 9, 20))
                .setLocation("宝马4S店-朝阳店").setItem("更换刹车片+刹车油")
                .setCost(2800).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        maintainMapper.insert(new Maintain().setAutoId(2).setMaintainTime(LocalDate.of(2024, 5, 10))
                .setLocation("奔驰4S店-海淀店").setItem("常规保养（机油+机滤）")
                .setCost(1500).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        maintainMapper.insert(new Maintain().setAutoId(3).setMaintainTime(LocalDate.of(2024, 2, 28))
                .setLocation("丰田4S店-丰台店").setItem("更换轮胎（4条）")
                .setCost(3200).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        maintainMapper.insert(new Maintain().setAutoId(6).setMaintainTime(LocalDate.of(2024, 8, 5))
                .setLocation("大众4S店-东城店").setItem("空调系统维修")
                .setCost(1800).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        maintainMapper.insert(new Maintain().setAutoId(6).setMaintainTime(LocalDate.of(2025, 1, 12))
                .setLocation("大众4S店-东城店").setItem("常规保养+更换火花塞")
                .setCost(900).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        System.out.println("维保记录数据初始化完成，共6条");
    }

    @Test @Order(14)
    void initViolation() {
        System.out.println("========== 初始化违章记录表 ==========");
        LocalDateTime now = LocalDateTime.now();
        violationMapper.insert(new Violation().setAutoId(1)
                .setViolationTime(LocalDateTime.of(2024, 4, 10, 14, 30))
                .setReason("超速行驶（限速80km/h，实际95km/h）")
                .setLocation("京藏高速K15+200m").setDeductPoints(3).setFine(200)
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        violationMapper.insert(new Violation().setAutoId(3)
                .setViolationTime(LocalDateTime.of(2024, 5, 22, 8, 15))
                .setReason("违反禁止标线指示")
                .setLocation("北京市朝阳区建国路").setDeductPoints(1).setFine(100)
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        violationMapper.insert(new Violation().setAutoId(4)
                .setViolationTime(LocalDateTime.of(2024, 6, 8, 16, 45))
                .setReason("违规停车")
                .setLocation("北京市海淀区中关村大街").setDeductPoints(0).setFine(200)
                .setStatus(true).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        violationMapper.insert(new Violation().setAutoId(6)
                .setViolationTime(LocalDateTime.of(2024, 7, 1, 11, 0))
                .setReason("闯红灯")
                .setLocation("北京市东城区安定门外大街").setDeductPoints(6).setFine(200)
                .setStatus(false).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        violationMapper.insert(new Violation().setAutoId(6)
                .setViolationTime(LocalDateTime.of(2024, 9, 15, 17, 20))
                .setReason("不按导向车道行驶")
                .setLocation("北京市丰台区南三环").setDeductPoints(2).setFine(100)
                .setStatus(false).setCreateTime(now).setUpdateTime(now).setDeleted(false));
        System.out.println("违章记录数据初始化完成，共5条");
    }
}
