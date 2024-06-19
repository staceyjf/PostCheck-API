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
import org.springframework.web.server.ResponseStatusException;

import com.auspost.postcode.exceptions.ServiceValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Suburb Management", description = "Endpoints for managing suburbs")
@RestController
@RequestMapping("/api/v1/suburbs")
public class SuburbController {
    @Autowired
    private SuburbService suburbService;

    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @Operation(summary = "Create a new Suburb", description = "Create a new Suburb and return the Suburb details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Suburb created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping()
    public ResponseEntity<Suburb> createSuburb(@Valid @RequestBody CreateSuburbDTO data)
            throws ServiceValidationException {
        Suburb createdSuburb = this.suburbService.createSuburb(data);
        fullLogsLogger.info("createSuburb Controller responded with new Suburb: " + createdSuburb);
        return new ResponseEntity<>(createdSuburb, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Suburbs", description = "Return a list of all Suburbs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping()
    public ResponseEntity<List<Suburb>> findAllSuburbs() {
        List<Suburb> allSuburbs = this.suburbService.findAllSuburbs();
        fullLogsLogger.info("findAllSuburbs Controller with all Suburbs.");
        return new ResponseEntity<>(allSuburbs, HttpStatus.OK);
    }

    @Operation(summary = "Get a Suburb by ID", description = "Return the details of the Suburb with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Suburb not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Suburb> findSuburbsById(@PathVariable Long id) {
        Optional<Suburb> maybeSuburb = this.suburbService.findById(id);
        Suburb foundSuburb = maybeSuburb
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Suburb not found"));
        fullLogsLogger.info("findSuburbsById Controller with the found Suburb:" + foundSuburb);
        return new ResponseEntity<>(foundSuburb, HttpStatus.OK);
    }

    @Operation(summary = "Update a Suburb by ID", description = "Update the Suburb with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Suburb not found"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Suburb> updateSuburbById(@PathVariable Long id,
            @Valid @RequestBody UpdateSuburbDTO data)
            throws ServiceValidationException {
        Optional<Suburb> maybeSuburb = this.suburbService.updateById(id, data);
        Suburb updatedSuburb = maybeSuburb.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Suburb with id: " + id + " not found"));
        fullLogsLogger.info("updateSuburbById responses with updated suburb:" + updatedSuburb);
        return new ResponseEntity<>(updatedSuburb, HttpStatus.OK);
    }

    // TODO: delete a suburb
}
