package star.liuwen.com.endgreendao3.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuwen on 2017/2/23.
 */
@Entity
public class SaveMoneyPlanModel {

    @Id(autoincrement = true)
    private long id;
    private String platForm;//存款平台
    private double saveMoney;//计划存款多少
    private double yield;//收益率
    private String startTime;//起息时间
    private String endTime;//结束时间
    private String remark;//备注
    private String accountType;//账户类型

    @Generated(hash = 384656849)
    public SaveMoneyPlanModel(long id, String platForm, double saveMoney,
            double yield, String startTime, String endTime, String remark,
            String accountType) {
        this.id = id;
        this.platForm = platForm;
        this.saveMoney = saveMoney;
        this.yield = yield;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remark = remark;
        this.accountType = accountType;
    }
    @Generated(hash = 534086048)
    public SaveMoneyPlanModel() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPlatForm() {
        return this.platForm;
    }
    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }
    public double getSaveMoney() {
        return this.saveMoney;
    }
    public void setSaveMoney(double saveMoney) {
        this.saveMoney = saveMoney;
    }
    public double getYield() {
        return this.yield;
    }
    public void setYield(double yield) {
        this.yield = yield;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getAccountType() {
        return this.accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
