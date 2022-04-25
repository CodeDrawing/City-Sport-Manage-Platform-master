package top.codezx.system.controller;



import com.github.pagehelper.PageInfo;
import top.codezx.common.constant.ControllerConstant;
import top.codezx.common.plugin.logging.aop.annotation.Logging;
import top.codezx.common.plugin.logging.aop.enums.BusinessType;
import top.codezx.common.plugin.submit.annotation.RepeatSubmit;
import top.codezx.common.tools.SecurityUtil;
import top.codezx.common.tools.SequenceUtil;
import top.codezx.common.web.base.BaseController;
import top.codezx.common.web.domain.request.PageDomain;
import top.codezx.common.web.domain.response.Result;
import top.codezx.common.web.domain.response.module.ResultTable;
import top.codezx.common.web.domain.response.module.ResultTree;
import top.codezx.system.domain.SysDept;
import top.codezx.system.domain.SysUser;
import top.codezx.system.domain.SysUsers;
import top.codezx.system.domain.vo.EditPassword;
import top.codezx.system.service.ISysLogService;
import top.codezx.system.service.ISysRoleService;
import top.codezx.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.codezx.system.service.ISysUsersService;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Describe: 用户控制器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@Api(tags = {"用户管理"})
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "user")
public class SysUserController extends BaseController {
    @Resource
    private ISysUsersService iSysUsersService;
    /**
     * Describe: 基础路径
     */
    private static String MODULE_PATH = "system/user/";

    /**
     * Describe: 用户模块服务
     */
    @Resource
    private ISysUserService sysUserService;

    /**
     * Describe: 角色模块服务
     */
    @Resource
    private ISysRoleService sysRoleService;

    /**
     * Describe: 日志模块服务
     */
    @Resource
    private ISysLogService sysLogService;

    /**
     * Describe: 获取用户列表视图
     * Param ModelAndView
     * Return 用户列表视图
     */
    @GetMapping("main")
    @ApiOperation(value = "获取用户列表视图")
    @PreAuthorize("hasPermission('/system/user/main','sys:user:main')")
    public ModelAndView main() {
        return jumpPage(MODULE_PATH + "main");
    }

    /**
     * Describe: 获取用户列表数据
     * Param ModelAndView
     * Return 用户列表数据
     */
    @GetMapping("data")
    @ApiOperation(value = "获取用户列表数据")
    @PreAuthorize("hasPermission('/system/user/data','sys:user:data')")
    @Logging(title = "查询用户", describe = "查询用户", type = BusinessType.QUERY)
    public ResultTable data(PageDomain pageDomain, SysUser param) {
        PageInfo<SysUser> pageInfo = sysUserService.page(param, pageDomain);
        return pageTable(pageInfo.getList(), pageInfo.getTotal());
    }
    @GetMapping("tree")
    public ResultTree tree(SysUser param){
        List<SysUser> data = sysUserService.list(param);
        return dataTree(data);
    }

    /**
     * Describe: 用户新增视图
     * Param ModelAndView
     * Return 返回用户新增视图
     */
    @GetMapping("add")
    @ApiOperation(value = "获取用户新增视图")
    @PreAuthorize("hasPermission('/system/user/add','sys:user:add')")
    public ModelAndView add(Model model) {
        model.addAttribute("sysRoles", sysRoleService.list(null));
        return jumpPage(MODULE_PATH + "add");
    }

    /**
     * Describe: 用户新增接口
     * Param ModelAndView
     * Return 操作结果
     */
    @RepeatSubmit
    @PostMapping("save")
    @ApiOperation(value = "保存用户数据")
    @PreAuthorize("hasPermission('/system/user/add','sys:user:add')")
    @Logging(title = "新增用户", describe = "新增用户", type = BusinessType.ADD)
    public Result save(@RequestBody SysUser sysUser) {
        sysUser.setLogin("0");
        sysUser.setEnable("1");
        sysUser.setStatus("1");
        sysUser.setUserId(SequenceUtil.makeStringId());
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        sysUserService.saveUserRole(sysUser.getUserId(), Arrays.asList(sysUser.getRoleIds().split(",")));
        Boolean result = sysUserService.save(sysUser);
        return decide(result);
    }

    /**
     * Describe: 用户修改视图
     * Param ModelAndView
     * Return 返回用户修改视图
     */
    @GetMapping("edit")
    @ApiOperation(value = "获取用户修改视图")
    @PreAuthorize("hasPermission('/system/user/edit','sys:user:edit')")
    public ModelAndView edit(Model model, String userId) {
        model.addAttribute("sysRoles", sysUserService.getUserRole(userId));
        model.addAttribute("sysUser", sysUserService.getById(userId));
        return jumpPage(MODULE_PATH + "edit");
    }

