package com.auspost.postcode.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.typeMap(String.class, String.class).setConverter(new StringTrimConverter());
        // mapper.typeMap(CreateColourDTO.class, Colour.class)
        // .addMappings(m -> m.using(new
        // LowerCaseConverter()).map(CreateColourDTO::getName, Colour::setName));
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

    // private class LowerCaseConverter implements Converter<String, String> {

    // @Override
    // public String convert(MappingContext<String, String> context) {
    // if (context.getSource() == null) {
    // return null;
    // }
    // return context.getSource().toLowerCase().trim();
    // }
    // }

}
