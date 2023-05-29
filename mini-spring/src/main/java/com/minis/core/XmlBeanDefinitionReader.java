package com.minis.core;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import com.minis.beans.SimpleBeanFactory;
import org.dom4j.Element;

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
            simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
