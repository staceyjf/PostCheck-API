package com.auspost.postcode.Suburb;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auspost.postcode.exceptions.ServiceValidationException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/suburbs")
public class SuburbController {
    @Autowired
    private SuburbService suburbService;

    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @PostMapping()
    public ResponseEntity<Suburb> createSuburb(@Valid @RequestBody CreateSuburbDTO data)
            throws ServiceValidationException {
        Suburb createdSuburb = this.suburbService.createSuburb(data);
        fullLogsLogger.info("createSuburb Controller responded with new Suburb: " + createdSuburb);
        return new ResponseEntity<>(createdSuburb, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Suburb>> findAllSuburbs() {
        List<Suburb> allSuburbs = this.suburbService.findAllSuburbs();
        fullLogsLogger.info("findAllSuburbs Controller with all Suburbs.");
        return new ResponseEntity<>(allSuburbs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suburb> findSuburbsById(@PathVariable Long id) {
        Optional<Suburb> maybeSuburb = this.suburbService.findById(id);
        Suburb foundSuburb = maybeSuburb.orElseThrow();
        fullLogsLogger.info("findSuburbsById Controller with the found Suburb:" + foundSuburb);
        return new ResponseEntity<>(foundSuburb, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Suburb> updateSuburbById(@PathVariable Long id,
            @Valid @RequestBody UpdateSuburbDTO data)
            throws ServiceValidationException {
        Optional<Suburb> maybeSuburb = this.suburbService.updateById(id, data);
        Suburb updatedSuburb = maybeSuburb.orElseThrow();
        fullLogsLogger.info("updateSuburbById responses with updated suburb:" + updatedSuburb);
        return new ResponseEntity<>(updatedSuburb, HttpStatus.OK);
    }
}
