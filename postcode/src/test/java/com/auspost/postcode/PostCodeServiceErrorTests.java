package com.auspost.postcode;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auspost.postcode.PostCode.CreatePostCodeDTO;
import com.auspost.postcode.PostCode.PostCodeService;
import com.auspost.postcode.PostCode.UpdatePostCodeDTO;

import com.auspost.postcode.exceptions.ServiceValidationException;
import com.auspost.postcode.exceptions.ValidationErrors;

@ExtendWith(MockitoExtension.class)
public class PostCodeServiceErrorTests {

    @Mock
    private PostCodeService postCodeService;

    private ValidationErrors errors;

    @Test
    void createPostCodeShouldErrorWhenDTOIsBlank() throws ServiceValidationException {
        CreatePostCodeDTO mockCreateDTO = new CreatePostCodeDTO();

        errors = new ValidationErrors();
        errors.addError("PostCode", "Postcode field must contain a value.");

        given(postCodeService.createPostCode(mockCreateDTO)).willThrow(new ServiceValidationException(errors));

        assertThrows(ServiceValidationException.class, () -> postCodeService.createPostCode(mockCreateDTO));

    }

    @Test
    void createPostCodeShouldErrorWhenPostCodeFieldIsNotNumerical() throws ServiceValidationException {
        CreatePostCodeDTO mockCreateDTO = new CreatePostCodeDTO();
        mockCreateDTO.setPostcode("abcd");

        errors = new ValidationErrors();
        errors.addError("PostCode", "Postcode field should only contain numbers and be 4 numbers in length.");

        given(postCodeService.createPostCode(mockCreateDTO)).willThrow(new ServiceValidationException(errors));

        assertThrows(ServiceValidationException.class, () -> postCodeService.createPostCode(mockCreateDTO));
    }

    @Test
    void createPostCodeShouldErrorWhenPostCodeFieldIsLessThan4Numbers() throws ServiceValidationException {
        CreatePostCodeDTO mockCreateDTO = new CreatePostCodeDTO();
        mockCreateDTO.setPostcode("20");

        errors = new ValidationErrors();
        errors.addError("PostCode", "Postcode field should only contain numbers and be 4 numbers in length.");

        given(postCodeService.createPostCode(mockCreateDTO)).willThrow(new ServiceValidationException(errors));

        assertThrows(ServiceValidationException.class, () -> postCodeService.createPostCode(mockCreateDTO));
    }

    @Test
    void createPostCodeShouldErrorWhenAnAssociatedSuburbCannotBeFound() throws ServiceValidationException {
        CreatePostCodeDTO mockCreateDTO = new CreatePostCodeDTO();
        mockCreateDTO.setPostcode("2001");
        mockCreateDTO.setSuburbIds(Collections.singleton(1L));

        errors = new ValidationErrors();
        errors.addError("Suburb", "Suburb with id 1 does not exist");

        given(postCodeService.createPostCode(mockCreateDTO)).willThrow(new ServiceValidationException(errors));

        assertThrows(ServiceValidationException.class, () -> postCodeService.createPostCode(mockCreateDTO));
    }

    @Test
    void updateByIdShouldErrorWhenDTOIsEmpty() throws ServiceValidationException {
        UpdatePostCodeDTO mockUpdateDTO = new UpdatePostCodeDTO();

        errors = new ValidationErrors();
        errors.addError("PostCode", "PostCode with id 7 does not exist");

        given(postCodeService.updateById(7L, mockUpdateDTO)).willThrow(new ServiceValidationException(errors));

        assertThrows(ServiceValidationException.class, () -> postCodeService.updateById(7L, mockUpdateDTO));
    }
}
