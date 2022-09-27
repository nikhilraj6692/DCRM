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
@Table(name = "user_rule_detail")
public class UserRule implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer ruleId;
    private String ruleName;
    private List<UserDetail> userList = new ArrayList<UserDetail>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    @Column(name = "rule_name")
    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    @OneToMany(mappedBy = "userRule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<UserDetail> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDetail> userList) {
        this.userList = userList;
    }

}
