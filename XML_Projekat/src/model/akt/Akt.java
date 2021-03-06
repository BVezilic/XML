//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.14 at 02:55:57 PM CEST 
//


package model.akt;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/skupstina}MetaPodaci"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/skupstina}Preambula" minOccurs="0"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/skupstina}Naslov"/>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element ref="{http://www.ftn.uns.ac.rs/skupstina}Deo" maxOccurs="unbounded" minOccurs="2"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element ref="{http://www.ftn.uns.ac.rs/skupstina}Clan" maxOccurs="unbounded" minOccurs="2"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element ref="{http://www.ftn.uns.ac.rs/skupstina}Tacka" maxOccurs="unbounded" minOccurs="2"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element ref="{http://www.ftn.uns.ac.rs/skupstina}Glava" maxOccurs="unbounded" minOccurs="2"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metaPodaci",
    "preambula",
    "naslov",
    "deo",
    "clan",
    "tacka",
    "glava"
})
@XmlRootElement(name = "Akt")
public class Akt {

    @XmlElement(name = "MetaPodaci", required = true)
    protected MetaPodaci metaPodaci;
    @XmlElement(name = "Preambula")
    protected String preambula;
    @XmlElement(name = "Naslov", required = true)
    protected String naslov;
    @XmlElement(name = "Deo")
    protected List<Deo> deo;
    @XmlElement(name = "Clan")
    protected List<Clan> clan;
    @XmlElement(name = "Tacka")
    protected List<Tacka> tacka;
    @XmlElement(name = "Glava")
    protected List<Glava> glava;

    /**
     * Gets the value of the metaPodaci property.
     * 
     * @return
     *     possible object is
     *     {@link MetaPodaci }
     *     
     */
    public MetaPodaci getMetaPodaci() {
        return metaPodaci;
    }

    /**
     * Sets the value of the metaPodaci property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaPodaci }
     *     
     */
    public void setMetaPodaci(MetaPodaci value) {
        this.metaPodaci = value;
    }

    /**
     * Gets the value of the preambula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreambula() {
        return preambula;
    }

    /**
     * Sets the value of the preambula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreambula(String value) {
        this.preambula = value;
    }

    /**
     * Gets the value of the naslov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaslov() {
        return naslov;
    }

    /**
     * Sets the value of the naslov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaslov(String value) {
        this.naslov = value;
    }

    /**
     * Gets the value of the deo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deo }
     * 
     * 
     */
    public List<Deo> getDeo() {
        if (deo == null) {
            deo = new ArrayList<Deo>();
        }
        return this.deo;
    }

    /**
     * Gets the value of the clan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Clan }
     * 
     * 
     */
    public List<Clan> getClan() {
        if (clan == null) {
            clan = new ArrayList<Clan>();
        }
        return this.clan;
    }

    /**
     * Gets the value of the tacka property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tacka property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTacka().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tacka }
     * 
     * 
     */
    public List<Tacka> getTacka() {
        if (tacka == null) {
            tacka = new ArrayList<Tacka>();
        }
        return this.tacka;
    }

    /**
     * Gets the value of the glava property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the glava property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGlava().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Glava }
     * 
     * 
     */
    public List<Glava> getGlava() {
        if (glava == null) {
            glava = new ArrayList<Glava>();
        }
        return this.glava;
    }

}
