package sh.ajb.com.endggmm.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwen on 2016/12/27.
 */
public class DataEngine {

    public static List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("title", "这是平安夜");
        list.add(map);
        map = new HashMap<>();
        map.put("title", "这是圣诞节");
        list.add(map);
        map = new HashMap<>();
        map.put("title", "这是工作日");
        list.add(map);

        map = new HashMap<>();
        map.put("title", "这是工作日");
        list.add(map);
        map = new HashMap<>();
        map.put("title", "这是圣诞节");
        list.add(map);
        return list;
    }

}
