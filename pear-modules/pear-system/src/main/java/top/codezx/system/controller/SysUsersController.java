package top.codezx.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.codezx.common.constant.ControllerConstant;
import top.codezx.common.tools.SecurityUtil;

import top.codezx.common.web.base.BaseController;
import top.codezx.system.domain.SysUser;
import top.codezx.system.domain.SysUsers;
import top.codezx.system.service.ISysUsersService;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@Api(tags = {"用户"})
@RequestMapping(ControllerConstant.API_SYSTEM_PREFIX + "users")
public class SysUsersController extends BaseController {
    /**
     * Describe: 基础路径
     */
    private static String MODULE_PATH = "system/users/";

    @Resource
    private ISysUsersService iSysUsersService;


    /**
     * Describe: 获取个人数据透视
     * Param ModelAndView
     * Return 获取个人数据透视
     */
    @GetMapping("console")
    @ApiOperation(value = "获取个人数据")
    @PreAuthorize("hasPermission('/system/users/console','sys:users:console')")
    public ModelAndView main(Model model) {

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

        SysUser sysUser = SecurityUtil.currentUser();
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
        //查询users/console页面的数据展示：参与的运动项目比例
        List<SysUsers> sysUsersSportProject = iSysUsersService.showMostSportProject(sysUser.getUserId());
        i=1;

        for (SysUsers user : sysUsersSportProject) {
            model.addAttribute("top"+i+"SportProject",user.getSportProject());
            model.addAttribute("top"+i+"SportProjectCount",user.getCount());
            i++;
        }

        //查询users/console页面的数据展示：最近5次的打运动记录
        SysUsers sysUsers1 = new SysUsers();
        sysUsers1.setUserId(sysUser.getUserId());
        sysUsers1.setLimit(4);
        List<SysUsers> sysUsersList = iSysUsersService.showSportRecord(sysUsers1);
        model.addAttribute("sysUsersList",sysUsersList);
        return jumpPage(MODULE_PATH+"console");
    }
}
