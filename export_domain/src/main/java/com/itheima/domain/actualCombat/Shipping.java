package com.itheima.domain.actualCombat;

import java.io.Serializable;
import java.util.Date;

public class Shipping implements Serializable {

    private String shippingOrderId;
    private String orderType;//n. 命令；顺序；规则；[贸易] 定单
    private String shipper;//n. 托运人；发货人；货主
    private String consignee;  //n. 收件人；受托者；承销人
    private String notifyParty;//通知程序
    private String lcNo;
    private String portOfLoading;  //多孔的
    private String portOfTrans; //港口 装运港  pref. 表“横穿”；表“进入”
    private String portOfDischarge;//港口 卸货港  vt. 解雇；卸下；放出；免除
    private Date loadingDate;   //装货日期
    private Date limitDate;  //限定日期
    private String isBatch;  //n. 一批；一炉；一次所制之量
    private String isTrans;   //n. 传动装置；变速箱
    private String copyNum;  //abbr. 号码（number）；数字（numeral）
    private String remark;   //. 评论；觉察
    private String specialCondition;  //特殊条件
    private String freight;   //. 货运；运费；船货
    private String checkBy;   //检查
    private Integer state;   //vt. 规定；声明；陈述
    private String createBy;  // 创造，创作；造成
    private String createDept;   //
    private Date createTime;   //创建日期
    private String companyId;  //创建企业id
    private String companyName;//创建企业名称

