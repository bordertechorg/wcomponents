package com.github.bordertech.wcomponents.render.webxml;

import com.github.bordertech.wcomponents.DialogOpenTrigger;
import com.github.bordertech.wcomponents.Renderer;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.XmlStringBuilder;
import com.github.bordertech.wcomponents.servlet.WebXmlRenderContext;

/**
 * The {@link Renderer} for {@link WDialog}.
 *
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */
final class WDialogRenderer extends AbstractWebXmlRenderer {
	public static final String TAG_NAME = "wc-dialog";

	/**
	 * Paints the given WDialog.
	 *
	 * @param component the WDialog to paint.
	 * @param renderContext the RenderContext to paint to.
	 */
	@Override
	public void doRender(final WComponent component, final WebXmlRenderContext renderContext) {
		WDialog dialog = (WDialog) component;
		int state = dialog.getState();

		if (state == WDialog.ACTIVE_STATE || dialog.getTrigger() != null) {
			int width = dialog.getWidth();
			int height = dialog.getHeight();
			String title = dialog.getTitle();
			XmlStringBuilder xml = renderContext.getWriter();

			xml.appendTagOpen(TAG_NAME);
			xml.appendAttribute("data-id", component.getId());
			xml.appendOptionalAttribute("class", component.getHtmlClass());
			xml.appendOptionalAttribute("track", component.isTracking(), "true");
			xml.appendOptionalAttribute("width", width > 0, width);
			xml.appendOptionalAttribute("height", height > 0, height);
			xml.appendOptionalAttribute("modal", dialog.getMode() == WDialog.MODAL, "true");
			xml.appendOptionalAttribute("open", dialog.getState() == WDialog.ACTIVE_STATE, "true");
			xml.appendOptionalAttribute("title", title);

			DialogOpenTrigger trigger = dialog.getTrigger();
			if (trigger != null) {
				xml.appendOptionalAttribute("triggerid", trigger.getId());
				if (dialog.hasLegacyTriggerButton()) {
					xml.appendClose();
					trigger.paint(renderContext);
					xml.appendEndTag(TAG_NAME);
				} else {
					xml.appendEnd();
				}
			} else {
				xml.appendEnd();
			}
		}
	}
}
