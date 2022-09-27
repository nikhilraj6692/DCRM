package com.smp.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company_detail")
public class CompanyDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer companyId;
    private String companyName;
    private Date createdDate;
    private List<ProjectDetail> projectList = new ArrayList<ProjectDetail>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @OneToMany(mappedBy = "companyDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ProjectDetail> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectDetail> projectList) {
        this.projectList = projectList;
    }

}
