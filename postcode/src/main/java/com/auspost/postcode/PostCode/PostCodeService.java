package com.auspost.postcode.PostCode;

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
public class PostCodeService {
    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");
    private static final Logger errorLogger = LogManager.getLogger("error");

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostCodeRepository repo;

    // add in subrub service

    public PostCode createdPostCode(@Valid CreatePostCodeDTO data) throws ServiceValidationException {
        PostCode newPostCode = mapper.map(data, PostCode.class);
        // Long colourId = data.getColourId();
        // Optional<Colour> maybeColour = this.colourService.findById(colourId);
        ValidationErrors errors = new ValidationErrors();

        // if (maybeColour.isEmpty()) {
        // errors.addError("colour", String.format("Colour with id %s does not exist",
        // colourId));
        // } else {
        // newPostCode.setColour(maybeColour.get());
        // }

        if (errors.hasErrors()) {
            errorLogger.error("Creation of new postcode failed: " + errors);
            throw new ServiceValidationException(errors);
        }

        fullLogsLogger.info("Created new PostCode in db: " + newPostCode);

        return this.repo.save(newPostCode);
    }

    // will return an empty array which negate error handling needs
    public List<PostCode> findAllPostCodes() {
        return this.repo.findAll();
    }

    public Optional<PostCode> findById(Long id) {
        return this.repo.findById(id);
    }
}
