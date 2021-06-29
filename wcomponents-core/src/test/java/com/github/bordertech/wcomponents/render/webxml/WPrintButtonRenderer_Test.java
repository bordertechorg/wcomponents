package com.github.bordertech.wcomponents.render.webxml;

import com.github.bordertech.wcomponents.WPrintButton;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Junit test case for {@link WPrintButtonRenderer}.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
public class WPrintButtonRenderer_Test extends AbstractWebXmlRendererTestCase {

	@Test
	public void testRendererCorrectlyConfigured() {
		WPrintButton component = new WPrintButton("dummy");
		Assert.assertTrue("Incorrect renderer supplied",
				getWebXmlRenderer(component) instanceof WPrintButtonRenderer);
	}

	@Test
	public void testDoPaint() throws IOException, SAXException {
		WPrintButton button = new WPrintButton("Print");
		assertXpathExists("//html:button[contains(@class, 'wc-printbutton')]", button);
	}

	@Test
	public void testXssEscaping() throws IOException, SAXException {
		WPrintButton button = new WPrintButton(getMaliciousContent());

		assertSafeContent(button);

		button.setToolTip(getMaliciousAttribute("html:button"));
		assertSafeContent(button);

		button.setAccessibleText(getMaliciousAttribute("html:button"));
		assertSafeContent(button);

		button.setImageUrl(getMaliciousAttribute());
		assertSafeContent(button);
	}
}
