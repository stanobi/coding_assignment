package com.daofab.coding_assignment.repo;

import com.daofab.coding_assignment.dto.ChildDTO;
import com.daofab.coding_assignment.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface used to access the database tables then save or fetch data
 */
public interface ChildRepo extends JpaRepository<Child, Long> {

    @Query("select new com.daofab.coding_assignment.dto.ChildDTO(c.id, p.sender, p.receiver, p.totalAmount, c.paidAmount) from Child c join c.parent p where p.id = ?1")
    List<ChildDTO> findAllByParentId(Long parentId);

}
