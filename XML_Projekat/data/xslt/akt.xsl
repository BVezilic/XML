<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:sk="http://www.ftn.uns.ac.rs/skupstina">

	<xsl:template match="/">
		<html>
			<head>
				<title><xsl:value-of select="sk:Akt/sk:Naslov"/></title>
				<style type="text/css">
					h1, h2, h3 {
						text-align: center;
					}
					p, li {
  					    text-indent: 50px;
					}
					.noBullet {
						list-style-type: none;
					}
				</style>
			</head>
			<body>
				<xsl:apply-templates/> 
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="sk:Naslov">
		<h1><xsl:value-of select="text()"/></h1>
	</xsl:template>
	
	<xsl:template match="sk:Deo">
		<h2>ДЕО <xsl:value-of select="@Redni_broj"/></h2>
		<h2><xsl:value-of select="@Naziv"/></h2>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Glava">
		<h3>Глава <xsl:value-of select="@Redni_broj"/></h3>
		<h2><xsl:value-of select="@Naziv"/></h2>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Odeljak">
		<h3><xsl:value-of select="@Naziv"/></h3>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Pododeljak">
		<h4><xsl:value-of select="@Naziv"/></h4>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Clan">
		<h3><xsl:value-of select="@Naziv"/></h3>
		<h3>Члан <xsl:value-of select="@Brojcana_oznaka"/>.<br/></h3>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Stav">
		<p><xsl:value-of select="text()"/></p>
		<xsl:apply-templates select="sk:Tacka"/> 
	</xsl:template>
	
	<xsl:template match="sk:Tacka">
		<li class="noBullet"><xsl:value-of select="text()"/></li>
		<xsl:apply-templates select="sk:Podtacka"/>
	</xsl:template>
	
	<xsl:template match="sk:Podtacka">
		<li class="noBullet"><xsl:value-of select="text()"/></li>
		<xsl:apply-templates select="sk:Alineja"/> 
	</xsl:template>
	
	<xsl:template match="sk:Alineja">
		<li class="noBullet"><xsl:value-of select="text()"/></li>
	</xsl:template>
	
</xsl:stylesheet>