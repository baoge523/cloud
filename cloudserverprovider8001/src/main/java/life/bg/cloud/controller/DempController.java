package life.bg.cloud.controller;

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
    public Dept get(@PathVariable("id")int id){
        return deptService.get(id);
    }

    @GetMapping("/list")
    public List<Dept> list(){
        return deptService.list();
    }


    @GetMapping("/discovery")
    public Object discovery(){
        // 获取所有注册到eureka中的服务
        List<String> services = discoveryClient.getServices();

        for(String service:services){
            System.out.println(service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-SERVER-PROVIDER");

        for (ServiceInstance instance : instances){
            System.out.println("serviceId:"+instance.getServiceId()+"\t主机名:"+instance.getHost()+"\t端口号:"+instance.getPort()
            +"\turl:"+instance.getUri());
        }
        /**
         * ServiceId ： CLOUD-SERVER-PROVIDER
         * host      ： 192.168.124.1
         * url       ： http://192.168.124.1:8001
         */

        // 返回当前项目的 DiscoveryClient 就会在页面显示该项目注入Eureka中的信息
        // {"services":["cloud-server-provider"],"localServiceInstance":{"host":"192.168.124.1","port":8001,"secure":false,"metadata":{},"uri":"http://192.168.124.1:8001","serviceId":"cloud-server-provider"}}
        return this.discoveryClient;


    }



}
