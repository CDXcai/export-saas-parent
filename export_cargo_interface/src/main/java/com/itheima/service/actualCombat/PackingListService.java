package com.itheima.service.actualCombat;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.actualCombat.PackingList;
import com.itheima.domain.actualCombat.PackingListExample;

import java.util.List;

public interface PackingListService {

    /**
     * 保存
     */
    void save(PackingList packingList);

    /**
     * 更新
     */
    void update(PackingList packingList);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    PackingList findById(String id);

    /**
     * 分页查询
     */
    PageInfo findAll(PackingListExample example, int page, int size);


    /**
     * 不需要分页查询，guojy
     * @param example
     * @return
     */
    List<PackingList> findAll(PackingListExample example);
}
