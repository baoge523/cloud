package life.bg.cloud.fallback;

// feign中有Hystrix
import feign.hystrix.FallbackFactory;
import life.bg.cloud.entity.Dept;
import life.bg.cloud.service.DeptClientService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // 需要将该FallbackFactory的实现类注入到IOC中
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public boolean post(Dept dept) {
                return false;
            }

            @Override
            public Dept get(int id) {
                return new Dept(id,"没有查找到指定id="+id+"的部门","no this dept id in db");
            }

            @Override
            public List<Dept> list() {
                return null;
            }
        };
    }
}
