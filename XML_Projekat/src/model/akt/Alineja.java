//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.14 at 02:55:57 PM CEST 
//


package model.akt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="Redni_broj" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="Redna_oznaka" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "Alineja")
public class Alineja {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "Redni_broj")
    protected Integer redniBroj;
    @XmlAttribute(name = "Redna_oznaka", required = true)
    protected String rednaOznaka;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the redniBroj property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRedniBroj() {
        return redniBroj;
    }

    /**
     * Sets the value of the redniBroj property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRedniBroj(Integer value) {
        this.redniBroj = value;
    }

    /**
     * Gets the value of the rednaOznaka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRednaOznaka() {
        return rednaOznaka;
    }

    /**
     * Sets the value of the rednaOznaka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRednaOznaka(String value) {
        this.rednaOznaka = value;
    }

}
