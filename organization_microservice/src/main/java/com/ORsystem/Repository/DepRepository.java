package com.ORsystem.Repository;


import com.ORsystem.Model.Depclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepRepository extends JpaRepository<Depclass, Long> {
}