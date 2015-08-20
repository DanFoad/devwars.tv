package com.bezman.jackson;

import com.bezman.Reference.Reference;
import com.bezman.jackson.serializer.UserPermissionSerializer;
import com.bezman.model.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Bean for the Jackson Object Mapper
 */
public class DevWarsObjectMapper extends ObjectMapper
{

    public DevWarsObjectMapper()
    {
        super();

        setSerializationInclusion(JsonInclude.Include.NON_NULL);

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Reference.objectMapper = this;
    }

}
