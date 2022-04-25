package top.codezx.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.codezx.common.constant.ControllerConstant;
import top.codezx.common.tools.SecurityUtil;
import top.codezx.common.web.base.BaseController;
import top.codezx.system.domain.SysArrivalInfo;
import top.codezx.system.domain.SysUser;
import top.codezx.system.service.ISysDisplayService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = {"场所管理员"})
@RequestMapping("place")
public class PlaceController extends BaseController {

    /**
     * Describe: 基础路径
     */
    private static String MODULE_PATH = "system/place/";

    @Resource
    private ISysDisplayService iSysDisplayService;

    /**
     * Describe: 获取场馆数据透视
     * Param ModelAndView
     * Return 获取场馆数据透视
     */
    @GetMapping("console")
    @ApiOperation(value = "获取场所列表视图")
    @PreAuthorize("hasPermission('/place/console','place:console')")
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
        一天前
         */
        ca.add(Calendar.DATE, -1); //七天前
        Date last1Day = ca.getTime(); //结果
        String sLast1Day = dateFormat.format(last1Day);//格式化
        String sLast1DayStart=sLast1Day+" 00:00:00";//当天开始
        String sLast1DayEnd=sLast1Day+" 23:59:59";//当天结束
        /*
        两天前
         */
        ca.add(Calendar.DATE, -1); //七天前
        Date last2Day = ca.getTime(); //结果
        String sLast2Day = dateFormat.format(last2Day);//格式化
        String sLast2DayStart=sLast2Day+" 00:00:00";//当天开始
        String sLast2DayEnd=sLast2Day+" 23:59:59";//当天结束
        /*
        三天前
         */
        ca.add(Calendar.DATE, -1); //七天前
        Date last3Day = ca.getTime(); //结果
        String sLast3Day = dateFormat.format(last3Day);//格式化
        String sLast3DayStart=sLast3Day+" 00:00:00";//当天开始
        String sLast3DayEnd=sLast3Day+" 23:59:59";//当天结束
        /*
        四天前
         */
        ca.add(Calendar.DATE, -1); //七天前
        Date last4Day = ca.getTime(); //结果
        String sLast4Day = dateFormat.format(last4Day);//格式化
        String sLast4DayStart=sLast4Day+" 00:00:00";//当天开始
        String sLast4DayEnd=sLast4Day+" 23:59:59";//当天结束
        /*
        五天前
         */
        ca.add(Calendar.DATE, -1); //七天前
        Date last5Day = ca.getTime(); //结果
        String sLast5Day = dateFormat.format(last5Day);//格式化
        String sLast5DayStart=sLast5Day+" 00:00:00";//当天开始
        String sLast5DayEnd=sLast5Day+" 23:59:59";//当天结束
        /*
        7天前
         */
        ca.add(Calendar.DATE, -2); //七天前
        Date last7Day = ca.getTime(); //结果
        String sLast7Day = dateFormat.format(last7Day);
        String sLast7DayStart=sLast7Day+" 00:00:00";
        String sLast7DayEnd=sLast7Day+" 23:59:59";
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
        一年前
         */
        Calendar caYear = Calendar.getInstance();//得到一个Calendar的实例
        caYear.add(Calendar.YEAR, -1); //一个月前
        Date lastYear = caYear.getTime(); //结果
        String sLastYear = dateFormat.format(lastYear);
        String sLastYearStart=sLastYear+" 00:00:00";
        String sLastYearEnd=sLastYear+" 23:59:59";


        SysUser sysUser = SecurityUtil.currentUser();
        Integer currentVisitData = iSysDisplayService.displayDataAboutVisitByDateAndPlaceId(sysUser.getUserId(), sCurrentDateStart, sCurrentDateEnd);
        Integer last7VisitData = iSysDisplayService.displayDataAboutVisitByDateAndPlaceId(sysUser.getUserId(), sLast7DayStart, sCurrentDateEnd);
        Integer last30VisitData = iSysDisplayService.displayDataAboutVisitByDateAndPlaceId(sysUser.getUserId(), sLast30DayStart, sCurrentDateEnd);
        Integer lastYearVisitData = iSysDisplayService.displayDataAboutVisitByDateAndPlaceId(sysUser.getUserId(), sLastYearStart, sCurrentDateEnd);

        model.addAttribute("currentVisitData",currentVisitData);
        model.addAttribute("last7VisitData",last7VisitData);
        model.addAttribute("last30VisitData",last30VisitData);
        model.addAttribute("lastYearVisitData",lastYearVisitData);

        List<SysArrivalInfo> sysArrivalInfos = iSysDisplayService.showTopFiveData(sysUser.getUserId());
        System.out.println(sysArrivalInfos);
        int i=1;

        for(SysArrivalInfo sysArrivalInfo:sysArrivalInfos){
            model.addAttribute("top"+i+"Date",dateFormatTwo.format(sysArrivalInfo.getArrivalDate()));
            model.addAttribute("top"+i+"DataOfMan",sysArrivalInfo.getTheNumberOfMan());
            model.addAttribute("top"+i+"DataOfWoman",sysArrivalInfo.getTheNumberOfWoman());
            i++;
        }
        //年龄分布数据
        Integer under18 = iSysDisplayService.showUnder18Data(sysUser.getUserId());
        Integer the18To30 = iSysDisplayService.show18TO30Data(sysUser.getUserId());
        Integer the31To60 = iSysDisplayService.show31To60Data(sysUser.getUserId());
        Integer above61 = iSysDisplayService.showAbove61Data(sysUser.getUserId());
        model.addAttribute("under18",under18);
        model.addAttribute("the18To30",the18To30);
        model.addAttribute("the31To60",the31To60);
        model.addAttribute("above61",above61);
        return jumpPage(MODULE_PATH+"console");
    }



}
