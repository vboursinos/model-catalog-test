package ai.turintech.modelcatalog.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NullAwareBeanUtils extends BeanUtils {

    private static Logger log = Logger.getLogger(NullAwareBeanUtils.class.getName());
    public static void copyNonNullProperties(Object dest, Object orig) {

        PropertyUtilsBean propertyUtils = new PropertyUtilsBean();
        PropertyDescriptor[] origDescriptors = propertyUtils.getPropertyDescriptors(orig);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if(!propertyUtils.isWriteable(dest, name)) {
                continue;
            }
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            try {
                Object value = propertyUtils.getSimpleProperty(orig, name);
                if (value != null) {
                    copyProperty(dest, name, value);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}