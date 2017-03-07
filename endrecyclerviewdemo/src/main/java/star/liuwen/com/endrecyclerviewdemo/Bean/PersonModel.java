package star.liuwen.com.endrecyclerviewdemo.Bean;

/**
 * Created by liuwen on 2017/3/7.
 */
public class PersonModel extends ModelBase {


    private long id;
    private String name;
    private int age;

    public PersonModel(long id, String name, int age, String data) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.data = data;
        BEAN_TYPE = 1;
        BEAN_SORT = 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
