package com.wx.inspect.transaction.single.mapper;

import com.wx.inspect.transaction.single.po.TbUserLogsPo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据访问
 * 
 * @author wx
 * @date 2019/08/20
 */
@Component
public interface TbUserLogsMapper {

    /**
     * 插入一条数据
     * 
     * @param tbUserLogsPo 待插入对象
     */
    int insert(TbUserLogsPo tbUserLogsPo);

    /**
     * 批量插入多条数据
     * 
     * @param list 待插入对象列表
     */
    void batchInsert(List<TbUserLogsPo> list);

    /**
     * 根据主键删除
     * 
     * @param id 主键值
     * @return 影响条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 根据主键更新
     * 
     * @param condition 要更新的对象
     * @return 影响条数
     */
    int updateByPrimaryKey(TbUserLogsPo condition);

    /**
     * 根据主键查询
     * 
     * @param id 主键值
     * @return 根据主键查询到的对象
     */
    TbUserLogsPo queryByPrimaryKey(Integer id);

    /**
     * 根据条件查询列表
     * 
     * @param condition 查询条件
     * @return 查询出来的对象列表
     */
    List<TbUserLogsPo> queryList(TbUserLogsPo condition);

    /**
     * 根据条件统计数量
     * 
     * @param condition 统计条件
     * @return 记录总数
     */
    int count(TbUserLogsPo condition);
}
