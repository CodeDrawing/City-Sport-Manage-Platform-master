package top.codezx.system.service.impl;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import top.codezx.common.tools.SequenceUtil;
import top.codezx.system.domain.SysArrivalInfo;
import top.codezx.system.domain.SysLog;
import top.codezx.system.mapper.SysDisplayMapper;
import top.codezx.system.service.ISysDisplayService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class SysDisplayService implements ISysDisplayService {
    @Autowired
    private SysDisplayMapper sysDisplayMapper;

    @Override
    public String queryPlaceIdByUserid(String userId) {
        String placeId = sysDisplayMapper.queryPlaceIdByUserid(userId);
        return placeId;
    }

    @Override
    public int displayDataAboutLoginByDate(String endDate, String startDate) {
        Integer integer = sysDisplayMapper.displayDataAboutLoginByDate(endDate, startDate);
        if(integer==null){
            integer=0;
        }
        return integer;
    }

    @Override
    public Integer displayDataAboutVisitByDateAndPlaceId(String userId, String endDate, String startDate) {
        String placeId = sysDisplayMapper.queryPlaceIdByUserid(userId);
        Integer result = sysDisplayMapper.displayDataAboutVisitByDateAndPlaceId(placeId, endDate, startDate);
        return result;
    }

    @Override
    public List<SysArrivalInfo> showTopFiveData(String userId) {
        String placeId = sysDisplayMapper.queryPlaceIdByUserid(userId);
        List<SysArrivalInfo> sysArrivalInfos = sysDisplayMapper.showTopFiveData(placeId);
        return sysArrivalInfos;
    }

    @Override
    public Integer showUnder18Data(String userId) {
        String placeId = sysDisplayMapper.queryPlaceIdByUserid(userId);
        Integer result = sysDisplayMapper.showUnder18Data(placeId);
        return result;
    }

    @Override
    public Integer show18TO30Data(String userId) {
        String placeId = sysDisplayMapper.queryPlaceIdByUserid(userId);
        Integer result = sysDisplayMapper.show18TO30Data(placeId);
        return result;
    }

    @Override
    public Integer show31To60Data(String userId) {
        String placeId = sysDisplayMapper.queryPlaceIdByUserid(userId);
        Integer result = sysDisplayMapper.show31To60Data(placeId);
        return result;
    }

    @Override
    public Integer showAbove61Data(String userId) {
        String placeId = sysDisplayMapper.queryPlaceIdByUserid(userId);
        Integer result = sysDisplayMapper.showAbove61Data(placeId);
        return result;
    }

    @Override
    public Integer showAllUnder18Data() {
        Integer result = sysDisplayMapper.showAllUnder18Data();
        return result;
    }

    @Override
    public Integer showAll18TO30Data() {
        Integer result = sysDisplayMapper.showAll18TO30Data();
        return result;
    }

    @Override
    public Integer showAll31To60Data() {
        Integer result = sysDisplayMapper.showAll31To60Data();
        return result;
    }

    @Override
    public Integer showAllAbove61Data() {
        Integer result = sysDisplayMapper.showAllAbove61Data();
        return result;
    }

    @Override
    public void insertData() {

        String[] placeId = {"1485486260817494016", "1485487668216201216", "1485610264140185600", "1498499495216807936", "1502084634979074048", "1509506147139190784" };
        String[] placeName={"精羽羽毛球馆","新都体育场" ,"懿阳羽毛球馆", "内江奥体中心羽毛球馆","集世麦羽毛球馆", "东兴区体育场"};
        Random random = new Random();
        int i;

        for (int j = 0; j < 20; j++) {
            i = random.nextInt(100);
            SysArrivalInfo proSysArrivalInfo = new SysArrivalInfo();
            proSysArrivalInfo.setPlaceId(placeId[i%6]);
            proSysArrivalInfo.setPlaceName(placeName[i%6]);
            //设置到场日期
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
            ca.setTime(new Date()); //设置时间为当前时间
            Date currentDate=ca.getTime();
            ca.add(Calendar.DATE, -random.nextInt(100));
            Date arrivalDate = ca.getTime(); //结果
//            String sLast1Day = dateFormat.format(last1Day);//格式化
            proSysArrivalInfo.setArrivalDate(arrivalDate);
            int under18=random.nextInt(400);
            int the18To30=random.nextInt(200);
            int above61=random.nextInt(100);
            int the31To60=random.nextInt(150);
            int sum=under18+the18To30+above61+the31To60;
            int theNumberOfMan=sum/(random.nextInt(5)+1);
            int theNumberOfWoman=sum-theNumberOfMan;
            proSysArrivalInfo.setArrivalInfoId(SequenceUtil.makeStringId());
            proSysArrivalInfo.setUnder18(under18);
            proSysArrivalInfo.setThe18To30(the18To30);
            proSysArrivalInfo.setAbove61(above61);
            proSysArrivalInfo.setThe31To60(the31To60);
            proSysArrivalInfo.setTheNumberOfMan(theNumberOfMan);
            proSysArrivalInfo.setTheNumberOfWoman(theNumberOfWoman);
            sysDisplayMapper.insertData(proSysArrivalInfo);
        }
    }
}