    /**
     * Describe: 用户密码修改视图
     * Param ModelAndView
     * Return 返回用户密码修改视图
     */
    @GetMapping("editpasswordadmin")
    @ApiOperation(value = "获取管理员修改用户密码视图")
    @PreAuthorize("hasPermission('/system/user/editPasswordAdmin','sys:user:editPasswordAdmin')")
    public ModelAndView editPasswordAdminView(Model model, String userId) {
        model.addAttribute("userId", userId);
        return jumpPage(MODULE_PATH + "editPasswordAdmin");
    }

    /**
     * Describe: 管理员修改用户密码接口
     * Param editPassword
     * Return: Result
     */
    @PutMapping("editPasswordAdmin")
    @ApiOperation(value = "管理员修改用户密码")
    @PreAuthorize("hasPermission('/system/user/editPasswordAdmin','sys:user:editPasswordAdmin')")
    public Result editPasswordAdmin(@RequestBody EditPassword editPassword) {
        SysUser editUser = sysUserService.getById(editPassword.getUserId());
        editUser.setPassword(new BCryptPasswordEncoder().encode(editPassword.getNewPassword()));
        boolean result = sysUserService.update(editUser);
        return decide(result, "修改成功", "修改失败");
    }

    /**
     * Describe: 用户密码修改视图
     * Param ModelAndView
     * Return 返回用户密码修改视图
     */
    @GetMapping("editPassword")
    public ModelAndView editPasswordView() {
        return jumpPage(MODULE_PATH + "password");
    }

    /**
     * Describe: 用户密码修改接口
     * Param editPassword
     * Return: Result
     */
    @PutMapping("editPassword")
    public Result editPassword(@RequestBody EditPassword editPassword) {
        String oldPassword = editPassword.getOldPassword();
        String newPassword = editPassword.getNewPassword();
        String confirmPassword = editPassword.getConfirmPassword();
        SysUser sysUser = SecurityUtil.currentUser();
        SysUser editUser = sysUserService.getById(sysUser.getUserId());
        if (Strings.isBlank(confirmPassword)
                || Strings.isBlank(newPassword)
                || Strings.isBlank(oldPassword)) {
            return failure("输入不能为空");
        }
        if (!new BCryptPasswordEncoder().matches(oldPassword, editUser.getPassword())) {
            return failure("密码验证失败");
        }
        if (!newPassword.equals(confirmPassword)) {
            return failure("两次密码输入不一致");
        }
        editUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        boolean result = sysUserService.update(editUser);
        return decide(result, "修改成功", "修改失败");
    }

    /**
     * Describe: 用户修改接口
     * Param ModelAndView
     * Return 返回用户修改接口
     */
    @PutMapping("update")
    @ApiOperation(value = "修改用户数据")
    @PreAuthorize("hasPermission('/system/user/edit','sys:user:edit')")
    @Logging(title = "修改用户", describe = "修改用户", type = BusinessType.EDIT)
    public Result update(@RequestBody SysUser sysUser) {
        sysUserService.saveUserRole(sysUser.getUserId(), Arrays.asList(sysUser.getRoleIds().split(",")));
        boolean result = sysUserService.update(sysUser);
        return decide(result);
    }

    /**
     * Describe: 头像修改接口
     * Param: SysUser
     * Return: Result
     */
    @PutMapping("updateAvatar")
    @ApiOperation(value = "修改用户头像")
    @Logging(title = "修改头像", describe = "修改头像", type = BusinessType.EDIT)
    public Result updateAvatar(@RequestBody SysUser sysUser) {
        String userId = SecurityUtil.currentUser().getUserId();
        sysUser.setUserId(userId);
        boolean result = sysUserService.update(sysUser);
        return decide(result);
    }

    /**
     * Describe: 用户批量删除接口
     * Param: ids
     * Return: Result
     */
    @DeleteMapping("batchRemove/{ids}")
    @ApiOperation(value = "批量删除用户")
    @PreAuthorize("hasPermission('/system/user/remove','sys:user:remove')")
    @Logging(title = "删除用户", describe = "删除用户", type = BusinessType.REMOVE)
    public Result batchRemove(@PathVariable String ids) {
        boolean result = sysUserService.batchRemove(ids.split(","));
        return decide(result);
    }

    /**
     * Describe: 用户删除接口
     * Param: id
     * Return: Result
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "删除用户数据")
    @PreAuthorize("hasPermission('/system/user/remove','sys:user:remove')")
    @Logging(title = "删除用户", describe = "删除用户", type = BusinessType.REMOVE)
    public Result remove(@PathVariable String id) {
        boolean result = sysUserService.remove(id);
        return decide(result);
    }



    /**
     * Describe: 根据 userId 开启用户
     * Param: SysUser
     * Return: 执行结果
     */
    @PutMapping("enable")
    @ApiOperation(value = "开启用户登录")
    public Result enable(@RequestBody SysUser sysUser) {
        sysUser.setEnable("1");
        boolean result = sysUserService.update(sysUser);
        return decide(result);
    }

