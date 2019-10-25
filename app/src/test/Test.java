import base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test extends BaseTest {

    @Autowired
    private  RedisTemplate redisTemplate;

    @org.junit.Test
    public void test() throws UnsupportedEncodingException {


        HashOperations<String,String,Object> opsForHash = redisTemplate.opsForHash();

        if(!redisTemplate.hasKey("he11")) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("key1", new Person().setAge("1818").setName("wwy"));
            param.put("key1", new Person().setAge("1919").setName("wwy1"));
            param.put("key3", new Person().setAge("18").setName("wwy2"));
            param.put("key5", new Person().setAge("18").setName("wwy3"));
            param.put("key2", new Person().setAge("18").setName("wwy4"));
            opsForHash.putAll("he11", param);
        }
       // List<Object> values = opsForHash.values("he11");
        Map<String,Object> ss = opsForHash.entries("he11");
        Person p = (Person)ss.get("key1");
        System.out.println(p.getAge());


    }






}


class Person implements Serializable {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public Person setAge(String age) {
        this.age = age;
        return this;
    }


}