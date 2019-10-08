package life.bg;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  每一个提供者轮询5次后更换下一个提供者
 */
public class RoundRobinRule_zy extends AbstractLoadBalancerRule {

    private static final Logger log = LoggerFactory.getLogger(RoundRobinRule_zy.class);


    private AtomicInteger incrementAndGetModulo = new AtomicInteger(0);
    private AtomicInteger next = new AtomicInteger(0);


    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    public Server choose(Object o) {
        return choose(getLoadBalancer(), o);
    }

    private Server choose(ILoadBalancer lb, Object o) {
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        } else {
            Server server = null;
            // 在一次服务请求过程中，内部寻找服务的次数最多10次
            int count = 0;

            while (true) {
                if (server == null && count++ < 10) {
                    // 存活的所有服务
                    List<Server> reachableServers = lb.getReachableServers();
                    // 总共的所有服务
                    List<Server> allServers = lb.getAllServers();
                    // 存活的个数
                    int upCount = reachableServers.size();
                    // 总共的个数
                    int serverCount = allServers.size();

                    if (upCount != 0 && serverCount != 0) {

                        int nextServerIndex = this.incrementAndGetModulo(serverCount);

                        server = (Server) allServers.get(nextServerIndex);

                        if (server == null) {
                            // 服务为空，当前线程就休息一会
                            Thread.yield();
                        } else {
                            // 当前服务是存活的
                            if (server.isAlive() && server.isReadyToServe()) {
                                return server;
                            }

                            server = null;
                        }
                        continue;
                    }

                    log.warn("No up servers available from load balancer: " + lb);
                    return null;
                }

                if (count >= 10) {
                    log.warn("No available alive servers after 10 tries from load balancer: " + lb);
                }
            }

        }


    }

    /**
     * 每一个服务轮询5次
     * @param modulo
     * @return
     */
    private int incrementAndGetModulo(int modulo){
        if (incrementAndGetModulo.get() % 5 == 0){
            next.incrementAndGet();
        }
        incrementAndGetModulo.incrementAndGet();
        return next.get() % modulo;

    }

}