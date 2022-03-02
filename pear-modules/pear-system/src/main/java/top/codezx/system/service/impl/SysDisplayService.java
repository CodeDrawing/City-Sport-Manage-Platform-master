package top.codezx.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import top.codezx.system.domain.SysLog;
import top.codezx.system.mapper.SysDisplayMapper;
import top.codezx.system.service.ISysDisplayService;

import java.util.List;

@Service
public class SysDisplayService implements ISysDisplayService {
    @Autowired
    private SysDisplayMapper sysDisplayMapper;
    @Override
    public int displayDataAboutLoginByDate(String endDate, String startDate) {
        List<SysLog> sysLogs = sysDisplayMapper.displayDataAboutLoginByDate(endDate, startDate);
        int counts = sysLogs.size();
        return counts;
    }
}