    @Override
    public String toString() {
        return "Shipping{" +
                "shippingOrderId='" + shippingOrderId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", shipper='" + shipper + '\'' +
                ", consignee='" + consignee + '\'' +
                ", notifyParty='" + notifyParty + '\'' +
                ", lcNo='" + lcNo + '\'' +
                ", portOfLoading='" + portOfLoading + '\'' +
                ", portOfTrans='" + portOfTrans + '\'' +
                ", portOfDischarge='" + portOfDischarge + '\'' +
                ", loadingDate=" + loadingDate +
                ", limitDate=" + limitDate +
                ", isBatch='" + isBatch + '\'' +
                ", isTrans='" + isTrans + '\'' +
                ", copyNum='" + copyNum + '\'' +
                ", remark='" + remark + '\'' +
                ", specialCondition='" + specialCondition + '\'' +
                ", freight='" + freight + '\'' +
                ", checkBy='" + checkBy + '\'' +
                ", state=" + state +
                ", createBy='" + createBy + '\'' +
                ", createDept='" + createDept + '\'' +
                ", createTime=" + createTime +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.SHIPPING_ORDER_ID
     *
     * @return the value of co_shipping_order.SHIPPING_ORDER_ID
     *
     * @mbg.generated
     */
    public String getShippingOrderId() {
        return shippingOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.SHIPPING_ORDER_ID
     *
     * @param shippingOrderId the value for co_shipping_order.SHIPPING_ORDER_ID
     *
     * @mbg.generated
     */
    public void setShippingOrderId(String shippingOrderId) {
        this.shippingOrderId = shippingOrderId == null ? null : shippingOrderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.ORDER_TYPE
     *
     * @return the value of co_shipping_order.ORDER_TYPE
     *
     * @mbg.generated
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.ORDER_TYPE
     *
     * @param orderType the value for co_shipping_order.ORDER_TYPE
     *
     * @mbg.generated
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.SHIPPER
     *
     * @return the value of co_shipping_order.SHIPPER
     *
     * @mbg.generated
     */
    public String getShipper() {
        return shipper;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.SHIPPER
     *
     * @param shipper the value for co_shipping_order.SHIPPER
     *
     * @mbg.generated
     */
    public void setShipper(String shipper) {
        this.shipper = shipper == null ? null : shipper.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.CONSIGNEE
     *
     * @return the value of co_shipping_order.CONSIGNEE
     *
     * @mbg.generated
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.CONSIGNEE
     *
     * @param consignee the value for co_shipping_order.CONSIGNEE
     *
     * @mbg.generated
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.NOTIFY_PARTY
     *
     * @return the value of co_shipping_order.NOTIFY_PARTY
     *
     * @mbg.generated
     */
    public String getNotifyParty() {
        return notifyParty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.NOTIFY_PARTY
     *
     * @param notifyParty the value for co_shipping_order.NOTIFY_PARTY
     *
     * @mbg.generated
     */
    public void setNotifyParty(String notifyParty) {
        this.notifyParty = notifyParty == null ? null : notifyParty.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.LC_NO
     *
     * @return the value of co_shipping_order.LC_NO
     *
     * @mbg.generated
     */
    public String getLcNo() {
        return lcNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.LC_NO
     *
     * @param lcNo the value for co_shipping_order.LC_NO
     *
     * @mbg.generated
     */
    public void setLcNo(String lcNo) {
        this.lcNo = lcNo == null ? null : lcNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.PORT_OF_LOADING
     *
     * @return the value of co_shipping_order.PORT_OF_LOADING
     *
     * @mbg.generated
     */
    public String getPortOfLoading() {
        return portOfLoading;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.PORT_OF_LOADING
     *
     * @param portOfLoading the value for co_shipping_order.PORT_OF_LOADING
     *
     * @mbg.generated
     */
    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading == null ? null : portOfLoading.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.PORT_OF_TRANS
     *
     * @return the value of co_shipping_order.PORT_OF_TRANS
     *
     * @mbg.generated
     */
    public String getPortOfTrans() {
        return portOfTrans;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.PORT_OF_TRANS
     *
     * @param portOfTrans the value for co_shipping_order.PORT_OF_TRANS
     *
     * @mbg.generated
     */
    public void setPortOfTrans(String portOfTrans) {
        this.portOfTrans = portOfTrans == null ? null : portOfTrans.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.PORT_OF_DISCHARGE
     *
     * @return the value of co_shipping_order.PORT_OF_DISCHARGE
     *
     * @mbg.generated
     */
    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.PORT_OF_DISCHARGE
     *
     * @param portOfDischarge the value for co_shipping_order.PORT_OF_DISCHARGE
     *
     * @mbg.generated
     */
    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge == null ? null : portOfDischarge.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.LOADING_DATE
     *
     * @return the value of co_shipping_order.LOADING_DATE
     *
     * @mbg.generated
     */
    public Date getLoadingDate() {
        return loadingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.LOADING_DATE
     *
     * @param loadingDate the value for co_shipping_order.LOADING_DATE
     *
     * @mbg.generated
     */
    public void setLoadingDate(Date loadingDate) {
        this.loadingDate = loadingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.LIMIT_DATE
     *
     * @return the value of co_shipping_order.LIMIT_DATE
     *
     * @mbg.generated
     */
    public Date getLimitDate() {
        return limitDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.LIMIT_DATE
     *
     * @param limitDate the value for co_shipping_order.LIMIT_DATE
     *
     * @mbg.generated
     */
    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.IS_BATCH
     *
     * @return the value of co_shipping_order.IS_BATCH
     *
     * @mbg.generated
     */
    public String getIsBatch() {
        return isBatch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.IS_BATCH
     *
     * @param isBatch the value for co_shipping_order.IS_BATCH
     *
     * @mbg.generated
     */
    public void setIsBatch(String isBatch) {
        this.isBatch = isBatch == null ? null : isBatch.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.IS_TRANS
     *
     * @return the value of co_shipping_order.IS_TRANS
     *
     * @mbg.generated
     */
    public String getIsTrans() {
        return isTrans;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.IS_TRANS
     *
     * @param isTrans the value for co_shipping_order.IS_TRANS
     *
     * @mbg.generated
     */
    public void setIsTrans(String isTrans) {
        this.isTrans = isTrans == null ? null : isTrans.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.COPY_NUM
     *
     * @return the value of co_shipping_order.COPY_NUM
     *
     * @mbg.generated
     */
    public String getCopyNum() {
        return copyNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.COPY_NUM
     *
     * @param copyNum the value for co_shipping_order.COPY_NUM
     *
     * @mbg.generated
     */
    public void setCopyNum(String copyNum) {
        this.copyNum = copyNum == null ? null : copyNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.REMARK
     *
     * @return the value of co_shipping_order.REMARK
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.REMARK
     *
     * @param remark the value for co_shipping_order.REMARK
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.SPECIAL_CONDITION
     *
     * @return the value of co_shipping_order.SPECIAL_CONDITION
     *
     * @mbg.generated
     */
    public String getSpecialCondition() {
        return specialCondition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.SPECIAL_CONDITION
     *
     * @param specialCondition the value for co_shipping_order.SPECIAL_CONDITION
     *
     * @mbg.generated
     */
    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition == null ? null : specialCondition.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.FREIGHT
     *
     * @return the value of co_shipping_order.FREIGHT
     *
     * @mbg.generated
     */
    public String getFreight() {
        return freight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.FREIGHT
     *
     * @param freight the value for co_shipping_order.FREIGHT
     *
     * @mbg.generated
     */
    public void setFreight(String freight) {
        this.freight = freight == null ? null : freight.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.CHECK_BY
     *
     * @return the value of co_shipping_order.CHECK_BY
     *
     * @mbg.generated
     */
    public String getCheckBy() {
        return checkBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.CHECK_BY
     *
     * @param checkBy the value for co_shipping_order.CHECK_BY
     *
     * @mbg.generated
     */
    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy == null ? null : checkBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.STATE
     *
     * @return the value of co_shipping_order.STATE
     *
     * @mbg.generated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.STATE
     *
     * @param state the value for co_shipping_order.STATE
     *
     * @mbg.generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.CREATE_BY
     *
     * @return the value of co_shipping_order.CREATE_BY
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.CREATE_BY
     *
     * @param createBy the value for co_shipping_order.CREATE_BY
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.CREATE_DEPT
     *
     * @return the value of co_shipping_order.CREATE_DEPT
     *
     * @mbg.generated
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.CREATE_DEPT
     *
     * @param createDept the value for co_shipping_order.CREATE_DEPT
     *
     * @mbg.generated
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.CREATE_TIME
     *
     * @return the value of co_shipping_order.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.CREATE_TIME
     *
     * @param createTime the value for co_shipping_order.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.company_id
     *
     * @return the value of co_shipping_order.company_id
     *
     * @mbg.generated
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.company_id
     *
     * @param companyId the value for co_shipping_order.company_id
     *
     * @mbg.generated
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column co_shipping_order.company_name
     *
     * @return the value of co_shipping_order.company_name
     *
     * @mbg.generated
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column co_shipping_order.company_name
     *
     * @param companyName the value for co_shipping_order.company_name
     *
     * @mbg.generated
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }
}