package com.sagar.joltdemo.util;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagar.joltdemo.entity.Employee;

import java.util.List;

public class JoltUtil {
    public static <T> T getEntity(String spec, Class<T> clazz, Object inputJson) {
        List chainrSpecJSON = JsonUtils.classpathToList(spec);
        Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
        Object transformedOutput = chainr.transform(inputJson);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(transformedOutput, clazz);
    }
}
