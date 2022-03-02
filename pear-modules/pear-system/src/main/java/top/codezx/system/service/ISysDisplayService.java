package top.codezx.system.service;

import java.util.Date;

public interface ISysDisplayService {
    /**
     * 访问人数查询log里面的
     * 查询内容：打卡成功（title）
     * 查询表：sys_logging
     * @param endDate 截止日期即现在的日期
     * @param startDate 开始日期
     * @return
     */
    int displayDataAboutLoginByDate(String endDate,String startDate);
}
