package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.SysRoleModule;
import com.hust.datn.repository.SysRoleModuleRepository;
import com.hust.datn.service.SysRoleModuleService;
import com.hust.datn.service.dto.SysRoleModuleDTO;
import com.hust.datn.service.mapper.SysRoleModuleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SysRoleModuleResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SysRoleModuleResourceIT {

    @Autowired
    private SysRoleModuleRepository sysRoleModuleRepository;

    @Autowired
    private SysRoleModuleMapper sysRoleModuleMapper;

    @Autowired
    private SysRoleModuleService sysRoleModuleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysRoleModuleMockMvc;

    private SysRoleModule sysRoleModule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysRoleModule createEntity(EntityManager em) {
        SysRoleModule sysRoleModule = new SysRoleModule();
        return sysRoleModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysRoleModule createUpdatedEntity(EntityManager em) {
        SysRoleModule sysRoleModule = new SysRoleModule();
        return sysRoleModule;
    }

    @BeforeEach
    public void initTest() {
        sysRoleModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysRoleModule() throws Exception {
        int databaseSizeBeforeCreate = sysRoleModuleRepository.findAll().size();
        // Create the SysRoleModule
        SysRoleModuleDTO sysRoleModuleDTO = sysRoleModuleMapper.toDto(sysRoleModule);
        restSysRoleModuleMockMvc.perform(post("/api/sys-role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysRoleModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the SysRoleModule in the database
        List<SysRoleModule> sysRoleModuleList = sysRoleModuleRepository.findAll();
        assertThat(sysRoleModuleList).hasSize(databaseSizeBeforeCreate + 1);
        SysRoleModule testSysRoleModule = sysRoleModuleList.get(sysRoleModuleList.size() - 1);
    }

    @Test
    @Transactional
    public void createSysRoleModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysRoleModuleRepository.findAll().size();

        // Create the SysRoleModule with an existing ID
        sysRoleModule.setId(1L);
        SysRoleModuleDTO sysRoleModuleDTO = sysRoleModuleMapper.toDto(sysRoleModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysRoleModuleMockMvc.perform(post("/api/sys-role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysRoleModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysRoleModule in the database
        List<SysRoleModule> sysRoleModuleList = sysRoleModuleRepository.findAll();
        assertThat(sysRoleModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysRoleModules() throws Exception {
        // Initialize the database
        sysRoleModuleRepository.saveAndFlush(sysRoleModule);

        // Get all the sysRoleModuleList
        restSysRoleModuleMockMvc.perform(get("/api/sys-role-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysRoleModule.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSysRoleModule() throws Exception {
        // Initialize the database
        sysRoleModuleRepository.saveAndFlush(sysRoleModule);

        // Get the sysRoleModule
        restSysRoleModuleMockMvc.perform(get("/api/sys-role-modules/{id}", sysRoleModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysRoleModule.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSysRoleModule() throws Exception {
        // Get the sysRoleModule
        restSysRoleModuleMockMvc.perform(get("/api/sys-role-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysRoleModule() throws Exception {
        // Initialize the database
        sysRoleModuleRepository.saveAndFlush(sysRoleModule);

        int databaseSizeBeforeUpdate = sysRoleModuleRepository.findAll().size();

        // Update the sysRoleModule
        SysRoleModule updatedSysRoleModule = sysRoleModuleRepository.findById(sysRoleModule.getId()).get();
        // Disconnect from session so that the updates on updatedSysRoleModule are not directly saved in db
        em.detach(updatedSysRoleModule);
        SysRoleModuleDTO sysRoleModuleDTO = sysRoleModuleMapper.toDto(updatedSysRoleModule);

        restSysRoleModuleMockMvc.perform(put("/api/sys-role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysRoleModuleDTO)))
            .andExpect(status().isOk());

        // Validate the SysRoleModule in the database
        List<SysRoleModule> sysRoleModuleList = sysRoleModuleRepository.findAll();
        assertThat(sysRoleModuleList).hasSize(databaseSizeBeforeUpdate);
        SysRoleModule testSysRoleModule = sysRoleModuleList.get(sysRoleModuleList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSysRoleModule() throws Exception {
        int databaseSizeBeforeUpdate = sysRoleModuleRepository.findAll().size();

        // Create the SysRoleModule
        SysRoleModuleDTO sysRoleModuleDTO = sysRoleModuleMapper.toDto(sysRoleModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysRoleModuleMockMvc.perform(put("/api/sys-role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysRoleModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysRoleModule in the database
        List<SysRoleModule> sysRoleModuleList = sysRoleModuleRepository.findAll();
        assertThat(sysRoleModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysRoleModule() throws Exception {
        // Initialize the database
        sysRoleModuleRepository.saveAndFlush(sysRoleModule);

        int databaseSizeBeforeDelete = sysRoleModuleRepository.findAll().size();

        // Delete the sysRoleModule
        restSysRoleModuleMockMvc.perform(delete("/api/sys-role-modules/{id}", sysRoleModule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysRoleModule> sysRoleModuleList = sysRoleModuleRepository.findAll();
        assertThat(sysRoleModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
