package com.minis.core;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import com.minis.beans.SimpleBeanFactory;
import com.minis.inject.ArgumentValues;
import com.minis.inject.PropertyValue;
import com.minis.inject.PropertyValues;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lethe
 * @date 2023/5/29 21:51
 */
public class XmlBeanDefinitionReader {

    SimpleBeanFactory simpleBeanFactory;
    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element)resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            //处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if(pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                } else if(pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(pName, pType, pV,isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            //处理构造器参数
            List<Element> constructorArgElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for(Element e:constructorArgElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                AVS.addGenericArgumentValue(aValue, aType);
            }
            beanDefinition.setConstructorArgumentValues(AVS);

            this.simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
