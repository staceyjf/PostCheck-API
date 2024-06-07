package com.auspost.postcode.Suburb;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class SuburbService {
    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");
    private static final Logger errorLogger = LogManager.getLogger("error");

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SuburbRepository repo;

    public Suburb createSuburb(@Valid CreateSuburbDTO data) throws ServiceValidationException {
        Suburb newSuburb = mapper.map(data, Suburb.class);
        ValidationErrors errors = new ValidationErrors();

        if (errors.hasErrors()) {
            throw new ServiceValidationException((errors));
        }

        fullLogsLogger.info("Created new Suburb in db:" + newSuburb);

        return this.repo.save(newSuburb);
    }

    public List<Suburb> findAllSuburbs() {
        return this.repo.findAll();
    }

    public Optional<Suburb> findById(Long id) {
        return this.repo.findById(id);
    }
}
