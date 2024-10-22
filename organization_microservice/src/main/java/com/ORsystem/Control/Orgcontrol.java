package com.ORsystem.Control;

import com.ORsystem.Model.Depclass;
import com.ORsystem.ORgdto.ORgdto;
import com.ORsystem.Service.Depservice;
import com.ORsystem.Service.Orgservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class Orgcontrol {

    @Autowired
    private Orgservice orgService;
    @Autowired
    private Depservice depservice;


    @PostMapping
    public ResponseEntity<ORgdto> createOrganization(@RequestBody ORgdto orgdto) {
        ORgdto createdOrg = orgService.createorganization(orgdto);
        return new ResponseEntity<>(createdOrg, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ORgdto> updateOrganization(@PathVariable("id") long id, @RequestBody ORgdto oRgdto) {
        oRgdto.setId(id);
        ORgdto updatedOrg = orgService.updateorganization(oRgdto);
        return ResponseEntity.ok(updatedOrg);
    }
    @PostMapping("/{orgId}/departments")
    public ResponseEntity<?> addDepartment(@PathVariable Long orgId, @RequestBody Depclass depclass) {
        Depclass addedDepartment = orgService.addDepartmentToOrganization(orgId, depclass);
        return ResponseEntity.ok(addedDepartment);
    }




    @GetMapping
    public ResponseEntity<List<ORgdto>> getAllOrganizations() {
        List<ORgdto> organizations = orgService.getAllOrganizations();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ORgdto> getOrganizationById(@PathVariable Long id) {
        return orgService.getOrganizationById(id)
                .map(orgdto -> new ResponseEntity<>(orgdto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete organization by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        boolean isDeleted = orgService.deleteOrganization(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
