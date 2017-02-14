package sh.ajb.com.endokhhtp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sh.ajb.com.endokhhtp.model.ChatModel;
import sh.ajb.com.endokhhtp.model.IndexModel;
import sh.ajb.com.endokhhtp.widget.CharacterParser;
import sh.ajb.com.endokhhtp.widget.PinyinComparator;

/**
 * Created by liuwen on 2016/12/21.
 * 描述 ：模拟添加数据的类
 */
public class DataEngine {


    //模拟数据
    public static List<ChatModel> loadChatModelData() {
        List<ChatModel> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 3 != 0) {
                data.add(new ChatModel("消息" + i, ChatModel.UserType.Me, i % 2 == 0 ? ChatModel.SendStatus.Success : ChatModel.SendStatus.Failure));
            } else {
                data.add(new ChatModel("消息" + i, ChatModel.UserType.Other, null));
            }
        }
        return data;
    }

    public static List<IndexModel> loadIndexModelData() {
        List<IndexModel> data = new ArrayList<>();
        data.add(new IndexModel("安阳"));
        data.add(new IndexModel("鞍山"));
        data.add(new IndexModel("保定"));
        data.add(new IndexModel("包头"));
        data.add(new IndexModel("北京"));
        data.add(new IndexModel("河北"));
        data.add(new IndexModel("北海"));
        data.add(new IndexModel("安庆"));
        data.add(new IndexModel("伊春"));
        data.add(new IndexModel("宝鸡"));
        data.add(new IndexModel("本兮"));
        data.add(new IndexModel("滨州"));
        data.add(new IndexModel("常州"));
        data.add(new IndexModel("常德"));
        data.add(new IndexModel("常熟"));
        data.add(new IndexModel("枣庄"));
        data.add(new IndexModel("内江"));
        data.add(new IndexModel("阿坝州"));
        data.add(new IndexModel("丽水"));
        data.add(new IndexModel("成都"));
        data.add(new IndexModel("承德"));
        data.add(new IndexModel("沧州"));
        data.add(new IndexModel("重庆"));
        data.add(new IndexModel("东莞"));
        data.add(new IndexModel("扬州"));
        data.add(new IndexModel("甘南州"));
        data.add(new IndexModel("大庆"));
        data.add(new IndexModel("佛山"));
        data.add(new IndexModel("广州"));
        data.add(new IndexModel("合肥"));
        data.add(new IndexModel("海口"));
        data.add(new IndexModel("济南"));
        data.add(new IndexModel("兰州"));
        data.add(new IndexModel("南京"));
        data.add(new IndexModel("泉州"));
        data.add(new IndexModel("荣成"));
        data.add(new IndexModel("三亚"));
        data.add(new IndexModel("上海"));
        data.add(new IndexModel("汕头"));
        data.add(new IndexModel("天津"));
        data.add(new IndexModel("武汉"));
        data.add(new IndexModel("厦门"));
        data.add(new IndexModel("宜宾"));
        data.add(new IndexModel("张家界"));
        data.add(new IndexModel("自贡"));
        data.add(new IndexModel("长沙"));
        PinyinComparator pinyinComparator = new PinyinComparator();
        CharacterParser characterParser = CharacterParser.getInstance();
        for (IndexModel indexModel : data) {
            indexModel.topc = characterParser.getSelling(indexModel.name).substring(0, 1).toUpperCase();
            if (indexModel.name.equals("重庆")) {
                indexModel.topc = "C";
            }
        }
        Collections.sort(data, pinyinComparator);
        return data;

    }

    public static List<IndexModel> loadXykData() {
        List<IndexModel> data = new ArrayList<>();
        data.add(new IndexModel("北京农商银行"));
        data.add(new IndexModel("北京银行"));
        data.add(new IndexModel("成都工商银行"));
        data.add(new IndexModel("成都银行"));
        data.add(new IndexModel("长沙银行"));
        data.add(new IndexModel("重庆银行"));
        data.add(new IndexModel("大连银行"));
        data.add(new IndexModel("东莞银行"));
        data.add(new IndexModel("甘肃银行"));
        data.add(new IndexModel("广州银行"));
        data.add(new IndexModel("工商银行"));
        data.add(new IndexModel("广发银行"));
        data.add(new IndexModel("光大银行"));
        data.add(new IndexModel("杭州银行"));
        data.add(new IndexModel("河北银行"));
        data.add(new IndexModel("恒丰银行"));
        data.add(new IndexModel("恒生银行"));
        data.add(new IndexModel("华夏银行"));
        data.add(new IndexModel("吉林银行"));
        data.add(new IndexModel("江苏银行"));
        data.add(new IndexModel("建设银行"));
        data.add(new IndexModel("交通银行"));
        data.add(new IndexModel("兰州银行"));
        data.add(new IndexModel("民泰银行"));
        data.add(new IndexModel("民生银行"));
        data.add(new IndexModel("南京银行"));
        data.add(new IndexModel("内蒙古银行"));
        data.add(new IndexModel("宁波银行"));
        data.add(new IndexModel("宁夏银行"));
        data.add(new IndexModel("农商银行"));
        data.add(new IndexModel("农业银行"));
        data.add(new IndexModel("平安银行"));
        data.add(new IndexModel("浦东发展银行"));
        data.add(new IndexModel("齐鲁银行"));
        data.add(new IndexModel("青岛银行"));
        data.add(new IndexModel("青海银行"));
        data.add(new IndexModel("其他"));
        data.add(new IndexModel("上海农商银行"));
        data.add(new IndexModel("上海银行"));
        data.add(new IndexModel("天津银行"));
        data.add(new IndexModel("温州银行"));
        data.add(new IndexModel("兴业银行"));
        data.add(new IndexModel("邮政银行"));
        data.add(new IndexModel("浙商银行"));
        data.add(new IndexModel("中国银行"));
        data.add(new IndexModel("招商银行"));
        data.add(new IndexModel("中信银行"));
        PinyinComparator pinyinComparator = new PinyinComparator();
        CharacterParser characterParser = CharacterParser.getInstance();
        for (IndexModel indexModel : data) {
            indexModel.topc = characterParser.getSelling(indexModel.name).substring(0, 1).toUpperCase();
            if (indexModel.name.equals("重庆银行")) {
                indexModel.topc = "C";
            }
        }
        Collections.sort(data, pinyinComparator);
        return data;

    }

}
