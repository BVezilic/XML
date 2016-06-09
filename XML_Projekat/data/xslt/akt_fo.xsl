<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:sk="http://www.ftn.uns.ac.rs/skupstina"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
            	<fo:simple-page-master master-name="simple">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="simple">
                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates/> 
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
	</xsl:template>
	
	<xsl:template match="sk:Naslov">
		<fo:block font-family="Georgia"><xsl:value-of select="text()"/></fo:block>
	</xsl:template>
	
	<xsl:template match="sk:Deo">
		<fo:block font-family="Georgia">ДЕО <xsl:value-of select="@Redni_broj"/></fo:block>
		<fo:block><xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Glava">
		<fo:block>Глава <xsl:value-of select="@Redni_broj"/></fo:block>
		<fo:block><xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Odeljak">
		<fo:block><xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Pododeljak">
		<fo:block><xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Clan">
		<fo:block><xsl:value-of select="@Naziv"/></fo:block>
		<fo:block>Члан <xsl:value-of select="@Brojcana_oznaka"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Stav">
		<fo:block><xsl:value-of select="text()"/></fo:block>
		<xsl:apply-templates select="sk:Tacka"/> 
	</xsl:template>
	
	<xsl:template match="sk:Tacka">
		<fo:block><xsl:value-of select="text()"/></fo:block>
		<xsl:apply-templates select="sk:Podtacka"/>
	</xsl:template>
	
	<xsl:template match="sk:Podtacka">
		<fo:block><xsl:value-of select="text()"/></fo:block>
		<xsl:apply-templates select="sk:Alineja"/> 
	</xsl:template>
	
	<xsl:template match="sk:Alineja">
		<fo:block><xsl:value-of select="text()"/></fo:block>
	</xsl:template>
	
</xsl:stylesheet>