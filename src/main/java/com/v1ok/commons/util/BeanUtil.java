package com.v1ok.commons.util;

import com.v1ok.commons.util.support.DateEditor;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.cglib.beans.BeanMap;

public class BeanUtil {

  public static void populate(final Object bean, final Map<String, ? extends Object> properties) {

    Validate.notNull(properties, "The properties params at populate method is not null!");
    Validate.notNull(bean, "The bean params at populate method is not null!");

    BeanMap beanMap = BeanMap.create(bean);
    Set set = beanMap.keySet();

    SimpleTypeConverter simpleTypeConverter = new SimpleTypeConverter();

    simpleTypeConverter.registerCustomEditor(Date.class, new DateEditor());

    for (Object beanProperty : set) {

      String propertyName = beanProperty.toString();
      Class<?> propertyType = beanMap.getPropertyType(propertyName);
      Object propertyValue = properties.get(propertyName);

      Object v = simpleTypeConverter.convertIfNecessary(propertyValue, propertyType);
      beanMap.put(beanProperty, v);

    }

  }

  /**
   * 把MAP对像转换成Bean,Map.Key 与Bean.property 的判断去掉下划线"—_"并忽略大小写判断。
   *
   * @param beanClass 要转成Bean的Class
   */
  public static <T> T mapToBean(Class<T> beanClass, Map<String, ?> map)
      throws IllegalAccessException, InstantiationException {

    Validate.notNull(map, "The map params In mapToBean method is not null!");

    T bean = beanClass.newInstance();

    BeanMap beanMap = BeanMap.create(bean);

    Set set = beanMap.keySet();

    SimpleTypeConverter simpleTypeConverter = new SimpleTypeConverter();

    Set<? extends Entry<String, ?>> entries = map.entrySet();

    for (Entry<String, ?> entry : entries) {
      String mapKey = StringUtils.replaceChars(entry.getKey(), "_", "");
      Object mapValue = entry.getValue();

      if (mapValue == null) {
        continue;
      }

      for (Object beanProperty : set) {

        if (StringUtils.equalsAnyIgnoreCase(beanProperty.toString(), mapKey)) {

          Class<?> propertyType = beanMap.getPropertyType(beanProperty.toString());

          //判断mapValue是否是基本类型。否则应该迭代转型
          if (!TypeUtil.isBaseType(propertyType)) {
            Object v = simpleTypeConverter.convertIfNecessary(mapValue, propertyType);
            beanMap.put(beanProperty, v);
            break;
          }

          Object v = mapToBean(propertyType, (Map<String, Object>) mapValue);
          beanMap.put(beanProperty, v);

          break;
        }

      }
    }

    return bean;
  }

}
