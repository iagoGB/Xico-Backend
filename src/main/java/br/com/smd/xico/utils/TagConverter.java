package br.com.smd.xico.utils;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TagConverter implements AttributeConverter<Tag, String> {
 
    @Override
    public String convertToDatabaseColumn(Tag tag) {
        if (tag == null) {
            return null;
        }
        return tag.getCode();
    }

    @Override
    public Tag convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Tag.values()).filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}