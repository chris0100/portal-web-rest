package com.portalweb.repositories;


import com.portalweb.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

    public Role findById(String id);

}
