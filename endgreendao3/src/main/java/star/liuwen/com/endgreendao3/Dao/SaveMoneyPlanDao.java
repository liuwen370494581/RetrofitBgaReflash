package star.liuwen.com.endgreendao3.Dao;

import java.util.List;

import star.liuwen.com.endgreendao3.Base.BaseApplication;
import star.liuwen.com.endgreendao3.Bean.SaveMoneyPlanModel;
import star.liuwen.com.endgreendao3.Bean.Shop;
import star.liuwen.com.endgreendao3.Bean.ShopDao;

/**
 * Created by liuwen on 2017/2/23.
 */
public class SaveMoneyPlanDao {

    /**
     * 添加数据
     *
     * @param model
     */
    public static void insertSaveMoneyDao(SaveMoneyPlanModel model) {
        BaseApplication.getDaoInstant().getSaveMoneyPlanModelDao().insert(model);
    }
}
