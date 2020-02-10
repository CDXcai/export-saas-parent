package com.itheima.dao.cargo;


import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.vo.ContractProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    //出货表查询
    List<ContractProductVo> findByShipTime(@Param("shipTime") String inputDate,@Param("companyId") String companyId);
}