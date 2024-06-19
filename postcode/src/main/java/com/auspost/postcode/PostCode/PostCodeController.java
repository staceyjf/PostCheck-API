package com.auspost.postcode.PostCode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.auspost.postcode.exceptions.ServiceValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Tag(name = "PostCode Management", description = "Endpoints for managing postcodes")
@RestController
@RequestMapping("/api/v1/postcodes")
public class PostCodeController {
    @Autowired
    private PostCodeService postcodeService;

    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @Operation(summary = "Create a new PostCode", description = "Create a new PostCode and return the PostCode details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PostCode created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<PostCode> createPostCode(@Valid @RequestBody CreatePostCodeDTO data)
            throws ServiceValidationException {
        PostCode createdPostCode = this.postcodeService.createPostCode(data);
        fullLogsLogger.info("createPostCode responses responded with new PostCode: " + createdPostCode);
        return new ResponseEntity<>(createdPostCode, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all PostCodes", description = "Return a list of all PostCodes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PostCode>> findAllPostCodes() {
        List<PostCode> allPostCodes = this.postcodeService.findAllPostCodes();
        fullLogsLogger.info("findAllPostCodes responses with all postcodes.");
        return new ResponseEntity<>(allPostCodes, HttpStatus.OK);
    }

    @Operation(summary = "Get a PostCode by ID", description = "Return the details of the PostCode with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "PostCode not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostCode> findPostCodesById(@PathVariable Long id) {
        Optional<PostCode> maybePost = this.postcodeService.findById(id);
        PostCode foundPostCode = maybePost
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PostCode not found"));
        fullLogsLogger.info("findPostCodesById responses with the found postcode:" + foundPostCode);
        return new ResponseEntity<>(foundPostCode, HttpStatus.OK);
    }

    @Operation(summary = "Update a PostCode by ID", description = "Update the PostCode with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "PostCode not found"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PostCode> updatePostCodeById(@PathVariable Long id,
            @Valid @RequestBody UpdatePostCodeDTO data)
            throws ServiceValidationException {
        Optional<PostCode> maybePostCode = this.postcodeService.updateById(id, data);
        PostCode updatedPostCode = maybePostCode.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PostCode with id: " + id + " not found"));
        fullLogsLogger.info("updatePostCodeById responses with updated postcode:" + updatedPostCode);
        return new ResponseEntity<>(updatedPostCode, HttpStatus.OK);
    }

    @Operation(summary = "Delete a PostCode by ID", description = "Delete the PostCode with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "PostCode deleted"),
            @ApiResponse(responseCode = "404", description = "PostCode not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostCodeById(@PathVariable Long id)
            throws ServiceValidationException {
        boolean isDeleted = this.postcodeService.deleteById(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PostCode with id: " + id + " not found");
        }
        fullLogsLogger.info(String.format("PostCode with id: %d has been deleted ", id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Find suburbs by PostCode", description = "Return a list of suburbs associated with the given PostCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Suburbs not found"),
    })
    @GetMapping("/suburbs")
    public ResponseEntity<?> findSuburbsByPostCode(@RequestParam String postcode) {
        List<PostCode> suburbsByPostCode = this.postcodeService.findSuburbsByPostCode(postcode);
        if (suburbsByPostCode.isEmpty()) {
            return new ResponseEntity<>("No suburbs found for postcode: " + postcode, HttpStatus.NOT_FOUND);
        }
        fullLogsLogger.info("suburbsByPostCode responses with all associated suburbs.");
        return new ResponseEntity<>(suburbsByPostCode, HttpStatus.OK);
    }

    @Operation(summary = "Find PostCodes by suburb", description = "Return a list of PostCodes associated with the given suburb")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "PostCodes not found"),
    })
    @GetMapping("/postcodes")
    public ResponseEntity<?> findPostCodesBySuburb(@RequestParam String suburb) {
        List<PostCode> postCodesBySuburb = this.postcodeService.findPostCodesBySuburb(suburb);
        if (postCodesBySuburb.isEmpty()) {
            return new ResponseEntity<>("No postcodes found for suburb: " + suburb, HttpStatus.NOT_FOUND);
        }
        fullLogsLogger.info("postCodesBySuburb responses with all associated postCodes.");
        return new ResponseEntity<>(postCodesBySuburb, HttpStatus.OK);
    }

}
