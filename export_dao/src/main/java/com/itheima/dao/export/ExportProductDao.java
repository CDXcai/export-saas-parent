package com.itheima.dao.export;


import com.itheima.domain.export.ExportProduct;
import com.itheima.domain.export.ExportProductExample;

import java.util.List;

public interface ExportProductDao {
	/**
	 * 根据id删除
	 */
    int deleteByPrimaryKey(String id);

	/**
	 * 保存
	 */
    int insertSelective(ExportProduct record);

	/**
	 * 条件查询
	 */
    List<ExportProduct> selectByExample(ExportProductExample example);

	/**
	 * 根据id查询
	 */
    ExportProduct selectByPrimaryKey(String id);

	/**
	 * 更新
	 */
    int updateByPrimaryKeySelective(ExportProduct record);

	//根据报运id查询报运货物的数据
	List<ExportProduct> selectByExportId(String exportId);
}