package com.hust.datn.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link com.hust.datn.domain.SysRoleModule} entity.
 */
public class SysRoleModuleDTO implements Serializable {

    private Long id;
    private String roleCode;
    private String moduleCode;
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysRoleModuleDTO)) {
            return false;
        }

        return id != null && id.equals(((SysRoleModuleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysRoleModuleDTO{" +
            "id=" + getId() +
            "}";
    }
}
