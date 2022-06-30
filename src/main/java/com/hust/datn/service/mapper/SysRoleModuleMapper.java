package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.SysRoleModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysRoleModule} and its DTO {@link SysRoleModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysRoleModuleMapper extends EntityMapper<SysRoleModuleDTO, SysRoleModule> {



    default SysRoleModule fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysRoleModule sysRoleModule = new SysRoleModule();
        sysRoleModule.setId(id);
        return sysRoleModule;
    }
}
