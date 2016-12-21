package sh.ajb.com.endokhhtp;

import java.util.ArrayList;
import java.util.List;

import sh.ajb.com.endokhhtp.model.ChatModel;

/**
 * Created by liuwen on 2016/12/21.
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
}
