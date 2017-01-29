<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ui="https://github.com/bordertech/wcomponents/namespace/ui/v1.0" 
	xmlns:html="http://www.w3.org/1999/xhtml" version="2.0">
	<xsl:import href="wc.ui.root.n.makeRequireConfig.xsl"/>
	<xsl:import href="wc.common.registrationScripts.xsl"/>

	<xsl:template match="ui:root">
		<html>
			<xsl:if test="@lang">
				<xsl:attribute name="lang">
					<xsl:value-of select="@lang"/>
				</xsl:attribute>
			</xsl:if>
			<head>
				<!-- Works more reliably if it is first -->
				<xsl:choose>
					<xsl:when test="ui:application/@icon">
						<xsl:call-template name="faviconHelper">
							<xsl:with-param name="href">
								<xsl:value-of select="ui:application[@icon][1]/@icon"/>
							</xsl:with-param>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="//html:link[@rel eq 'shortcut icon' or @rel eq 'icon']">
						<xsl:apply-templates select="//html:link[contains(@rel, 'icon')][1]"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="faviconHelper">
							<xsl:with-param name="href" select="concat($resourceRoot,'${images.target.dir.name}/favicon.ico')"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
				<!--
					The format-detection is needed to work around issues in some very popular mobile browsers that will convert
					"numbers" into phone links (a elements) if they appear to be phone numbers, even if those numbers are the
					content of buttons or links. This breaks important stuff if you, for example, want to link or submit using
					a number identifier.
					
					If you want a phone number link in these (or any) browser use WPhoneNumberField set readOnly.
				-->
				<xsl:element name="meta">
					<xsl:attribute name="name">
						<xsl:text>format-detection</xsl:text>
					</xsl:attribute>
					<xsl:attribute name="content">
						<xsl:text>telephone=no</xsl:text>
					</xsl:attribute>
				</xsl:element>
				<xsl:element name="meta">
					<xsl:attribute name="name"><xsl:text>viewport</xsl:text></xsl:attribute>
					<xsl:attribute name="content"><xsl:text>initial-scale=1</xsl:text></xsl:attribute>
				</xsl:element>
				<title>
					<xsl:value-of select="@title"/>
				</title>
				<xsl:call-template name="cssLink">
					<xsl:with-param name="filename" select="'${css.target.file.name}'"/>
					<xsl:with-param name="id" select="'wc_css_screen'"/><!-- this id is used by the style loader js -->
				</xsl:call-template>
				
				<xsl:if test="$isDebug = 1">
					<!-- Load debug CSS -->
					<xsl:call-template name="cssLink">
						<xsl:with-param name="filename" select="'${css.target.file.name.debug}'"/>
					</xsl:call-template>
				</xsl:if>
				<xsl:apply-templates select="ui:application/ui:css" mode="inHead"/>
				<xsl:apply-templates select=".//html:link[@rel eq 'stylesheet']" mode="inHead"/>
				<!--
					We need to set up the require config very early.
				-->
				<xsl:call-template name="makeRequireConfig"/>
				<!--
					non-AMD compatible fixes for IE: things that need to be fixed before we can require anything but
					have to be added after we have included requirejs/require.
				-->
				<xsl:call-template name="makeIE8CompatScripts"/>
				<!--
					Load requirejs
				-->
				<script type="text/javascript" src="{concat($resourceRoot, $scriptDir, '/lib/require.js?', $cacheBuster)}"></script>
				<!-- We can delete some script nodes after they have been used. To do this we need the script element to have an ID. -->
				<xsl:variable name="scriptId" select="generate-id()"/>
				<!-- We want to load up the CSS as soon as we can, so do it immediately after loading require. -->
				<xsl:variable name="styleLoaderId" select="concat($scriptId,'-styleloader')"/>
				<script type="text/javascript" id="{$styleLoaderId}">
					<xsl:text>require(["wc/compat/compat!"], function() {</xsl:text>
					<xsl:text>require(["wc/a8n", "wc/loader/style", "wc/dom/removeElement"</xsl:text>
					<xsl:if test="number($isDebug) eq 1">
						<xsl:text>,"wc/debug/common"</xsl:text>
					</xsl:if>
					<xsl:text>], function(a, s, r){try{s.load();}finally{r("</xsl:text>
					<xsl:value-of select="$styleLoaderId"/>
					<xsl:text>", 250);}});</xsl:text>
					<xsl:text>});</xsl:text>
				</script>
				<xsl:call-template name="registrationScripts"/>
				<!--
					We grab all base, meta and link elements from the content and place
					them in the head where they belong.
				-->
				<xsl:apply-templates select="ui:application/ui:js" mode="inHead"/>
				<xsl:apply-templates select=".//html:base|.//html:link[not(contains(@rel,'icon') or @rel eq 'stylesheet')]|.//html:meta" mode="inHead"/>
			</head>
			<body data-wc-domready="false">
				<!--
					loading indicator and shim
					We show a loading indicator as we construct the page then remove it as part of post-initialisation.
					This helps to prevent users interacting with a page before it is ready. The modal shim is part of
					the page level loading indicator.
				-->
				<div id="wc-shim" class="wc_shim_loading">
					<xsl:text>&#xa0;</xsl:text>
					<noscript>
						<p>
							<xsl:text>You must have JavaScript enabled to use this application.</xsl:text>
						</p>
					</noscript>
				</div>
				<div id="wc-ui-loading">
					<div tabindex="0" class="wc-icon">&#x200b;</div>
				</div>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>
	
	<xsl:template name="faviconHelper">
		<xsl:param name="href" select="''"/>
		<xsl:if test="$href ne ''">
			<xsl:element name="link">
				<xsl:attribute name="rel">
					<xsl:text>shortcut icon</xsl:text><!-- Invalid but the only cross browser option -->
				</xsl:attribute>
				<xsl:attribute name="href">
					<xsl:value-of select="$href"/>
				</xsl:attribute>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	
	<!-- 
		IE 8 and below needs a helper to recognise HTML5 elemnts as HTML 
		elements. This needs to happen so very early that we cannot use 
		require to load it. We can use an IE conditional comment to limit
		this code to IE8 and before. We have tested for IE so that we 
		do not event output the conditional comment in other browsers. It still
		needs to be in a conditional comment so that we do not apply to IE > 8.
	-->
	<xsl:template name="makeIE8CompatScripts">
		<xsl:comment>[if lte IE 8] &gt;
&lt;script type="text/javascript"&gt;
(function(){
	var i, el=["details","datalist","aside","dialog","summary","section","header","nav","footer","meter","output","progress","audio","video","source","time","track","figcaption","figure"];
	if (window.SystemJS &amp;&amp; SystemJS.config) SystemJS.config({ "wc/fix/html5Fix_ie8": { elements: el } });
	else if (window.require &amp;&amp; require.config) require.config["wc/fix/html5Fix_ie8"] = { elements: el };
	for (i = 0; i &lt; el.length; i++){ document.createElement(el[i]); } })();
&lt;/script&gt;
&lt;![endif]</xsl:comment>
	</xsl:template>

	<xsl:template name="cssLink">
		<xsl:param name="filename"/>
		<xsl:param name="id" select="''"/>
		<link type="text/css" rel="stylesheet">
			<xsl:if test="$id ne ''">
				<xsl:attribute name="id">
					<xsl:value-of select="$id"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="href">
				<xsl:value-of select="$resourceRoot"/>
				<xsl:text>${css.target.dir.name}/</xsl:text>
				<xsl:value-of select="$filename"/>
				<xsl:if test="$isDebug = 1">
					<xsl:text>${debug.target.file.name.suffix}</xsl:text>
				</xsl:if>
				<xsl:text>.css?</xsl:text>
				<xsl:value-of select="$cacheBuster"/>
			</xsl:attribute>
		</link>
	</xsl:template>
</xsl:stylesheet>
