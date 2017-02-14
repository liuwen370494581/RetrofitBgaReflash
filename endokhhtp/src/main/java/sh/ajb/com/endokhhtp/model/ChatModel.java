package sh.ajb.com.endokhhtp.model;

/**
 * Created by liuwen on 2016/12/21.
 */
public class ChatModel {
    public String mMsg;
    public SendStatus mSendStatus;
    public UserType mUserType;

    public ChatModel(String msg, UserType userType, SendStatus sendStatus) {
        mMsg = msg;
        mUserType = userType;
        mSendStatus = sendStatus;
    }

    public enum UserType {
        Other, Me
    }

    public enum SendStatus {
        Success, Failure
    }
}
