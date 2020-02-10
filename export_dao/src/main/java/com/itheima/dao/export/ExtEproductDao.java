package com.itheima.dao.export;


import com.itheima.domain.export.ExtEproduct;
import com.itheima.domain.export.ExtEproductExample;

import java.util.List;

public interface ExtEproductDao {
	/**
	 * 根据id删除
	 */
    int deleteByPrimaryKey(String id);

	/**
	 * 保存
	 */
    int insertSelective(ExtEproduct record);

	/**
	 * 条件查询
	 */
    List<ExtEproduct> selectByExample(ExtEproductExample example);

	/**
	 * 根据id查询
	 */
    ExtEproduct selectByPrimaryKey(String id);

	/**
	 * 更新
	 */
    int updateByPrimaryKeySelective(ExtEproduct record);
}