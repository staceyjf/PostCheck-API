package com.auspost.postcode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auspost.postcode.Suburb.AUSTRALIANSTATE;
import com.auspost.postcode.Suburb.CreateSuburbDTO;
import com.auspost.postcode.Suburb.Suburb;
import com.auspost.postcode.Suburb.SuburbService;
import com.auspost.postcode.Suburb.UpdateSuburbDTO;
import com.auspost.postcode.exceptions.ServiceValidationException;

@ExtendWith(MockitoExtension.class)
public class SuburbServiceTests {

    @Mock
    private SuburbService suburbService;

    private Suburb mockSuburb;
    private Suburb mockSuburb2;
    private List<Suburb> mockSuburbs;

    @BeforeEach
    public void setupService() throws ServiceValidationException {
        mockSuburb = new Suburb();
        mockSuburb.setName("Sydney");
        mockSuburb.setState(AUSTRALIANSTATE.NSW);

        mockSuburb2 = new Suburb();
        mockSuburb2.setName("Sydney");
        mockSuburb2.setState(AUSTRALIANSTATE.QLD);

        mockSuburbs = new ArrayList<Suburb>();
        mockSuburbs.add(mockSuburb);
        mockSuburbs.add(mockSuburb2);

    }

    @Test
    void createSuburbShouldReturnASuburb() throws ServiceValidationException {
        // mock up the needed variables
        CreateSuburbDTO mockCreateDTO = new CreateSuburbDTO();
        mockCreateDTO.setName("Sydney");
        mockCreateDTO.setState("NSW");

        // define the expected behaviour with Mockito
        given(suburbService.createSuburb(mockCreateDTO)).willReturn(mockSuburb);

        // call the function
        Suburb testResult = suburbService.createSuburb(mockCreateDTO);

        // confirm the tester Suburb matches the mock with JUnit 5
        assertEquals(mockSuburb, testResult, "The returned Suburb should match the expected one");

        // use Mockito's verify() to ensure that the method was called with the right
        // param
        verify(suburbService).createSuburb(mockCreateDTO);
    }

    @Test
    void createSuburbShouldAcceptDifferentStateVariations() throws ServiceValidationException {
        // mock up the needed variables
        CreateSuburbDTO mockCreateDTO = new CreateSuburbDTO();
        mockCreateDTO.setName("Sydney");
        mockCreateDTO.setState("new south wales");

        // define the expected behaviour with Mockito
        given(suburbService.createSuburb(mockCreateDTO)).willReturn(mockSuburb);

        // call the function
        Suburb testResult = suburbService.createSuburb(mockCreateDTO);

        // confirm the tester Suburb matches the mock with JUnit 5
        assertEquals(mockSuburb, testResult,
                "The input state could convert full name of the input state to match an Australian State ENUM");

        // use Mockito's verify() to ensure that the method was called with the right
        // param
        verify(suburbService).createSuburb(mockCreateDTO);
    }

    @Test
    void createSuburbShouldAcceptLowerCaseStateVariation() throws ServiceValidationException {
        // mock up the needed variables
        CreateSuburbDTO mockCreateDTO = new CreateSuburbDTO();
        mockCreateDTO.setName("Sydney");
        mockCreateDTO.setState("nsw");

        // define the expected behaviour with Mockito
        given(suburbService.createSuburb(mockCreateDTO)).willReturn(mockSuburb);

        // call the function
        Suburb testResult = suburbService.createSuburb(mockCreateDTO);

        // confirm the tester Suburb matches the mock with JUnit 5
        assertEquals(mockSuburb, testResult,
                "The input state could not lowercase the input state to match an Australian State ENUM");

        // use Mockito's verify() to ensure that the method was called with the right
        // param
        verify(suburbService).createSuburb(mockCreateDTO);
    }

    @Test
    void findAllSuburbsShouldReturnAllSuburb() throws ServiceValidationException {

        given(suburbService.findAllSuburbs()).willReturn(mockSuburbs);

        List<Suburb> testResult = suburbService.findAllSuburbs();

        assertEquals(
                mockSuburbs, testResult,
                "The returned List of suburbs should match the expected one");

        verify(suburbService).findAllSuburbs();
    }

    @Test
    void findByIdshouldReturnRelevantSuburb() throws ServiceValidationException {
        given(suburbService.findById(1L)).willReturn(Optional.of(mockSuburb));

        Optional<Suburb> testResult = suburbService.findById(1L);

        assertEquals(Optional.of(mockSuburb), testResult,
                "The returned optional Suburb should match the expected one");

        verify(suburbService).findById(1L);
    }

    @Test
    void updateByIdShouldReturntheUpdatedSuburb() throws ServiceValidationException {
        UpdateSuburbDTO mockUpdateDTO = new UpdateSuburbDTO();
        mockUpdateDTO.setState("QLD");

        Suburb mockUpdatedSuburb = new Suburb();
        mockUpdatedSuburb.setState(AUSTRALIANSTATE.QLD);

        given(suburbService.updateById(1L, mockUpdateDTO)).willReturn(Optional.of(mockUpdatedSuburb));

        Optional<Suburb> testResult = suburbService.updateById(1L, mockUpdateDTO);

        assertEquals(Optional.of(
                mockUpdatedSuburb), testResult,
                "The returned optional update Suburb should match the expected one");

        verify(suburbService).updateById(1L, mockUpdateDTO);
    }

}
