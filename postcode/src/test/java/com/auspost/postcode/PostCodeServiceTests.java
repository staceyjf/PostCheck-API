package com.auspost.postcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.auspost.postcode.PostCode.CreatePostCodeDTO;
import com.auspost.postcode.PostCode.PostCode;
import com.auspost.postcode.PostCode.PostCodeService;
import com.auspost.postcode.Suburb.AUSTRALIANSTATE;
import com.auspost.postcode.Suburb.Suburb;
import com.auspost.postcode.exceptions.ServiceValidationException;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class PostCodeServiceTests {

    @MockBean
    private PostCodeService postCodeService;

    private CreatePostCodeDTO mockCreateDTO;

    private PostCode testPostCode;

    private Suburb testSuburb;

    @BeforeEach
    public void setupService() throws ServiceValidationException {
        testSuburb = new Suburb();
        testSuburb.setId(1L);
        testSuburb.setName("Sydney");
        testSuburb.setState(AUSTRALIANSTATE.ACT);

        mockCreateDTO = new CreatePostCodeDTO();
        mockCreateDTO.setPostcode("2000");
        mockCreateDTO.setSuburbIds(Collections.singleton(1L)); // 1 long for id

        testPostCode = new PostCode();
        testPostCode.setPostcode("2000");
        testPostCode.setAssociatedSuburbs(Collections.singleton(testSuburb));
    }

    @Test
    void createPostCodeShouldReturnAPostCode() throws Exception {
        // define the expected behaviour with Mockito
        given(postCodeService.createPostCode(mockCreateDTO)).willReturn(testPostCode);

        // call the function
        PostCode testResult = postCodeService.createPostCode(mockCreateDTO);

        // make the assertions with jupiter
        assertEquals(testPostCode, testResult, "The returned PostCode should match the expected one");
        verify(postCodeService).createPostCode(mockCreateDTO);

    }

}
