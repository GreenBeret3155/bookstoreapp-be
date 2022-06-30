package com.hust.datn.web.rest;

import com.hust.datn.service.SysRoleModuleService;
import com.hust.datn.web.rest.errors.BadRequestAlertException;
import com.hust.datn.service.dto.SysRoleModuleDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hust.datn.domain.SysRoleModule}.
 */
@RestController
@RequestMapping("/api")
public class SysRoleModuleResource {

    private final Logger log = LoggerFactory.getLogger(SysRoleModuleResource.class);

    private static final String ENTITY_NAME = "sysRoleModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysRoleModuleService sysRoleModuleService;

    public SysRoleModuleResource(SysRoleModuleService sysRoleModuleService) {
        this.sysRoleModuleService = sysRoleModuleService;
    }

    /**
     * {@code POST  /sys-role-modules} : Create a new sysRoleModule.
     *
     * @param sysRoleModuleDTO the sysRoleModuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysRoleModuleDTO, or with status {@code 400 (Bad Request)} if the sysRoleModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-role-modules")
    public ResponseEntity<SysRoleModuleDTO> createSysRoleModule(@RequestBody SysRoleModuleDTO sysRoleModuleDTO) throws URISyntaxException {
        log.debug("REST request to save SysRoleModule : {}", sysRoleModuleDTO);
        if (sysRoleModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysRoleModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysRoleModuleDTO result = sysRoleModuleService.save(sysRoleModuleDTO);
        return ResponseEntity.created(new URI("/api/sys-role-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-role-modules} : Updates an existing sysRoleModule.
     *
     * @param sysRoleModuleDTO the sysRoleModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysRoleModuleDTO,
     * or with status {@code 400 (Bad Request)} if the sysRoleModuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysRoleModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-role-modules")
    public ResponseEntity<SysRoleModuleDTO> updateSysRoleModule(@RequestBody SysRoleModuleDTO sysRoleModuleDTO) throws URISyntaxException {
        log.debug("REST request to update SysRoleModule : {}", sysRoleModuleDTO);
        if (sysRoleModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysRoleModuleDTO result = sysRoleModuleService.save(sysRoleModuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysRoleModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-role-modules} : get all the sysRoleModules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysRoleModules in body.
     */
    @GetMapping("/sys-role-modules")
    public ResponseEntity<List<SysRoleModuleDTO>> getAllSysRoleModules(Pageable pageable) {
        log.debug("REST request to get a page of SysRoleModules");
        Page<SysRoleModuleDTO> page = sysRoleModuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-role-modules/:id} : get the "id" sysRoleModule.
     *
     * @param id the id of the sysRoleModuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysRoleModuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-role-modules/{id}")
    public ResponseEntity<SysRoleModuleDTO> getSysRoleModule(@PathVariable Long id) {
        log.debug("REST request to get SysRoleModule : {}", id);
        Optional<SysRoleModuleDTO> sysRoleModuleDTO = sysRoleModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysRoleModuleDTO);
    }

    /**
     * {@code DELETE  /sys-role-modules/:id} : delete the "id" sysRoleModule.
     *
     * @param id the id of the sysRoleModuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-role-modules/{id}")
    public ResponseEntity<Void> deleteSysRoleModule(@PathVariable Long id) {
        log.debug("REST request to delete SysRoleModule : {}", id);
        sysRoleModuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
