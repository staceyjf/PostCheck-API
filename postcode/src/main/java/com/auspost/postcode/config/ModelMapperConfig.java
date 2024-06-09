package com.auspost.postcode.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auspost.postcode.Suburb.AustralianState;
import com.auspost.postcode.Suburb.SuburbDTO;
import com.auspost.postcode.Suburb.Suburb;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.typeMap(String.class, String.class).setConverter(new StringTrimConverter());
        mapper.typeMap(SuburbDTO.class, Suburb.class)
                .addMappings(m -> m.using(new UpperCaseConvertor()).map(SuburbDTO::getName, Suburb::setName))
                .addMappings(
                        m -> m.using(new UpperCaseEnumConvertor()).map(SuburbDTO::getState, Suburb::setState));
        return mapper;
    }

    // remove whitespace
    private class StringTrimConverter implements Converter<String, String> {
        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().trim();
        }
    }

    // ensure that suburb names are recorded consistently
    private class UpperCaseConvertor implements Converter<String, String> {
        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().toUpperCase().trim();
        }
    }

    // converts lowercase state inputs to uppercase to avoid JSON parse error
    private class UpperCaseEnumConvertor implements Converter<String, AustralianState> {
        @Override
        public AustralianState convert(MappingContext<String, AustralianState> context) {
            if (context.getSource() == null) {
                return null;
            }
            // return the ENUM value of the response body state value
            return AustralianState.valueOf(context.getSource().toUpperCase());
        }
    }

}
