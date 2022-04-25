package top.codezx.system.service.impl;

import org.springframework.stereotype.Service;
import top.codezx.system.domain.SysUsers;
import top.codezx.system.mapper.SysUsersMapper;
import top.codezx.system.service.ISysUsersService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUsersServiceImpl implements ISysUsersService {

    @Resource
    private SysUsersMapper sysUsersMapper;

    @Override
    public int showUserArrivalDataByRangeData(String userId, String startDate, String endDate) {
        int result = sysUsersMapper.showUserArrivalDataByRangeData(userId, startDate, endDate);
        return result;
    }

    @Override
    public List<SysUsers> showTop5ArrivalByUserId(String userId) {
        List<SysUsers> sysUsers = sysUsersMapper.showTop5ArrivalByUserId(userId);
        return sysUsers;
    }

    @Override
    public List<SysUsers> showMostSportProject(String userId) {
        List<SysUsers> sysUsers = sysUsersMapper.showMostSportProject(userId);
        return sysUsers;
    }

    @Override
    public List<SysUsers> showSportRecord(SysUsers sysUsers) {
        List<SysUsers> sysUsersList = sysUsersMapper.showSportRecord(sysUsers);
        return sysUsersList;
    }
}
