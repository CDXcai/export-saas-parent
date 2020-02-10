package com.itheima.domain.system;

/**
 * 部门表
 */
public class Dept  {
    private String id; //部门id
    private String deptName; //部门名称
    private Dept parent;//上级部门
    //逻辑删除和物理删除(前面全都是物理删除)
    //decimal(6,0) 表示 六位整数 没有小数
    private Integer state; //部门的状态

    private String companyId;//企业的id
    private String companyName; //企业的名称

    @Override
    public String toString() {
        return "Dept{" +
                "id='" + id + '\'' +
                ", deptName='" + deptName + '\'' +
                ", parent=" + parent +
                ", state=" + state +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Dept getParent() {
        return parent;
    }

    public void setParent(Dept parent) {
        this.parent = parent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
