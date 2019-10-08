package life.bg.cloud.controller;

import life.bg.cloud.entity.Dept;
import life.bg.cloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 宝哥
 * @date 2019/10/3
 * TODO
 */
@RestController
@RequestMapping("/consumer/dept")
public class ConsumerController {

    @Autowired
    private DeptClientService deptClientService;


    @PostMapping("/add")
    public Boolean add(Dept dept){
       return deptClientService.post(dept);
    }

    @GetMapping("/get/{id}")
    public Dept get(@PathVariable int id){
        return deptClientService.get(id);
    }
    @GetMapping("/list")
    public List<Dept> list(){
        return deptClientService.list();
    }

}
