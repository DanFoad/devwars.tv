package com.bezman.jackson.serializer;


import com.bezman.Reference.Reference;
import com.bezman.annotation.UserPermissionFilter;
import com.bezman.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Terence on 8/2/2015.
 */
public class UserPermissionSerializer extends JsonSerializer<Object> implements ContextualSerializer
{

    String userFieldName;

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
    {
        ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(o.getClass().getDeclaredFields()));

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        jsonGenerator.writeStartObject();

        boolean hasSecretKey = (boolean) request.getAttribute("hasSecretKey");
        User currentUser = (User) request.getAttribute("user");

        fields.forEach(field -> {
            try
            {
                JsonIgnore jsonIgnore = field.getAnnotation(JsonIgnore.class);
                UserPermissionFilter userPermissionFilter = field.getAnnotation(UserPermissionFilter.class);

                field.setAccessible(true);

                if(userPermissionFilter == null)
                {
                    if(jsonIgnore == null && field.get(o) != null)
                        jsonGenerator.writeObjectField(field.getName(), field.get(o));
                } else
                {
                    String fieldName = userPermissionFilter.userField();
                    User user;

                    if (!fieldName.isEmpty())
                    {
                        Field userField = o.getClass().getDeclaredField(fieldName);
                        userField.setAccessible(true);

                        user = (User) userField.get(o);
                    } else user = (User) o;

                    if (jsonIgnore == null && field.get(o) != null && currentUser != null)
                    {
                        if (hasSecretKey || currentUser.getId() == user.getId() || User.Role.valueOf(currentUser.getRole()) == User.Role.ADMIN || userPermissionFilter == null)
                        {
                            jsonGenerator.writeObjectField(field.getName(), field.get(o));
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        jsonGenerator.writeEndObject();
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException
    {
        return this;
    }
}