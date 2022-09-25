package com.smp.app.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "file_return_requested_rule_detail")
public class FileReturnRuleAssociatedDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private FileToReturnRequestDetail fileToReturnRequestDetail;
    private ProjectRuleDetail projectRuleDetail;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @ManyToOne
    @JoinColumn(name = "file_return_req_id")
    public FileToReturnRequestDetail getFileToReturnRequestDetail() {
        return fileToReturnRequestDetail;
    }

    public void setFileToReturnRequestDetail(FileToReturnRequestDetail fileToReturnRequestDetail) {
        this.fileToReturnRequestDetail = fileToReturnRequestDetail;
    }


    @ManyToOne
    @JoinColumn(name = "rule_id")
    public ProjectRuleDetail getProjectRuleDetail() {
        return projectRuleDetail;
    }

    public void setProjectRuleDetail(ProjectRuleDetail projectRuleDetail) {
        this.projectRuleDetail = projectRuleDetail;
    }

}
