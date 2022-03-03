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
    private UserDetailsService userDetailsService;

    /**
     * Describe: 获取场馆数据透视
     * Param ModelAndView
     * Return 获取场馆数据透视
     */
    @GetMapping("console")
    @ApiOperation(value = "获取场所列表视图")
    @PreAuthorize("hasPermission('/place/console','place:console')")
    public ModelAndView main() {
        SysUser sysUser = SecurityUtil.currentUser();
        System.out.println("===================");
        System.err.println(sysUser.getDeptId());
        System.out.println("===================");
        return jumpPage(MODULE_PATH+"console");
    }
}
