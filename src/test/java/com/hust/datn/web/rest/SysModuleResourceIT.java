package com.hust.datn.web.rest;

import com.hust.datn.BookstoreappApp;
import com.hust.datn.domain.SysModule;
import com.hust.datn.repository.SysModuleRepository;
import com.hust.datn.service.SysModuleService;
import com.hust.datn.service.dto.SysModuleDTO;
import com.hust.datn.service.mapper.SysModuleMapper;

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
 * Integration tests for the {@link SysModuleResource} REST controller.
 */
@SpringBootTest(classes = BookstoreappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SysModuleResourceIT {

    @Autowired
    private SysModuleRepository sysModuleRepository;

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysModuleService sysModuleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysModuleMockMvc;

    private SysModule sysModule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysModule createEntity(EntityManager em) {
        SysModule sysModule = new SysModule();
        return sysModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysModule createUpdatedEntity(EntityManager em) {
        SysModule sysModule = new SysModule();
        return sysModule;
    }

    @BeforeEach
    public void initTest() {
        sysModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysModule() throws Exception {
        int databaseSizeBeforeCreate = sysModuleRepository.findAll().size();
        // Create the SysModule
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(sysModule);
        restSysModuleMockMvc.perform(post("/api/sys-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeCreate + 1);
        SysModule testSysModule = sysModuleList.get(sysModuleList.size() - 1);
    }

    @Test
    @Transactional
    public void createSysModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysModuleRepository.findAll().size();

        // Create the SysModule with an existing ID
        sysModule.setId(1L);
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(sysModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysModuleMockMvc.perform(post("/api/sys-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysModules() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        // Get all the sysModuleList
        restSysModuleMockMvc.perform(get("/api/sys-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysModule.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSysModule() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        // Get the sysModule
        restSysModuleMockMvc.perform(get("/api/sys-modules/{id}", sysModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysModule.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSysModule() throws Exception {
        // Get the sysModule
        restSysModuleMockMvc.perform(get("/api/sys-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysModule() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        int databaseSizeBeforeUpdate = sysModuleRepository.findAll().size();

        // Update the sysModule
        SysModule updatedSysModule = sysModuleRepository.findById(sysModule.getId()).get();
        // Disconnect from session so that the updates on updatedSysModule are not directly saved in db
        em.detach(updatedSysModule);
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(updatedSysModule);

        restSysModuleMockMvc.perform(put("/api/sys-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isOk());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeUpdate);
        SysModule testSysModule = sysModuleList.get(sysModuleList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSysModule() throws Exception {
        int databaseSizeBeforeUpdate = sysModuleRepository.findAll().size();

        // Create the SysModule
        SysModuleDTO sysModuleDTO = sysModuleMapper.toDto(sysModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysModuleMockMvc.perform(put("/api/sys-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysModule in the database
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysModule() throws Exception {
        // Initialize the database
        sysModuleRepository.saveAndFlush(sysModule);

        int databaseSizeBeforeDelete = sysModuleRepository.findAll().size();

        // Delete the sysModule
        restSysModuleMockMvc.perform(delete("/api/sys-modules/{id}", sysModule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysModule> sysModuleList = sysModuleRepository.findAll();
        assertThat(sysModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
