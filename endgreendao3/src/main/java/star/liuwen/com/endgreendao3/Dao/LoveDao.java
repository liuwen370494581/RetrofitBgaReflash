package star.liuwen.com.endgreendao3.Dao;

import java.util.List;

import star.liuwen.com.endgreendao3.Base.BaseApplication;
import star.liuwen.com.endgreendao3.Bean.Shop;
import star.liuwen.com.endgreendao3.Bean.ShopDao;

/**
 * Created by liuwen on 2017/2/23.
 */
public class LoveDao {

    /**
     * 添加数据
     *
     * @param shop
     */
    public static void insertLove(Shop shop) {
        BaseApplication.getDaoInstant().getShopDao().insert(shop);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLove(long id) {
        BaseApplication.getDaoInstant().getShopDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateLove(Shop shop) {
        BaseApplication.getDaoInstant().getShopDao().update(shop);
    }

    /**
     * 查询条件为Type=TYPE_LOVE的数据
     *
     * @return
     */
    public static List<Shop> queryLove() {
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder().where(ShopDao.Properties.Type.eq(Shop.TYPE_LOVE)).list();
    }

    public static long getCount() {
        return BaseApplication.getDaoInstant().getShopDao().count();
    }
}
