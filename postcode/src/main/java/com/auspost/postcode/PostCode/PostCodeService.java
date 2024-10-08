package com.auspost.postcode.PostCode;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.auspost.postcode.Suburb.Suburb;
import com.auspost.postcode.Suburb.SuburbService;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Service
@Transactional
public class PostCodeService {
    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @Autowired
    private PostCodeRepository repo;

    @Autowired
    private SuburbService suburbService;

    public PostCode createPostCode(@Valid CreatePostCodeDTO data) throws ServiceValidationException {
        PostCode newPostCode = new PostCode();
        ValidationErrors errors = new ValidationErrors();

        String trimmedPostCodeField = data.getPostcode().trim();
        if (trimmedPostCodeField.isBlank()) {
            errors.addError("PostCode", "Postcode field must contain a value.");
        }

        if (!trimmedPostCodeField.matches("[\\d]{4}")) {
            errors.addError("PostCode", "Postcode field should only contain numbers and be 4 numbers in length.");
        }

        Set<Suburb> associatedSuburbs = new HashSet<>();
        fullLogsLogger.info("Suburb ids are" + data.getSuburbIds());
        for (Long id : data.getSuburbIds()) {
            fullLogsLogger.info("Processing suburbId: " + id);

            Optional<Suburb> maybeSuburb = this.suburbService.findById(id);

            if (maybeSuburb.isEmpty()) {
                errors.addError("Suburb", String.format("Suburb with id %s does not exist", id));
            } else {
                associatedSuburbs.add(maybeSuburb.get());
            }
        }
        // attempt all validation before throwing an error
        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }

        // update with DTO data post validation & data cleaning
        newPostCode.setPostcode(trimmedPostCodeField);
        newPostCode.setAssociatedSuburbs(associatedSuburbs);

        // as the unique constraint is at the bd level
        try {
            PostCode savedPostCode = this.repo.save(newPostCode);
            fullLogsLogger.info("Created new PostCode in db with ID: " + savedPostCode.getId());
            return savedPostCode;
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                errors.addError("PostCode", "Postcode already exists.");
            } else {
                errors.addError("PostCode", "There was an issue saving a new postcode to the database");
            }
            throw new ServiceValidationException(errors);
        }

    }

    // will return an empty array which negate error handling needs
    public List<PostCode> findAllPostCodes() {
        List<PostCode> postCodes = this.repo.findAll();
        Collections.sort(postCodes);
        fullLogsLogger.info("Sourced all postcodes from the db. Total count: " + postCodes.size());
        return postCodes;
    }

    public Optional<PostCode> findById(Long id) {
        Optional<PostCode> foundPostCode = this.repo.findById(id);
        fullLogsLogger.info("Located PostCode in db with ID: " + foundPostCode.get().getId());
        return foundPostCode;
    }

    public Optional<PostCode> updateById(Long id, @Valid UpdatePostCodeDTO data) throws ServiceValidationException {
        Optional<PostCode> maybePostCode = this.findById(id);
        ValidationErrors errors = new ValidationErrors();

        if (maybePostCode.isEmpty()) {
            errors.addError("PostCode", String.format("PostCode with id %s does not exist", data.getId()));
        }

        PostCode foundPostCode = maybePostCode.get();

        // check to see if postcode field has been provided
        String trimmedPostCodeField = data.getPostcode() != null ? data.getPostcode().trim() : null;

        if (trimmedPostCodeField != null) {
            if (trimmedPostCodeField.isEmpty()) {
                errors.addError("PostCode", "Postcode field needs to have a value.");
            }

            if (trimmedPostCodeField.length() != 4) {
                errors.addError("PostCode", "Postcode field must have length of 4 characters.");
            }

            if (!trimmedPostCodeField.matches("[\\d]{4}")) {
                errors.addError("PostCode", "Postcode field should only contain numbers.");
            }
        }

        Set<Suburb> associatedSuburbs = new HashSet<>();

        if (data.getSuburbIds() != null) {
            fullLogsLogger.info("Suburb ids are" + data.getSuburbIds());
            for (Long suburbId : data.getSuburbIds()) {
                fullLogsLogger.info("Processing suburbId: " + suburbId);
                Optional<Suburb> maybeSuburb = this.suburbService.findById(suburbId);

                if (maybeSuburb.isEmpty()) {
                    errors.addError("Suburb", String.format("Suburb with id %s does not exist", suburbId));
                } else {
                    associatedSuburbs.add(maybeSuburb.get());
                }
            }
        }

        // attempt all validation before throwing an error
        if (errors.hasErrors()) {
            throw new ServiceValidationException(errors);
        }

        // update with DTO fields after validation
        if (trimmedPostCodeField != null) {
            foundPostCode.setPostcode(trimmedPostCodeField);
        }
        if (!associatedSuburbs.isEmpty()) {
            foundPostCode.setAssociatedSuburbs(associatedSuburbs);
        }

        PostCode updatedPostCode = this.repo.save(foundPostCode);

        fullLogsLogger.info("Update PostCode in db with ID: " + updatedPostCode.getId());
        return Optional.of(updatedPostCode);
    }

    public List<PostCode> findSuburbsByPostCode(String postCode) {
        PostCode probe = new PostCode();
        probe.setPostcode(postCode);

        // Examplematcher allows for more granular matching
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("postcode", match -> match.contains()); // This will match any postcode containing the
                                                                     // specified sequence

        Example<PostCode> example = Example.of(probe, matcher);

        // to use Example<PostCode> we need to use the more flexible findAll instead of
        // the specific findbypostcode
        List<PostCode> associatedSuburbs = this.repo.findAll(example, Sort.by(Sort.Order.asc("postcode")));
        fullLogsLogger.info("Sourced all associatedSuburbs from the db. Total count: " + associatedSuburbs.size());
        return associatedSuburbs;
    }

    public List<PostCode> findPostCodesBySuburb(String suburb) {
        List<PostCode> associatedPostCodes = this.repo.findPostCodesByAssociatedSuburbsNameContainingIgnoreCase(suburb);
        fullLogsLogger.info("Sourced all associatedPostCodes from the db. Total count: " + associatedPostCodes.size());
        return associatedPostCodes;
    }

    public boolean deleteById(Long id) throws ServiceValidationException {
        Optional<PostCode> maybePostCode = this.findById(id);
        if (maybePostCode.isEmpty()) {
            return false;
        }

        PostCode foundPostCode = maybePostCode.get();

        foundPostCode.associatedSuburbs.clear();

        // TODO: add a function to remove Orphaned sububs

        this.repo.delete(foundPostCode);
        return true;
    }
}
