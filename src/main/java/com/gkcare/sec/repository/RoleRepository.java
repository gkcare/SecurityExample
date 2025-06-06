package com.gkcare.sec.repository;

import com.gkcare.sec.entity.Role;
import com.gkcare.sec.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleType name);

}
