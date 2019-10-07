package life.bg.cloud.controller;

import life.bg.cloud.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author 宝哥
 * @date 2019/10/3
 * TODO
 */
@RestController
@RequestMapping("/consumer/dept")
public class ConsumerController {

    /**
     * RestTemplate 提供rest的访问
     */
    @Autowired
    private RestTemplate restTemplate;

//    private static final String URL = "http://localhost:8001";

    // CLOUD-SERVER-PROVIDER 提供的微服务名称 相当于 ip+port
    // 注意: 服务名称 == ip + port
    private static final String URL = "http://CLOUD-SERVER-PROVIDER";

    @PostMapping("/add")
    public Boolean add(Dept dept){
        return restTemplate.postForObject(URL+"/dept/add",dept,Boolean.class);
    }

    @GetMapping("/get/{id}")
    public Dept get(@PathVariable int id){
        return restTemplate.getForObject(URL+"/dept/get/"+id,Dept.class);
    }
    @GetMapping("/list")
    public List<Dept> list(){
        return restTemplate.getForObject(URL+"/dept/list",List.class);
    }
    @GetMapping("/discovery")
    public Object discovery(){
        return restTemplate.getForObject(URL+"/dept/discovery",Object.class);
    }

}
