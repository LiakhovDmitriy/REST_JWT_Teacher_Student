package com.gmail.dimaliahov.repository;

import com.gmail.dimaliahov.model.Role;
import com.gmail.dimaliahov.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName (String name);


}
