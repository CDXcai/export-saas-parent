package com.itheima.service.export;


import com.itheima.domain.export.ExportProduct;
import com.itheima.domain.export.ExportProductExample;

import java.util.List;


public interface ExportProductService {

	//根据条件查询
	List<ExportProduct> findAll(ExportProductExample example);
}
