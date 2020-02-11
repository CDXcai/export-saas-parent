package com.itheima.service.actualCombat;



import com.github.pagehelper.PageInfo;
import com.itheima.domain.actualCombat.Shipping;
import com.itheima.domain.actualCombat.ShippingExample;


public interface ShippingService {

	void save(Shipping shipping);
	
	void update(Shipping shipping);

	void delete(String id);

	Shipping findById(String id);


	/**
	 * 分页查询数据
	 * @param example
	 * @param page
	 * @param size
	 * @return
	 */
	PageInfo findAll(ShippingExample example, int page, int size);

	void deleteByContractID(String ContractID);
	
}
