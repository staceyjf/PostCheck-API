package com.auspost.postcode;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auspost.postcode.Suburb.CreateSuburbDTO;
import com.auspost.postcode.Suburb.SuburbService;
import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

@ExtendWith(MockitoExtension.class)
public class SuburbServiceErrorTests {

    @Mock
    private SuburbService suburbService;

    private ValidationErrors errors;

    @Test
    void createPostCodeShouldErrorWhenDTOIsBlank() throws ServiceValidationException {
        CreateSuburbDTO mockCreateDTO = new CreateSuburbDTO();
        mockCreateDTO.setState("Nswsss");

        errors = new ValidationErrors();
        errors.addError("State",
                "A state match could not be found. Please consult the documentation for accepted values for Australian states.");

        given(suburbService.createSuburb(mockCreateDTO)).willThrow(new ServiceValidationException(errors));

        assertThrows(ServiceValidationException.class, () -> suburbService.createSuburb(mockCreateDTO));

    }

}
