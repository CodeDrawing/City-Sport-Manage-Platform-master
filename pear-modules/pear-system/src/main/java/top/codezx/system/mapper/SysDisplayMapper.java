package top.codezx.system.mapper;



import org.apache.ibatis.annotations.Mapper;
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
    List<SysLog> displayDataAboutLoginByDate(String endDate, String startDate);
}
