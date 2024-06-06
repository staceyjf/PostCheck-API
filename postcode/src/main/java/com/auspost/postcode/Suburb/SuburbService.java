package com.auspost.postcode.Suburb;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Suburb createSuburb(@Valid CreateSuburbDTO data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSuburb'");
    }

    public List<Suburb> findAllSuburbs() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllSuburbs'");
    }

    public Optional<Suburb> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