    /**
     * Describe: 根据 userId 禁用用户
     * Param: SysUser
     * Return: 执行结果
     */
    @PutMapping("disable")
    @ApiOperation(value = "禁用用户登录")
    public Result disable(@RequestBody SysUser sysUser) {
        sysUser.setEnable("0");
        boolean result = sysUserService.update(sysUser);
        return decide(result);
    }

    /**
     * Describe: 个人资料
     * Param: null
     * Return: ModelAndView
     */
    @GetMapping("center")
    @ApiOperation(value = "个人资料")
    public ModelAndView center(Model model) {
        SysUser sysUser = SecurityUtil.currentUser();
        model.addAttribute("userInfo", sysUserService.getById(sysUser.getUserId()));
        model.addAttribute("logs", sysLogService.selectTopLoginLog(sysUser.getUsername()));


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTwo = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        /*
        当前日期
         */
        Date currentDate=ca.getTime();
        String sCurrentDate = dateFormat.format(currentDate);//格式化
        String sCurrentDateStart=sCurrentDate+" 00:00:00";//当天开始
        String sCurrentDateEnd=sCurrentDate+" 23:59:59";//当天结束

        /*
        一个月前
         */
        Calendar caMonth = Calendar.getInstance();//得到一个Calendar的实例
        caMonth.add(Calendar.MONTH, -1); //一个月前
        Date last30Day = caMonth.getTime(); //结果
        String sLast30Day = dateFormat.format(last30Day);
        String sLast30DayStart=sLast30Day+" 00:00:00";
        String sLast30DayEnd=sLast30Day+" 23:59:59";
        /*
        三个月前
         */
        Calendar sanMonth = Calendar.getInstance();//得到一个Calendar的实例
        sanMonth.add(Calendar.MONTH, -3); //一个月前
        Date last3Month = caMonth.getTime(); //结果
        String sLast3Month = dateFormat.format(last3Month);
        String sLast3MonthStart=sLast30Day+" 00:00:00";
        String sLast3MonthEnd=sLast30Day+" 23:59:59";

        /*
        一年前
         */
        Calendar caYear = Calendar.getInstance();//得到一个Calendar的实例
        caYear.add(Calendar.YEAR, -1); //一个月前
        Date lastYear = caYear.getTime(); //结果
        String sLastYear = dateFormat.format(lastYear);
        String sLastYearStart=sLastYear+" 00:00:00";
        String sLastYearEnd=sLastYear+" 23:59:59";

        int arrival30DayData = iSysUsersService.showUserArrivalDataByRangeData(sysUser.getUserId(), sLast30DayStart, sCurrentDateEnd);

        int arrival3MonthData = iSysUsersService.showUserArrivalDataByRangeData(sysUser.getUserId(), sLast3MonthStart, sCurrentDateEnd);
        int arrival1YearData = iSysUsersService.showUserArrivalDataByRangeData(sysUser.getUserId(), sLastYearStart, sCurrentDateEnd);
        System.out.println("+++");
        System.err.println("arrival1YearData"+arrival30DayData);
        model.addAttribute("arrival30DayData",arrival30DayData);
        model.addAttribute("arrival3MonthData",arrival3MonthData);
        model.addAttribute("arrival1YearData",arrival1YearData);

        //查询users/console页面的数据展示：个人常去场馆次数
        List<SysUsers> sysUsers = iSysUsersService.showTop5ArrivalByUserId(sysUser.getUserId());
        int i=1;
        for (SysUsers user : sysUsers) {
            model.addAttribute("top"+i+"ArrivalPlaceName",user.getPlaceName());
            model.addAttribute("top"+i+"ArrivalPlaceCount",user.getCount());
            i++;
        }

        //查询users/console页面的数据展示：最近5次的打运动记录
        SysUsers sysUsers1 = new SysUsers();
        sysUsers1.setUserId(sysUser.getUserId());
        sysUsers1.setLimit(4);
        List<SysUsers> sysUsersList = iSysUsersService.showSportRecord(sysUsers1);
        model.addAttribute("sysUsersList",sysUsersList);



        return jumpPage(MODULE_PATH + "center");
    }

    /**
     * Describe: 用户修改接口
     * Param ModelAndView
     * Return 返回用户修改接口
     */
    @PutMapping("updateInfo")
    @ApiOperation(value = "修改用户数据")
    public Result updateInfo(@RequestBody SysUser sysUser) {
        boolean result = sysUserService.update(sysUser);
        return decide(result);
    }


    /**
     * Describe: 更换头像
     * Param: null
     * Return: ModelAndView
     */
    @GetMapping("profile/{id}")
    public ModelAndView profile(Model model, @PathVariable("id") String userId) {
        model.addAttribute("userId", userId);
        return jumpPage(MODULE_PATH + "profile");
    }
}
