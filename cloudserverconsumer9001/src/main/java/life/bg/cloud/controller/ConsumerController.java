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

    private static final String URL = "http://localhost:8001/dept";

    @PostMapping("/add")
    public Boolean add(Dept dept){
        return restTemplate.postForObject(URL+"/add",dept,Boolean.class);
    }

    @GetMapping("/get/{id}")
    public Dept get(@PathVariable int id){
        return restTemplate.getForObject(URL+"/get/"+id,Dept.class);
    }
    @GetMapping("/list")
    public List<Dept> list(){
        return restTemplate.getForObject(URL+"/list",List.class);
    }
    @GetMapping("/discovery")
    public Object discovery(){
        return restTemplate.getForObject(URL+"/discovery",Object.class);
    }

}
