package com.minis.core;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import org.dom4j.Element;

/**
 * @author lethe
 * @date 2023/5/29 21:51
 */
public class XmlBeanDefinitionReader {

    BeanFactory beanFactory;
    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element)resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
