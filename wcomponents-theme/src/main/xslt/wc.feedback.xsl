<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:ui="https://github.com/bordertech/wcomponents/namespace/ui/v1.0" 
	xmlns:html="http://www.w3.org/1999/xhtml"
	version="2.0">

	<xsl:import href="wc.common.attributes.xsl"/>

	<xsl:template match="ui:fieldindicator">
		<span>
			<xsl:if test="@id">
				<xsl:attribute name="id">
					<xsl:value-of select="@id"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:call-template name="makeCommonClass"/>
			<xsl:variable name="iconclass">
				<xsl:choose>
					<xsl:when test="@type = 'warn'">fa-exclamation-triangle</xsl:when>
					<xsl:otherwise>fa-times-circle</xsl:otherwise>
				</xsl:choose>s
			</xsl:variable>
			<i aria-hidden="true" class="fa {$iconclass}"></i>
			<xsl:apply-templates select="ui:message" mode="fieldindicator" />
		</span>
	</xsl:template>

	<!-- WMessageBox -->
	<xsl:template match="ui:messagebox">
		<xsl:variable name="type" select="@type"/>
		<section id="{@id}">
			<xsl:call-template name="makeCommonClass">
				<xsl:with-param name="additional">
					<xsl:text>wc_msgbox</xsl:text>
				</xsl:with-param>
			</xsl:call-template>
			<h1>
				<xsl:variable name="iconclass">
					<xsl:text>fa-fw </xsl:text>
					<xsl:choose>
						<xsl:when test="$type eq 'error'">
							<xsl:text>fa-minus-circle</xsl:text>
						</xsl:when>
						<xsl:when test="$type eq 'warn'">
							<xsl:text>fa-exclamation-triangle</xsl:text>
						</xsl:when>
						<xsl:when test="$type eq 'info'">
							<xsl:text>fa-info-circle</xsl:text>
						</xsl:when>
						<xsl:when test="$type eq 'success'">
							<xsl:text>fa-check-circle</xsl:text>
						</xsl:when>
					</xsl:choose>
				</xsl:variable>
				<i aria-hidden="true" class="fa {$iconclass}"></i>
				<span>
					<xsl:choose>
						<xsl:when test="@title">
							<xsl:value-of select="@title"/>
						</xsl:when>
						<xsl:when test="$type eq 'error'">
							<xsl:text>{{#i18n}}messagetitle_error{{/i18n}}</xsl:text>
						</xsl:when>
						<xsl:when test="$type eq 'warn'">
							<xsl:text>{{#i18n}}messagetitle_warn{{/i18n}}</xsl:text>
						</xsl:when>
						<xsl:when test="$type eq 'info'">
							<xsl:text>{{#i18n}}messagetitle_info{{/i18n}}</xsl:text>
						</xsl:when>
						<xsl:when test="$type eq 'success'">
							<xsl:text>{{#i18n}}messagetitle_success{{/i18n}}</xsl:text>
						</xsl:when>
					</xsl:choose>
				</span>
			</h1>
			<div class="wc_messages">
				<xsl:apply-templates select="ui:message" mode="messagebox" />
			</div>
		</section>
	</xsl:template>

	<!--
		Transform for ui:message.
		
		ui:message is a child of either a WMessageBox, WFieldWarningIndicator or a WFieldErrorIndicator.
	-->
	<xsl:template match="ui:message" />

	<xsl:template match="ui:message" mode="messagebox">
		<div>
			<xsl:call-template name="makeCommonClass"/>
			<xsl:apply-templates />
		</div>
	</xsl:template>

	<xsl:template match="ui:message" mode="fieldindicator">
		<span>
			<xsl:call-template name="makeCommonClass"/>
			<xsl:apply-templates />
		</span>
	</xsl:template>

	<!-- WValidationErrors. -->
	<xsl:template match="ui:validationerrors|ui:error"/>

</xsl:stylesheet>
