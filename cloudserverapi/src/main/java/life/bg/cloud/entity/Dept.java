package life.bg.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 宝哥
 * @date 2019/10/3
 * TODO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dept implements Serializable {
    /**
     * 部门id
     */
    private int id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门在那个数据库中 database() 函数获取
     */
    private String dbSources;
}
