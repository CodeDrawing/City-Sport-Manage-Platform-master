package top.codezx.system.mapper;

import org.mapstruct.Mapper;

import java.util.Date;
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
    int displayDataAboutLoginByDateAndPlace(Date endDate, Date startDate);
}
