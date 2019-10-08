package life.bg.cloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import life.bg.cloud.entity.Dept;
import life.bg.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 宝哥
 * @date 2019/10/2
 * TODO
 */
@RestController
@RequestMapping("/dept")
public class DempController {

    @Autowired
    private DeptService deptService;

    /**
     * 服务发现的客户端，spring 提供的组件
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/add")
    public boolean post(Dept dept){
       return deptService.post(dept);
    }

    /**
     * rest 风格 /{id} 表示参数
     * @return
     */
    @GetMapping("/get/{id}")
    @HystrixCommand(fallbackMethod = "backGet")  // 现在通过service接口统一的做fallbackFactory处理
    public Dept get(@PathVariable("id")int id){
        Dept dept = deptService.get(id);
        /**
         * 做了FallbackFactory的指定，这个异常要不要都无所谓了
         */
        if (dept == null){
            throw new NullPointerException("dept is null");
        }
        return dept;
    }

    /**
     * 用于 Hystrix熔断器的回调
     *
     *
     * 这种方式的缺点是: 需要做熔断处理的每一个方法都需要添加@HystrixCommand注解，和定义一个方法来做处理
     *
     * 解决方案:
     *   在service层就做处理，针对于一个service接口做集中处理
     *
     * @param id
     * @return
     */
    public Dept backGet(int id){
        return new Dept(id,"没有查找到指定id="+id+",的部门,@Hystrix","没有数据在mysql中");
    }


    @GetMapping("/list")
    public List<Dept> list(){
        return deptService.list();
    }


}
