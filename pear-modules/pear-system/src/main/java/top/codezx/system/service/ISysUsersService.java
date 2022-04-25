package top.codezx.system.service;

import top.codezx.system.domain.SysUsers;

import java.util.List;

public interface ISysUsersService {
    /**
     * 查询用户的打卡数据
     * @param userId 用户id
     * @param startDate 开始日期  离的比较远的
     * @param endDate   结束日期 离的比较进的
     * @return count(*)
     */
    int showUserArrivalDataByRangeData(String userId, String startDate,String endDate);

    /**
     * 查询users/console页面的数据展示：个人常去场馆次数
     * @param userId 用户Id
     * @return List<SysUsers>
     */
    List<SysUsers> showTop5ArrivalByUserId(String userId);
    /**
     * 查询users/console页面的数据展示：参与的运动项目比例
     * @param userId 用户Id
     * @return List<SysUsers>
     */
    List<SysUsers> showMostSportProject(String userId);
    /**
     * 查询users/console页面的数据展示：最近5次的打运动记录
     * @param sysUsers 用户信息
     * @return List<SysUsers>
     */
    List<SysUsers> showSportRecord(SysUsers sysUsers);

}
