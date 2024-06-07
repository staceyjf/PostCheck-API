package com.auspost.postcode.PostCode;

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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postcodes")
public class PostCodeController {
    @Autowired
    private PostCodeService postcodeService;

    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @PostMapping()
    public ResponseEntity<PostCode> createPostCode(@Valid @RequestBody PostCodeDTO data)
            throws ServiceValidationException {
        PostCode createdPostCode = this.postcodeService.createPostCode(data);
        fullLogsLogger.info("createPostCode Controller responded with new PostCode: " + createdPostCode);
        return new ResponseEntity<>(createdPostCode, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<PostCode>> findAllPostCodes() {
        List<PostCode> allPostCodes = this.postcodeService.findAllPostCodes();
        fullLogsLogger.info("findAllPostCodes Controller with all postcodes.");
        return new ResponseEntity<>(allPostCodes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCode> findPostCodesById(@PathVariable Long id) {
        Optional<PostCode> maybePost = this.postcodeService.findById(id);
        PostCode foundPostCode = maybePost.orElseThrow();
        fullLogsLogger.info("findPostCodesById Controller with the found postcode:" + foundPostCode);
        return new ResponseEntity<>(foundPostCode, HttpStatus.OK);
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<PostCode> updatePostCodeById(@PathVariable Long id,
    // @Valid @RequestBody UpdateTodoDTO data)
    // throws ServiceValidationException {
    // return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    // }

    // TODO: find postcode by suburb name

}
