package com.daofab.coding_assignment.repo;

import com.daofab.coding_assignment.dto.ParentDTO;
import com.daofab.coding_assignment.model.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface used to access the database tables then save or fetch data
 */
public interface ParentRepo extends JpaRepository<Parent, Long> {

    @Query("select new com.daofab.coding_assignment.dto.ParentDTO(p.id, p.sender, p.receiver, p.totalAmount, sum(c.paidAmount)) from Parent p join p.child c group by p.id ")
    Page<ParentDTO> findAllParent(Pageable pageable);

}
