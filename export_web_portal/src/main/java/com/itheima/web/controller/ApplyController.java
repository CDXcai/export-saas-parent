package com.itheima.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {

	@Reference //使用dubbo的service
	private CompanyService companyService;

	@RequestMapping("/apply")
	@ResponseBody
	public String apply(Company company) {
		try {
			companyService.save(company);
			return "1";
		}catch (Exception e){
			e.printStackTrace();
		}
		return "2";
	}
}
