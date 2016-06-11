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
		<fo:block font-family="Georgia" 
			font-weight="bold" 
			font-size="18pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="text()"/></fo:block>
	</xsl:template>
	
	<xsl:template match="sk:Deo">
		<fo:block font-family="Georgia" 
			font-weight="bold" 
			font-size="16pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">DEO <xsl:value-of select="@Redna_oznaka"/></fo:block>
		<fo:block font-family="Georgia" 
			font-weight="bold" 
			font-size="16pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Glava">
		<fo:block font-family="Georgia" 
			font-weight="bold" 
			font-size="14pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Redna_oznaka"/>&#160;<xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Odeljak">
		<fo:block font-family="Georgia" 
			font-weight="bold" 
			font-size="13pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Redna_oznaka"/>&#160;<xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Pododeljak">
		<fo:block font-family="Georgia" 
			font-weight="bold" 
			font-size="13pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Redna_oznaka"/>&#160;<xsl:value-of select="@Naziv"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Clan">
		<fo:block font-family="Georgia" 
			font-size="14pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Naziv"/></fo:block>
		<fo:block font-family="Georgia" 
			font-size="14pt" 
			text-align="center"
			space-before="12pt"
			space-after="12pt">Član <xsl:value-of select="@Redna_oznaka"/></fo:block>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Stav">
		<fo:block font-family="Georgia" 
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="text()"/></fo:block>
		<xsl:apply-templates select="sk:Tacka"/> 
	</xsl:template>
	
	<xsl:template match="sk:Tacka">
		<fo:block font-family="Georgia" 
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Redna_oznaka"/>&#160;&#160;<xsl:value-of select="text()"/></fo:block>
		<xsl:apply-templates select="sk:Podtacka"/>
	</xsl:template>
	
	<xsl:template match="sk:Podtacka">
		<fo:block font-family="Georgia" 
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Redna_oznaka"/>&#160;&#160;<xsl:value-of select="text()"/></fo:block>
		<xsl:apply-templates select="sk:Alineja"/> 
	</xsl:template>
	
	<xsl:template match="sk:Alineja">
		<fo:block font-family="Georgia" 
			font-size="12pt" 
			text-align="justify"
			text-indent="1.5cm"
			space-before="12pt"
			space-after="12pt"><xsl:value-of select="@Redna_oznaka"/>&#160;&#160;<xsl:value-of select="text()"/></fo:block>
	</xsl:template>
	
</xsl:stylesheet>