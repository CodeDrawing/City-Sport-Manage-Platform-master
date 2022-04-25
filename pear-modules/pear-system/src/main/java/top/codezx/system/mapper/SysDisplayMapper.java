package top.codezx.system.mapper;



import org.apache.ibatis.annotations.Mapper;
import top.codezx.system.domain.SysArrivalInfo;
import top.codezx.system.domain.SysLog;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysDisplayMapper {
    /**
     * 访问人数查询log里面的
     * 查询内容：打卡成功（title）
     * 查询表：sys_logging
     * @param endDate
     * @param startDate
     * @return
     */
    Integer displayDataAboutLoginByDate(String endDate, String startDate);
    /**
     * 访问人数查询arrivalInfo里面的
     * 查询内容：placeId（title）
     * 查询表：sys_arrivalInfo
     * @param placeId
     * @return 男士人数+女士人数
     */
    Integer displayDataAboutVisitByDateAndPlaceId(String placeId,String endDate, String startDate);
    /**
     * 根据userId查询该用户管理的场馆id
     * 查询内容：userId
     * 查询表：sys_placeInfo
     * @param userId
     * @return place_id
     */
    String queryPlaceIdByUserid(String userId);
    /**
     * 显示该场馆的男女数量 top 5(最近5天)
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @param placeId
     * @return List<SysArrivalInfo>
     */
    List<SysArrivalInfo> showTopFiveData(String placeId);
    /**
     * 显示该场馆的18岁以下的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @param placeId
     * @return Integer
     */
    Integer showUnder18Data(String placeId);
    /**
     * 显示该场馆的18-30岁的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @param placeId
     * @return Integer
     */
    Integer show18TO30Data(String placeId);
    /**
     * 显示该场馆的31-60岁的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @param placeId
     * @return Integer
     */
    Integer show31To60Data(String placeId);
    /**
     * 显示该场馆的60岁以上的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @param placeId
     * @return Integer
     */

    Integer showAbove61Data(String placeId);
    /**
     * 显示该场馆的18岁以下的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @return Integer
     */
    Integer showAllUnder18Data();
    /**
     * 显示该场馆的18-30岁的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @return Integer
     */
    Integer showAll18TO30Data();
    /**
     * 显示该场馆的31-60岁的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @return Integer
     */
    Integer showAll31To60Data();
    /**
     * 显示该场馆的60岁以上的数据
     * 查询内容：placeId
     * 查询表：sys_arrivalInfo
     * @return Integer
     */
    Integer showAllAbove61Data();

    Integer insertData(SysArrivalInfo sysArrivalInfo);

}
