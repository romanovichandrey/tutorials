package by.bytechs.ldap.data.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.ldap.LdapName;
import java.io.Serializable;
import java.util.List;

/**
 * @author Romanovich Andrei 02.02.2021 - 10:42
 */
@Entry(objectClasses = {"top", "person", "organizationalPerson", "user"})
public class User implements Serializable {

    @Id
    @JsonIgnore
    private LdapName id;

    @Attribute(name = "cn")
    private String fullName;

    @Attribute(name = "userPassword")
    private String password;

    @Attribute(name = "sAMAccountName")
    private String accountName;

    @Attribute(name = "userPrincipalName")
    private String userPrincipalName;

    @Attribute(name = "objectCategory")
    private String objectCategory;

    @Attribute(name = "mail")
    private String mail;

    @Attribute(name = "uSNCreated")
    private String usnCreated;

    @Attribute(name = "uSNChanged")
    private String usnChanged;

    @Attribute(name = "whenChanged")
    private String whenChanged;

    @Attribute(name = "objectClass")
    private List<String> objectClasses;

    @Attribute(name = "primaryGroupID")
    private Integer primaryGroupID;

    @Attribute(name = "lastLogonTimestamp")
    private Long lastLogonTimestamp;

    @Attribute(name = "userAccountControl")
    private String userAccountControl;

    @Attribute(name = "lockoutTime")
    private String lockoutTime;

    @Attribute(name = "memberOf")
    private List<String> memberOfs;

    public LdapName getId() {
        return id;
    }

    public void setId(LdapName id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsnCreated() {
        return usnCreated;
    }

    public void setUsnCreated(String usnCreated) {
        this.usnCreated = usnCreated;
    }

    public String getUsnChanged() {
        return usnChanged;
    }

    public void setUsnChanged(String usnChanged) {
        this.usnChanged = usnChanged;
    }

    public String getWhenChanged() {
        return whenChanged;
    }

    public void setWhenChanged(String whenChanged) {
        this.whenChanged = whenChanged;
    }

    public List<String> getObjectClasses() {
        return objectClasses;
    }

    public void setObjectClasses(List<String> objectClasses) {
        this.objectClasses = objectClasses;
    }

    public Integer getPrimaryGroupID() {
        return primaryGroupID;
    }

    public void setPrimaryGroupID(Integer primaryGroupID) {
        this.primaryGroupID = primaryGroupID;
    }

    public Long getLastLogonTimestamp() {
        return lastLogonTimestamp;
    }

    public void setLastLogonTimestamp(Long lastLogonTimestamp) {
        this.lastLogonTimestamp = lastLogonTimestamp;
    }

    public String getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(String userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getLockoutTime() {
        return lockoutTime;
    }

    public void setLockoutTime(String lockoutTime) {
        this.lockoutTime = lockoutTime;
    }

    public List<String> getMemberOfs() {
        return memberOfs;
    }

    public void setMemberOfs(List<String> memberOfs) {
        this.memberOfs = memberOfs;
    }
}
