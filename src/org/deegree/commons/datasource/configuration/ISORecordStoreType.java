//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.20 at 02:51:50 PM MEZ 
//


package org.deegree.commons.datasource.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ISORecordStoreType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ISORecordStoreType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.deegree.org/datasource}RecordStoreType">
 *       &lt;attribute name="connId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISORecordStoreType")
public class ISORecordStoreType
    extends RecordStoreType
{

    @XmlAttribute(required = true)
    protected String connId;

    /**
     * Gets the value of the connId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnId() {
        return connId;
    }

    /**
     * Sets the value of the connId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnId(String value) {
        this.connId = value;
    }

}
