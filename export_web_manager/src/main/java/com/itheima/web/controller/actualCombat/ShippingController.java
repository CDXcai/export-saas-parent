package com.itheima.web.controller.actualCombat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.actualCombat.PackingList;
import com.itheima.domain.actualCombat.PackingListExample;
import com.itheima.domain.actualCombat.Shipping;
import com.itheima.domain.actualCombat.ShippingExample;
import com.itheima.domain.system.User;
import com.itheima.service.actualCombat.PackingListService;
import com.itheima.service.actualCombat.ShippingService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/12 16:05
 * @Description: 委托单
 * @version:
 */
@Controller
@RequestMapping("/actualCombat/shipping")
public class ShippingController extends BaseController {

    @Reference
    private ShippingService shippingService;

    @Reference
    private PackingListService packingListService;

    /**
     * 委托单列表查询
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", name = "委托单列表查询")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        ShippingExample example = new ShippingExample();
        ShippingExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        PageInfo pageInfo = shippingService.findAll(example, page, size);
        // System.out.println(pageInfo);
        request.setAttribute("page", pageInfo);
        return "actualCombat/shipping/shipping-list";
    }


    @RequestMapping(value = "/toAdd", name = "跳转委托单新增页面")
    public String toAdd() {
        //System.out.println("新增");
        // 获取状态为1的装箱单
        PackingListExample example = new PackingListExample();
        PackingListExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        criteria.andStateEqualTo(1L);
        //
        /**
         * 不分页查询，需要service 提供一个方法
         * @Override
         *     public List<PackingList> findAll(PackingListExample example) {
         *         return packingListDao.selectByExample(example);
         *     }
         */
        List<PackingList> packingLists = packingListService.findAll(example);
        request.setAttribute("packingLists",packingLists);
        return "actualCombat/shipping/shipping-add";
    }


    @RequestMapping(value = "/toUpdate", name = "跳转委托单修改页面")
    public String toUpdate(String id) {
        // 获取委托单信息
        Shipping shipping = shippingService.findById(id);
        //System.out.println("shipping = " + shipping);
        request.setAttribute("shipping",shipping);
        // 查询 该委托单对应的装箱单
        PackingList packingList = packingListService.findById(id);
        request.setAttribute("packingList",packingList);
        return "actualCombat/shipping/shipping-update";
    }

    @RequestMapping(value = "/edit", name = "委托单新增或修改")
    public String edit(Shipping shipping,String packingListId) {
        shipping.setCompanyId(super.companyId);
        shipping.setCompanyName(super.companyName);
        if (StringUtils.isEmpty(shipping.getShippingOrderId())){
            User loginUser = (User) session.getAttribute("loginUser");
            shipping.setCreateBy(loginUser.getId());
            shipping.setCreateDept(loginUser.getDeptId());
            shipping.setCreateTime(new Date());
            // 设置委托单的id和装箱单一致
            shipping.setShippingOrderId(packingListId);
            shippingService.save(shipping);
        }else{
            shippingService.update(shipping);
        }
        return "redirect:/actualCombat/shipping/list.do";
    }

    @RequestMapping(value = "submit", name = "委托单提交")
    public String submit(String id) {
//        System.out.println("id = " + id);
        Shipping shipping = new Shipping();
        shipping.setShippingOrderId(id);
        shipping.setState(1);
        // 修改 委托单状态为 已提交 state = 1;
        shippingService.update(shipping);
        return "redirect:/actualCombat/shipping/list.do";
    }

    @RequestMapping(value = "cancel", name = "委托单取消")
    public String cancel(String id) {
        Shipping shipping = new Shipping();
        shipping.setShippingOrderId(id);
        shipping.setState(0);
        // 修改 委托单状态为 草稿 state = 0;
        shippingService.update(shipping);
        return "redirect:/actualCombat/shipping/list.do";
    }


    @RequestMapping(value = "delete", name = "委托单删除")
    public String delete(String id) {
        shippingService.delete(id);
        return "redirect:/actualCombat/shipping/list.do";
    }

    @RequestMapping(value = "toMap", name = "地图")
    public String toMap(String id){

        // 查询指定id的对象
        Shipping shipping = shippingService.findById(id);
        // 添加到请求域中
        request.setAttribute("startAddress",shipping.getPortOfTrans());
        request.setAttribute("endAddress",shipping.getPortOfDischarge());
        return "actualCombat/shipping/shipping-map";
    }

}
