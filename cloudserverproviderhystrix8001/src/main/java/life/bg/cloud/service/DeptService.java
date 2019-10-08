package life.bg.cloud.service;

import life.bg.cloud.entity.Dept;

import java.util.List;

/**
 * @author 宝哥
 * @date 2019/10/2
 * TODO
 */
public interface DeptService {

    boolean post(Dept dept);

    Dept get(int id);

    List<Dept> list();


}
