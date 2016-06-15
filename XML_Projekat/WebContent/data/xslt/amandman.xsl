<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:ak="http://www.ftn.uns.ac.rs/amandman"
	xmlns:sk="http://www.ftn.uns.ac.rs/skupstina">
	<xsl:template match="/">
		<html>
			<head>
				<title>Amandman</title>
				<style type="text/css">
					h1, h2, h3, h4 {
						text-align: center;
					}
					p, li {
  					    text-align: center;
					}
					.noBullet {
						list-style-type: none;
					}
				</style>
			</head>
			<body>
				<h1>Amandman</h1>
				<xsl:apply-templates/> 
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="ak:Amandman">
		<h2><xsl:value-of select="@Naziv"/></h2>
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="ak:Kontekst">
		<p><b>Referentni zakon:</b></p>
		<p><xsl:value-of select="@Referentni_zakon"/></p>
		<p><b>Deo zakona:</b></p>
		<p><xsl:value-of select="text()"/></p>
	</xsl:template>
	
	<xsl:template match="ak:Operacija">
		<p><b>Operacija:</b></p>
		<p><xsl:value-of select="text()"/></p>
		<p><b>Sadrzaj:</b></p>
	</xsl:template>
	
	<xsl:template match="sk:Clan">
		<h3><xsl:value-of select="@Naziv"/></h3>
		<h3>Član <xsl:value-of select="@Redna_oznaka"/></h3>
		<xsl:apply-templates/> 
	</xsl:template>
	
	<xsl:template match="sk:Stav">
		<p><xsl:value-of select="text()"/></p>
		<xsl:apply-templates select="sk:Tacka"/> 
	</xsl:template>
	
	<xsl:template match="sk:Tacka">
		<li class="noBullet"><xsl:value-of select="@Redna_oznaka"/>&#160;&#160;<xsl:value-of select="text()"/></li><br/>
		<xsl:apply-templates select="sk:Podtacka"/>
	</xsl:template>
	
	<xsl:template match="sk:Podtacka">
		<li class="noBullet"><xsl:value-of select="@Redna_oznaka"/>&#160;&#160;<xsl:value-of select="text()"/></li><br/>
		<xsl:apply-templates select="sk:Alineja"/> 
	</xsl:template>
	
	<xsl:template match="sk:Alineja">
		<li class="noBullet"><xsl:value-of select="@Redna_oznaka"/>&#160;&#160;<xsl:value-of select="text()"/></li><br/>
	</xsl:template>
	
</xsl:stylesheet>