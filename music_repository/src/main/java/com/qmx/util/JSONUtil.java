package com.qmx.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qmx.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zcl on 2018/8/19.
 */
public class JSONUtil {
    static Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public JSONUtil() {
    }

    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException var3) {
            throw new BusinessException("Erorr happen when parsing object to json");
        }
    }

    public static Map toMap(String json) {
        return (Map)toObject(json, Map.class);
    }

    public static Map toMap(Object object) {
        String json = toJson(object);
        return toMap(json);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        if(StringUtils.isEmpty(json)) {
            logger.debug("Convent json to object, json: {}, class: {}, return: {}", new Object[]{json, clazz, null});
            return null;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            Object returnObject = null;

            try {
                returnObject = mapper.readValue(json, clazz);
            } catch (IOException var5) {
                throw new BusinessException("Error happen when convent json to object, json: " + json + ", class: " + clazz, var5);
            }

            logger.debug("Convent json to object, json: {}, class: {}, return: {}", new Object[]{json, clazz, json});
            return (T)returnObject;
        }
    }

    public static <T> T toObject(Map map, Class<T> clazz) {
        if(map == null) {
            return null;
        } else {
            String json = toJson(map);
            logger.debug("Convent map to jso, map: {}, json {}", map, json);
            return toObject(json, clazz);
        }
    }

    public static boolean isNullOrPrimaryType(Object object) {
        return object == null || object instanceof String || object instanceof Number || object instanceof Boolean || object instanceof Character;
    }

    public static Map<String, Object> getBeanFlatMap(Object object) {
        if(object == null) {
            return null;
        } else {
            Map<String, Object> prefixedMap = getMapWithKeyPrefix("", toMap(object));
            Map<String, Object> returnMap = new HashMap();
            if(returnMap != null) {
                Iterator var3 = prefixedMap.entrySet().iterator();

                while(var3.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                    returnMap.put(((String)entry.getKey()).replaceFirst("\\.", ""), entry.getValue());
                }
            }

            return returnMap;
        }
    }

    public static String getParamsString(Object object) {
        if(object == null) {
            return null;
        } else {
            Map<String, Object> beanFlatMap = getBeanFlatMap(object);
            String paramsString = "";
            if(beanFlatMap != null) {
                Iterator var3 = beanFlatMap.entrySet().iterator();

                while(var3.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                    if(entry.getValue() != null) {
                        paramsString = paramsString + (String)entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString()) + "&";
                    }
                }
            }

            return paramsString;
        }
    }

    private static Map<String, Object> getMapWithKeyPrefix(String prefix, Object object) {
        Map<String, Object> returnMap = new HashMap();
        if(isNullOrPrimaryType(object)) {
            returnMap.put(prefix, object);
        } else {
            Object object2;
            if(object instanceof Map) {
                Map<String, Object> map = (Map)object;
                if(map != null) {
                    Iterator var4 = map.entrySet().iterator();

                    while(var4.hasNext()) {
                        Map.Entry<String, Object> entry = (Map.Entry)var4.next();
                        object2 = entry.getValue();
                        String key = (String)entry.getKey();
                        Map<String, Object> innerMap = getMapWithKeyPrefix(prefix + "." + key, object2);
                        returnMap.putAll(innerMap);
                    }
                }
            } else if(object instanceof Collection) {
                Collection collection = (Collection)object;
                int i = 0;

                for(Iterator var11 = collection.iterator(); var11.hasNext(); ++i) {
                    object2 = var11.next();
                    Map<String, Object> innerMap = getMapWithKeyPrefix(prefix + "[" + i + "]", object2);
                    returnMap.putAll(innerMap);
                }
            }
        }

        return returnMap;
    }

    public static <T> T toObject(String jsonString, TypeReference<T> typeReference) {
        if(jsonString == null) {
            return null;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            Object returnObject = null;

            try {
                returnObject = mapper.readValue(jsonString, typeReference);
                return (T) returnObject;
            } catch (IOException var5) {
                throw new BusinessException("Error happen when convent json to object, json: " + jsonString + ", class: " + typeReference, var5);
            }
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class... elementClasses) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T> T toCollectionObject(String jsonString, Class<?> collectionClass, Class<?> elementClasses) {
        JavaType javaType = getCollectionType(collectionClass, new Class[]{elementClasses});
        Object returnObject = null;

        try {
            returnObject = (new ObjectMapper()).readValue(jsonString, javaType);
            return (T) returnObject;
        } catch (IOException var6) {
            throw new BusinessException("Error happen when convent json to object, json: " + jsonString + ", collectionClass: " + collectionClass + ", class: " + elementClasses, var6);
        }
    }
}
