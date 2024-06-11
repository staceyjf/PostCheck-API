package com.auspost.postcode.Suburb;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class SuburbService {
    @Autowired
    private SuburbRepository repo;

    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    public Suburb createSuburb(@Valid CreateSuburbDTO data) throws ServiceValidationException {
        Suburb newSuburb = new Suburb();
        ValidationErrors errors = new ValidationErrors();

        String trimmedNameField = data.getName().toLowerCase().trim();
        if (trimmedNameField.isBlank()) {
            errors.addError("Suburb", "Suburb field must contain a value.");
        }

        AUSTRALIANSTATE state = AUSTRALIANSTATE.from(data.getState());

        // from method returns null if there is no match
        if (state == null) {
            errors.addError("State",
                    "A state match could not be found. Please consult the documentation for accepted values for Australian states.");
        }

        if (errors.hasErrors()) {
            throw new ServiceValidationException((errors));
        }

        // update with DTO data post validation & data cleaning
        newSuburb.setName(trimmedNameField);
        newSuburb.setState(state);
        // newSuburb.setAssociatedPostcodes(associatedPostCodes);

        Suburb savedSuburb = this.repo.save(newSuburb);
        fullLogsLogger.info("Created new Suburb in db:" + newSuburb);
        return savedSuburb;
    }

    public List<Suburb> findAllSuburbs() {
        return this.repo.findAll();
    }

    public Optional<Suburb> findById(Long id) {
        Optional<Suburb> foundSuburb = this.repo.findById(id);
        fullLogsLogger.info("Located Suburb in db with ID: " + foundSuburb.get().getId());
        return foundSuburb;
    }

    public Optional<Suburb> updateById(Long id, @Valid UpdateSuburbDTO data) throws ServiceValidationException {
        Optional<Suburb> maybeSuburb = this.findById(id);
        ValidationErrors errors = new ValidationErrors();

        if (maybeSuburb.isEmpty()) {
            errors.addError(("Suburb"), String.format("Suburb with id %s does not exist", data.getId()));
        }

        Suburb foundSuburb = maybeSuburb.get();

        String trimmedNameField = data.getName() != null ? data.getName().toLowerCase().trim() : null;

        if (trimmedNameField != null) {
            if (trimmedNameField.isBlank()) {
                errors.addError("Suburb", "Suburb field must contain a value.");
            }
        }

        AUSTRALIANSTATE state = null;

        if (data.getState() != null) {
            state = AUSTRALIANSTATE.from(data.getState());

            if (state == null) {
                errors.addError("State",
                        "A state match could not be found. Please consult the documentation for accepted values for Australian states.");
            }
        }

        if (errors.hasErrors()) {
            throw new ServiceValidationException((errors));
        }

        // update with DTO data post validation & data cleaning
        if (trimmedNameField != null) {
            foundSuburb.setName(trimmedNameField);
        }

        if (state != null) {
            foundSuburb.setState(state);
        }

        Suburb updatedSuburb = this.repo.save(foundSuburb);
        fullLogsLogger.info("Created new Suburb in db:" + foundSuburb);
        return Optional.of(updatedSuburb);
    }

}
