package com.api.springrest.vo.mapper;

import com.api.springrest.exceptions.RequiredObjectIsNullException;
import com.github.dozermapper.core.*;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination){
        if (origin == null) {
            throw new RequiredObjectIsNullException("Cannot map a null object");
        }
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObject(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin){
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
