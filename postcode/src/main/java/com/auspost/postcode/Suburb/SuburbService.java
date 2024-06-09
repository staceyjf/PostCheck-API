package com.auspost.postcode.Suburb;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auspost.postcode.PostCode.PostCodeService;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class SuburbService {
    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @Autowired
    private SuburbRepository repo;

    @Autowired
    private PostCodeService postCodeService;

    public Suburb createSuburb(@Valid SuburbDTO data) throws ServiceValidationException {
        Suburb newSuburb = new Suburb();
        ValidationErrors errors = new ValidationErrors();

        String trimmedNameField = data.getName().toLowerCase().trim();
        if (trimmedNameField.isBlank()) {
            errors.addError("Suburb", "Suburb field must contain a value.");
        }

        AustralianState state = AustralianState.from(data.getState());

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

        fullLogsLogger.info("Created new Suburb in db:" + newSuburb);

        return this.repo.save(newSuburb);
    }

    public List<Suburb> findAllSuburbs() {
        return this.repo.findAll();
    }

    public Optional<Suburb> findById(Long id) {
        return this.repo.findById(id);
    }

    public Optional<Suburb> updateById(Long id, @Valid SuburbDTO data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateById'");
    }

}
