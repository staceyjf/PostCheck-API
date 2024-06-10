package com.auspost.postcode.PostCode;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auspost.postcode.Suburb.Suburb;
import com.auspost.postcode.Suburb.SuburbService;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

import jakarta.transaction.Transactional;
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

        PostCode savedPostCode = this.repo.save(newPostCode);
        fullLogsLogger.info("Created new PostCode in db with ID: " + savedPostCode.getId());
        return savedPostCode;
    }

    // will return an empty array which negate error handling needs
    public List<PostCode> findAllPostCodes() {
        List<PostCode> postCodes = this.repo.findAll();
        fullLogsLogger.info("Sourced all postcodes from the db");
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

    public List<Suburb> findSuburbByPostCode(String postCode) {
        List<Suburb> associatedSuburbs = this.repo.findAssociatedSuburbsByPostcode(postCode);
        fullLogsLogger.info("Sourced all associatedSuburbs from the db");
        return associatedSuburbs;
    }
}
