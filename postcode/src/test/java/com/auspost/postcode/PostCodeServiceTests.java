package com.auspost.postcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auspost.postcode.PostCode.CreatePostCodeDTO;
import com.auspost.postcode.PostCode.PostCode;
import com.auspost.postcode.PostCode.PostCodeService;
import com.auspost.postcode.PostCode.UpdatePostCodeDTO;
import com.auspost.postcode.Suburb.AUSTRALIANSTATE;
import com.auspost.postcode.Suburb.Suburb;
import com.auspost.postcode.exceptions.ServiceValidationException;

// focus of test is generated results
// unit test
@ExtendWith(MockitoExtension.class)
public class PostCodeServiceTests {

    @Mock
    private PostCodeService postCodeService;

    private PostCode mockPostCode;
    private PostCode mockPostCode2;
    private Suburb testSuburb;
    private List<PostCode> mockpostCodes;

    // No need to roll back transactions because all database operations are mocked.
    @BeforeEach
    public void setupService() throws ServiceValidationException {
        testSuburb = new Suburb();
        testSuburb.setId(1L);
        testSuburb.setName("Sydney");
        testSuburb.setState(AUSTRALIANSTATE.ACT);

        mockPostCode = new PostCode();
        mockPostCode.setPostcode("2000");
        mockPostCode.setAssociatedSuburbs(Collections.singleton(testSuburb));

        mockPostCode2 = new PostCode();
        mockPostCode2.setPostcode("2001");
        mockPostCode2.setAssociatedSuburbs(Collections.singleton(testSuburb));

        mockpostCodes = new ArrayList<PostCode>();
        mockpostCodes.add(mockPostCode);
        mockpostCodes.add(mockPostCode2);
    }

    @Test
    void createPostCodeShouldReturnAPostCode() throws Exception {
        // mock up the needed variables
        CreatePostCodeDTO mockCreateDTO = new CreatePostCodeDTO();
        mockCreateDTO.setPostcode("2000");
        mockCreateDTO.setSuburbIds(Collections.singleton(1L)); // 1 long for id

        // define the expected behaviour with Mockito
        given(postCodeService.createPostCode(mockCreateDTO)).willReturn(mockPostCode);

        // call the function
        PostCode testResult = postCodeService.createPostCode(mockCreateDTO);

        // confirm the tester postcode matches the mock with JUnit 5
        assertEquals(mockPostCode, testResult, "The returned PostCode should match the expected one");

        // use Mockito's verify() to ensure that the method was called with the right
        // param
        verify(postCodeService).createPostCode(mockCreateDTO);
    }

    @Test
    void findAllPostCodesShouldReturnAllPostCode() throws Exception {

        given(postCodeService.findAllPostCodes()).willReturn(mockpostCodes);

        List<PostCode> testResult = postCodeService.findAllPostCodes();

        assertEquals(
                mockpostCodes, testResult,
                "The returned List of postcodes should match the expected one");

        verify(postCodeService).findAllPostCodes();
    }

    @Test
    void findByIdshouldReturnRelevantPostCode() throws Exception {
        given(postCodeService.findById(1L)).willReturn(Optional.of(mockPostCode));

        Optional<PostCode> testResult = postCodeService.findById(1L);

        assertEquals(Optional.of(mockPostCode), testResult,
                "The returned optional PostCode should match the expected one");

        verify(postCodeService).findById(1L);
    }

    @Test
    void updateByIdShouldReturntheUpdatedPostCode() throws Exception {
        UpdatePostCodeDTO mockUpdateDTO = new UpdatePostCodeDTO();
        mockUpdateDTO.setPostcode("2001");

        PostCode mockUpdatedPostCode = new PostCode();
        mockUpdatedPostCode.setPostcode("2001");
        mockUpdatedPostCode.setAssociatedSuburbs(Collections.singleton(testSuburb));

        given(postCodeService.updateById(1L, mockUpdateDTO)).willReturn(Optional.of(mockUpdatedPostCode));

        Optional<PostCode> testResult = postCodeService.updateById(1L, mockUpdateDTO);

        assertEquals(Optional.of(
                mockUpdatedPostCode), testResult,
                "The returned optional update PostCode should match the expected one");

        verify(postCodeService).updateById(1L, mockUpdateDTO);
    }

    @Test
    void findSuburbByPostCodeShouldReturnAllPostCode() throws Exception {
        List<Suburb> testSuburbs = new ArrayList<>();
        testSuburbs.add(testSuburb);

        given(postCodeService.findSuburbByPostCode("2001")).willReturn(testSuburbs);

        List<Suburb> testResult = postCodeService.findSuburbByPostCode("2001");

        assertEquals(
                testSuburbs, testResult,
                "The returned List of suburbs should match the expected one");

        verify(postCodeService).findSuburbByPostCode("2001");
    }

    @Test
    void findPostCodesBySuburbShouldReturnAllPostCode() throws Exception {
        given(postCodeService.findPostCodesBySuburb("sydney")).willReturn(mockpostCodes);

        List<PostCode> testResult = postCodeService.findPostCodesBySuburb("sydney");

        assertEquals(
                mockpostCodes, testResult,
                "The returned List of postcodes should match the expected one");

        verify(postCodeService).findPostCodesBySuburb("sydney");
    }

}
