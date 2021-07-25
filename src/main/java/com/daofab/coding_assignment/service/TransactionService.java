package com.daofab.coding_assignment.service;

import com.daofab.coding_assignment.dto.ChildDTO;
import com.daofab.coding_assignment.dto.ParentDTO;
import com.daofab.coding_assignment.model.Child;
import com.daofab.coding_assignment.model.Parent;
import com.daofab.coding_assignment.repo.ChildRepo;
import com.daofab.coding_assignment.repo.ParentRepo;
import com.daofab.coding_assignment.util.SampleChild;
import com.daofab.coding_assignment.util.SampleParent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class containing core business logic
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final ParentRepo parentRepo;
    private final ChildRepo childRepo;

    @Value("${json.file-path.parent}")
    public String parentFilePath;

    @Value("${json.file-path.child}")
    public String childFilePath;

    /**
     * load data from json file and populate into database
     */
    public String loadJsonIntoDatabase() {

        ObjectMapper objectMapper = new ObjectMapper();
        Optional<String> parentResponseOptional = loadParentJsonIntoDatabase(objectMapper);
        if (parentResponseOptional.isPresent()){
            return parentResponseOptional.get();
        }

        Optional<String> childResponseOptional = loadChildJsonIntoDatabase(objectMapper);
        return childResponseOptional.orElse(Strings.EMPTY);
    }

    /**
     * Load Parent data from json file and populate into the database.
     * Also Ensures that No data exist in the database tables (Parent, Child) before populating
     * @param objectMapper Class used to read json value into Pojo
     * @return error message if there is any error else it is empty
     */
    private Optional<String> loadParentJsonIntoDatabase(ObjectMapper objectMapper){

        File parentFile;
        try {
            parentFile = ResourceUtils.getFile(parentFilePath);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            log.error("Parent Json File Not Found", e);
            return Optional.of("Unable to location Parent json file in the provided file path : "+parentFilePath);
        }

        log.info("ParentFile => {}", parentFile.getAbsolutePath());
        if (parentFile.exists()) {
            log.info("Parent Resource full path is {}", parentFile.getAbsolutePath());
            TypeReference<SampleParent> sampleParentTypeReference = new TypeReference<SampleParent>() {};
            try {
                SampleParent sampleParent = objectMapper.readValue(parentFile, sampleParentTypeReference);
                if (Objects.nonNull(sampleParent.getData()) && !sampleParent.getData().isEmpty()){
                    childRepo.deleteAll();
                    parentRepo.deleteAll();
                    parentRepo.saveAll(sampleParent.getData());
                }
            } catch (IOException e) {
                log.error("Unable to process parent file as json", e);
                return Optional.of("Unable to read parent file content as Json ");
            }
        }

        return Optional.empty();
    }

    /**
     * Load Child data from json file and populate into the database.
     * Also Ensures that No data exist in the database tables (Parent, Child) before populating
     * @param objectMapper Class used to read json value into Pojo
     * @return error message if there is any error else it is empty
     */
    private Optional<String> loadChildJsonIntoDatabase(ObjectMapper objectMapper){

        File childFile;
        try {
            childFile = ResourceUtils.getFile(childFilePath);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            log.error("Child Json File Not Found", e);
            return Optional.of("Unable to location child json file in the provided file path : "+childFilePath);
        }

        if (childFile.exists()) {
            log.info("Child Resource full path is {}", childFile.getAbsolutePath());
            TypeReference<SampleChild> sampleChildTypeReference = new TypeReference<SampleChild>() {};
            try {
                SampleChild sampleChild = objectMapper.readValue(childFile, sampleChildTypeReference);
                if (Objects.nonNull(sampleChild.getData()) && !sampleChild.getData().isEmpty()){
                    List<Child> childList = sampleChild.getData().stream().map(jsonChild -> new Child(jsonChild.getId(), new Parent(jsonChild.getParentId()), jsonChild.getPaidAmount())).collect(Collectors.toList());
                    childRepo.saveAll(childList);
                }
            } catch (IOException e) {
                log.error("Unable to process child file as Json ", e);
                return Optional.of("Unable to read child file content as Json ");
            }
        }

        return Optional.empty();
    }

    /**
     * Fetch all parent transaction but paginated based on the provided page number
     * @param pageNumber provided page number
     * @return paginated set of parent transactions
     */
    public Page<ParentDTO> getAllParent(int pageNumber){

        try {
            return parentRepo.findAllParent(PageRequest.of(pageNumber, 2));
        } catch (Exception e) {
            log.error("Unable to fetch parent from the database ", e);
        }

        return new PageImpl<>(Collections.emptyList());
    }

    /**
     * Fetch all Child transaction based on the provided parent's id
     * @param parentId provided parent's id
     * @return all child transactions based on the parent's id
     */
    public List<ChildDTO> getAllChildByParentId(Long parentId){

        try {
            return childRepo.findAllByParentId(parentId);
        } catch (Exception e) {
            log.error("Unable to fetch parent from the database ", e);
        }

        return Collections.emptyList();
    }

    //write unit tests for service
    //write unit tests for repo
    //write unit tests for controller

    //write a readme file on how to setup
    //add swagger
    //document your swagger page.

}
