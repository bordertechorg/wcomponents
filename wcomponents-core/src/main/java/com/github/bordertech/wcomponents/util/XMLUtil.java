package com.github.bordertech.wcomponents.util;

import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.WebUtilities;

/**
 * Utility class for rendering file element.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class XMLUtil {

	/**
	 * Theme URI.
	 */
	public static final String THEME_URI = "https://github.com/bordertech/wcomponents/namespace/ui/v1.0";

	/**
	 * XHtml URI.
	 */
	public static final String XHTML_URI = "http://www.w3.org/1999/xhtml";

	/**
	 * XML Declaration.
	 */
	public static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	/**
	 * UI Namespace attribute.
	 */
	public static final String UI_NAMESPACE = " xmlns:ui=\"" + THEME_URI + "\"";

	/**
	 * Standard Namespace attributes.
	 */
	public static final String STANDARD_NAMESPACES = " xmlns=\"" + XHTML_URI + "\" xmlns:html=\"" + XHTML_URI + "\""
			+ UI_NAMESPACE;

	/**
	 * XML Declaration.
	 * @deprecated 1.4.0 never used internally. Must never be used!
	 */
	@Deprecated
	public static final String XML_DECLERATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	/**
	 * Prevent instantiation of utility class.
	 */
	private XMLUtil() {
		// Do nothing
	}

	/**
	 * @param uic the current user context
	 * @return the xml declaration with the theme processing instruction
	 */
	public static String getXMLDeclarationWithThemeXslt(final UIContext uic) {
		String theme = WebUtilities.encode(ThemeUtil.getThemeXslt(uic));
		return XML_DECLARATION + "\n<?xml-stylesheet type=\"text/xsl\" href=\"" + theme + "\"?>";
	}
}
