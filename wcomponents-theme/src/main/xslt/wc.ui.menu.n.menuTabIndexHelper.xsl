<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ui="https://github.com/bordertech/wcomponents/namespace/ui/v1.0" 
	xmlns:html="http://www.w3.org/1999/xhtml" version="2.0">
	<xsl:import href="wc.ui.menu.n.hasStickyOpen.xsl"/>
	<!--
		The tabIndex is set on the first visible, enabled item in a menu.
		
		NOTE: 2013-07-23 Changed to match http://www.w3.org/TR/wai-aria-practices/#menu which stipulates that for menu/menu bar the first item is 
		focusable.
		
		We have retained the non-focusability of disabled items since graphical user agents do not allow disabled controls to receive focus.
	-->
	<xsl:template name="menuTabIndexHelper">
		<xsl:param name="menu"/>
		<xsl:variable name="stickyOpen">
			<xsl:call-template name="hasStickyOpen">
				<xsl:with-param name="type" select="$menu/@type"/>
			</xsl:call-template>
		</xsl:variable>
		<!-- 
			If you feel like collapsing this choose then go righht ahead. Personally I think it would be unwise as the test would be very long.
		-->
		<xsl:choose>
			<!-- 
				If we do not have a menu ancestor we are in an submenu content ajax response. We do not have to worry about setting the tabIndex 
				because the JavaScript ajax subscriber will do it for us.
			-->
			<xsl:when test="not($menu)">
				<!-- leave this one by itself: it filters out all calls where $menu is not defined -->
				<xsl:text>-1</xsl:text>
			</xsl:when>
			<!-- if I am disabled I cannot have tabIndex but only need explicity -1 if I am a menuItem with no @URL or @submit -->
			<xsl:when test="@disabled">
				<xsl:if test="self::ui:menuitem and not(@url or @submit)">
					<xsl:text>-1</xsl:text>
				</xsl:if>
			</xsl:when>
			<!-- 
				If I have a closed or disabled submenu ancestor (or open if sticky is 0) which is a descendant of my root menu then I cannot have 
				tabindex.
			-->
			<xsl:when test="ancestor::ui:submenu[1][ancestor::ui:menu[1] eq $menu and (@disabled or number($stickyOpen) eq 0 or not(@open))]">
				<xsl:text>-1</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
