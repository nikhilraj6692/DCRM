package com.smp.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "provision_book_detail")
public class ProvisionBookDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer bookId;
    private String bookName;
    private List<ProjectRuleDetail> projectRuleList = new ArrayList<ProjectRuleDetail>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Column(name = "book_name")
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @OneToMany(mappedBy = "provisionBookDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ProjectRuleDetail> getProjectRuleList() {
        return projectRuleList;
    }

    public void setProjectRuleList(List<ProjectRuleDetail> projectRuleList) {
        this.projectRuleList = projectRuleList;
    }
}
