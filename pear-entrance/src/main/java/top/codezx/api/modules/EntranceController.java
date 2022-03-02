package top.codezx.api.modules;


import org.springframework.ui.Model;
import top.codezx.common.plugin.logging.aop.annotation.Logging;
import top.codezx.common.plugin.logging.aop.enums.BusinessType;
import top.codezx.common.tools.SecurityUtil;
import top.codezx.common.web.base.BaseController;
import top.codezx.secure.session.SecureSessionService;
import io.swagger.annotations.Api;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.codezx.system.service.ISysDisplayService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Describe: 入 口 控 制 器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
@RequestMapping
@Api(tags = {"项目入口"})
public class EntranceController extends BaseController {

    @Resource
    private SessionRegistry sessionRegistry;
    @Resource
    private ISysDisplayService iSysDisplayService;

    /**
     * Describe: 获取登录视图
     * Param: ModelAndView
     * Return: 登录视图
     */
    @GetMapping("login")
    public ModelAndView login(HttpServletRequest request) {
        if (SecurityUtil.isAuthentication()) {
            SecureSessionService.expiredSession(request, sessionRegistry);
            return jumpPage("index");
        } else {
            return jumpPage("login");
        }
    }





    /**
     * Describe: 获取主页视图
     * Param: ModelAndView
     * Return: 登录视图
     */
    @GetMapping("index")
    @Logging(title = "主页", describe = "返回 Index 主页视图", type = BusinessType.ADD)
    public ModelAndView index() {
        return jumpPage("index");
    }

    /**
     * Describe: 获取主页视图
     * Param: ModelAndView
     * Return: 主页视图
     */
    @GetMapping("console")
    public ModelAndView home(Model model) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

        //数据展示
        int todayVisitData = iSysDisplayService.displayDataAboutLoginByDate(sCurrentDateStart,sCurrentDateEnd);
        int day7VisitData = iSysDisplayService.displayDataAboutLoginByDate(sLast7DayStart,sCurrentDateEnd);
        int day30VisitData = iSysDisplayService.displayDataAboutLoginByDate(sLast30DayStart,sCurrentDateEnd);
        int lastYearVisitData = iSysDisplayService.displayDataAboutLoginByDate(sLastYearStart,sCurrentDateEnd);
        model.addAttribute("todayVisitData",todayVisitData);
        model.addAttribute("day7VisitData",day7VisitData);
        model.addAttribute("day30VisitData",day30VisitData);
        model.addAttribute("lastYearVisitData",lastYearVisitData);

        //图表数据
        int last1DayData = iSysDisplayService.displayDataAboutLoginByDate(sLast1DayStart,sLast1DayEnd);
        int last2DayData = iSysDisplayService.displayDataAboutLoginByDate(sLast2DayStart,sLast2DayEnd);
        int last3DayData = iSysDisplayService.displayDataAboutLoginByDate(sLast3DayStart,sLast3DayEnd);
        int last4DayData = iSysDisplayService.displayDataAboutLoginByDate(sLast4DayStart,sLast4DayEnd);
        int last5DayData = iSysDisplayService.displayDataAboutLoginByDate(sLast5DayStart,sLast5DayEnd);
        model.addAttribute("last1DayData",last1DayData);
        model.addAttribute("last2DayData",last2DayData);
        model.addAttribute("last3DayData",last3DayData);
        model.addAttribute("last4DayData",last4DayData);
        model.addAttribute("last5DayData",last5DayData);



        return jumpPage("console/console");
    }

    /**
     * Describe:无权限页面
     * Return:返回403页面
     */
    @GetMapping("error/403")
    public ModelAndView noPermission() {
        return jumpPage("error/403");
    }

    /**
     * Describe:找不到页面
     * Return:返回404页面
     */
    @GetMapping("error/404")
    public ModelAndView notFound() {
        return jumpPage("error/404");
    }

    /**
     * Describe:异常处理页
     * Return:返回500界面
     */
    @GetMapping("error/500")
    public ModelAndView onException() {
        return jumpPage("error/500");
    }

}
