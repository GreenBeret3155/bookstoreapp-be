package com.hust.datn.service.mapper;


import com.hust.datn.domain.*;
import com.hust.datn.service.dto.SysModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysModule} and its DTO {@link SysModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysModuleMapper extends EntityMapper<SysModuleDTO, SysModule> {



    default SysModule fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysModule sysModule = new SysModule();
        sysModule.setId(id);
        return sysModule;
    }
}
