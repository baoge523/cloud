package life.bg.cloud.service.impl;

import life.bg.cloud.entity.Dept;
import life.bg.cloud.mapper.DeptMapper;
import life.bg.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 宝哥
 * @date 2019/10/2
 * TODO
 */
@Service
public class DeptServiceImpl implements DeptService {


    @Autowired
    private DeptMapper deptMapper;


    public boolean post(Dept dept) {
        int cloumn = deptMapper.insert(dept);

        if(cloumn < 1){
            return false;
        }

        return true;
    }

    public Dept get(int id) {
        return deptMapper.queryForOne(id);
    }


    public List<Dept> list() {
        return deptMapper.queryForMany();
    }
}
