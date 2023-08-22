/*
 * Fix for https://bugzilla.mozilla.org/show_bug.cgi?id=1245292.
 *
 * You only need this for shift+click - shiftKey is reliable in other events.
 * It is even reliable in firefox if the click event was not generated by the spacebar.
 *
 * We can't actually fix the event, it is up to code to call this as necessary.
 *
 * Because this is code we want to delete I'm going to add this property to wc/dom/event instead.
 * It could also logically belong on wc/key.
 *
 * Code can test it second, e.g. `if ($event.shiftKey || eventManager.shiftKey)`.
 * When it is not present there will be no side effects if other code is not refactored.
 *
 * The intention is only to load this module in Firefox, but it really won't matter either way.
 */

import initialise from "wc/dom/initialise";
import eventManager from "wc/dom/event";

let inited = false;

/**
 * When shift key is pressed shiftKey will be true.
 * When shift key is released it will be false.
 * Any other keys will also report the state of the shift key so we may as well listen to them too.
 * @param {KeyboardEvent} $event A keyboard event.
 */
function shiftKeyHandler($event) {
	eventManager.shiftKey = $event.shiftKey;
}

initialise.register({
	initialise: function(element) {
		if (!inited) {  // paranoid, really don't want this registered lots of times.
			inited = true;
			eventManager.add(element, "keydown", shiftKeyHandler, null, null, true);
			eventManager.add(element, "keyup", shiftKeyHandler, null, null, true);
		}
	}
});
