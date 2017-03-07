package star.liuwen.com.endrecyclerviewdemo.Bean;

/**
 * Created by liuwen on 2017/3/7.
 */
public class AnimalModel extends ModelBase {

    private long id;
    private String name;
    private int age;
    private String address;


    public AnimalModel(long id, String name, int age, String address, String data) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.data = data;
        BEAN_TYPE = 2;
        BEAN_SORT = 2;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
