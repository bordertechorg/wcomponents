/**
 * @module
 */
import timers from "wc/timers.mjs";

/**
 * Provides a helper to remove elements from the DOM. This helper was built especially to remove SCRIPT elements
 * generated by the registration scripts and debugInfo registration. You probably don't want to use it as a generic
 * element removal wrapper, but you could.
 *
 * @function module:wc/dom/removeElement
 *
 * @param {string} id The id of the element to be removed.
 * @param {number} [useTimeout] If set then wrap the removal in a timeout of this many milliseconds. Probably pointless
 * especially if this is called from a `finally` block.
 */
function remove(id, useTimeout) {
	const _timeout = useTimeout || 0;
	if (!id) {
		return;
	}

	function _remove() {
		const el = document.getElementById(id);
		const parent = el?.parentElement;
		if (parent) {
			parent.removeChild(el);
		}
	}

	if (useTimeout || useTimeout === 0) {
		timers.setTimeout(_remove, _timeout);
	} else {
		_remove();
	}
}
export default remove;