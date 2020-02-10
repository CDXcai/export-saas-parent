package com.itheima.service.export;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.export.Export;
import com.itheima.domain.export.ExportExample;
import com.itheima.domain.vo.ExportResult;

import java.util.List;


public interface ExportService {

    Export findById(String id);

    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo findAll(ExportExample example, int page, int size);
	List<Export> findAll(ExportExample example);

	//更新报运结果
	void exportE(ExportResult result);
}
