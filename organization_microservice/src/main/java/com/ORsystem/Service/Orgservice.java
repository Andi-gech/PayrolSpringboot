package com.ORsystem.Service;

import com.ORsystem.Model.Depclass;
import com.ORsystem.Model.Orgmodel;
import com.ORsystem.ORgdto.ORgdto;
import com.ORsystem.Repository.DepRepository;
import com.ORsystem.Repository.OrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Orgservice {
    @Autowired
    private OrgRepository oRgRepository;
    @Autowired
    private DepRepository depRepository;


    public Orgservice(OrgRepository oRgRepository) {
        this.oRgRepository = oRgRepository;
    }

    public ORgdto createorganization(ORgdto oRgdto) {
        Orgmodel org = new Orgmodel();
        org.setName(oRgdto.getName());
        org.setAbrivatedname(oRgdto.getAbrivatedname());
        org.setStablishedyear(oRgdto.getStablishedyear());
        org.setMission(oRgdto.getMission());
        org.setVission(oRgdto.getVission());
        org.setLogo(oRgdto.getLogo());
        if (oRgdto.getDepartments() != null) {
            org.setDepartments(oRgdto.getDepartments());
        }

        Orgmodel savedOrg = oRgRepository.save(org);
        return new ORgdto(
                savedOrg.getId(),

                savedOrg.getAbrivatedname(),
                savedOrg.getName(),
                savedOrg.getLogo(),
                savedOrg.getStablishedyear(),
                savedOrg.getMission(),
                savedOrg.getVission(),
                savedOrg.getDepartments()
        );
    }


    public List<ORgdto> getAllOrganizations() {
        List<Orgmodel> orgList = oRgRepository.findAll();
        return orgList.stream().map(org -> new ORgdto(
                org.getId(),
                org.getAbrivatedname(),
                org.getName(),
                org.getLogo(),
                org.getStablishedyear(),
                org.getMission(),
                org.getVission(),
                org.getDepartments()
        )).collect(Collectors.toList());
    }
    public ORgdto updateorganization(ORgdto oRgdto ) {
        Optional<Orgmodel> org = oRgRepository.findById(oRgdto.getId());
        Orgmodel savedOrg = org.get();
        if(oRgdto.getAbrivatedname() != null) {
            savedOrg.setAbrivatedname(oRgdto.getAbrivatedname());
        }
        if(oRgdto.getName() != null) {
            savedOrg.setName(oRgdto.getName());

        }
        if(oRgdto.getLogo() != null) {
            savedOrg.setLogo(oRgdto.getLogo());
        }
        if(oRgdto.getStablishedyear() != null) {
            savedOrg.setStablishedyear(oRgdto.getStablishedyear());
        }
        if(oRgdto.getMission() != null) {
            savedOrg.setMission(oRgdto.getMission());
        }
        if(oRgdto.getVission() != null) {
            savedOrg.setVission(oRgdto.getVission());
        }
        Orgmodel savedOrg1 = oRgRepository.save(savedOrg);
        return new ORgdto(
                savedOrg1.getId(),
                savedOrg1.getName(),
                savedOrg1.getAbrivatedname(),
                savedOrg1.getVission(),
                savedOrg1.getMission(),
                savedOrg1.getStablishedyear(),
                savedOrg1.getLogo(),
                savedOrg1.getDepartments()
        );


    }

    public Optional<ORgdto> getOrganizationById(Long id) {
        Optional<Orgmodel> orgOptional = oRgRepository.findById(id);
        return orgOptional.map(org -> new ORgdto(
                org.getId(),
                org.getAbrivatedname(),
                org.getName(),
                org.getLogo(),
                org.getStablishedyear(),
                org.getMission(),
                org.getVission(),
                org.getDepartments()
        ));
    }
    public Depclass addDepartmentToOrganization(Long orgId, Depclass depclass) {
        Optional<Orgmodel> orgOptional = oRgRepository.findById(orgId);

        if (orgOptional.isPresent()) {
            Orgmodel organization = orgOptional.get();
            depclass = depRepository.save(depclass);  // Save department first
            organization.getDepartments().add(depclass);  // Add department to the list
            oRgRepository.save(organization);  // Save the updated organization
            return depclass;
        } else {
            throw new RuntimeException("Organization not found");
        }
    }

    // Method to delete a department
    public boolean deleteDepartment(Long depId) {
        Optional<Depclass> depOptional = depRepository.findById(depId);
        if (depOptional.isPresent()) {
            depRepository.deleteById(depId);
            return true;
        } else {
            return false;
        }
    }



    public boolean deleteOrganization(Long id) {
        if (oRgRepository.existsById(id)) {
            oRgRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
