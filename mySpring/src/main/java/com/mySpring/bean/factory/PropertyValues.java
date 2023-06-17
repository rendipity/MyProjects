package com.mySpring.bean.factory;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private  final List<PropertyValue> propertyValueList =new ArrayList<>();

    public void addProperty(PropertyValue propertyValue){
        for (PropertyValue property:propertyValueList) {
            if (property.getName().equals(propertyValue.getName())){
                property.setValue(propertyValue.getValue());
                return;
            }
        }
        this.propertyValueList.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }
}
