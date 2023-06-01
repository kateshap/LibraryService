package com.example.libraryservice.libobjects;

import jakarta.xml.bind.annotation.*;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="year" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         <element name="author" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "title",
        "year",
        "author"
})
@XmlRootElement(name = "book")
public class LibXML {

    @XmlElement(required = true)
    protected String title;
    protected Integer year;
    protected String author;

    /**
     * Gets the value of the title property.
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }



    /**
     * Gets the value of the year property.
     *
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the value of the yaer property.
     *
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets the value of the author property.
     *
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     *
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}