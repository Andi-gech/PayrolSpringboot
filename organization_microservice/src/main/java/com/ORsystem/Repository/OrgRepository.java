package com.ORsystem.Repository;

import com.ORsystem.Model.Orgmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgRepository extends JpaRepository <Orgmodel,Long>{
}
