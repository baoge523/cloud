package life.bg.cloud.mapper;

import life.bg.cloud.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 宝哥
 * @date 2019/10/2
 * TODO
 *
 * mybatis 的动态代理 如果想要加入IOC中，那么就需要使用 @Mapper 或者 @MapperScan
 */
@Mapper
public interface DeptMapper {

    /**
     * 插入一条记录,并把插入后的id，返回给对象
     * @param dept
     * @return 0 表示插入失败，1或者大于1 表示插入成功的条数
     */
    int insert(Dept dept);

    /**
     * 通过id获取一条记录
     * @param id
     * @return
     */
    Dept queryForOne(int id);

    /**
     * 获取所有的记录
     * @return
     */
    List<Dept> queryForMany();


}
