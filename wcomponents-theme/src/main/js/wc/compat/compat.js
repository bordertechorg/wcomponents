/**
 * This loader plugin module determines the the dependencies we need to load the configure JS environment with the necessary
 * features expected by the rest of the codebase, i.e. polyfills.
 *
 * It is intended that this loader plugin will be a loader dependency <http://requirejs.org/docs/api.html#config-deps>
 * it **must** run before other modules because it loads the compatibility modules and fixes required for
 * this browser to handle the rest of the codebase.
 *
 * You **must not** load anything here that needs to wire up events (those are fixes, not compatibility
 * code). This is for basic scripting API support.
 *
 * Many of the tests are written by us for our own specific needs however some are also lifted with little or no
 * change from the has project: <https://github.com/phiggins42/has.js/>
 *
 * Read the source Luke!
 *
 * @module
 * @private
 * @param has @ignore
 */
define(["wc/has"], function(has) {
	"use strict";
	var result = ["lib/dojo/sniff"];

	(function(addtest) {
		// This block taken from tests from hasjs project. Didn't want to load the whole script.
		addtest("bug-getelementsbyname", function(g, d) {
			var buggy,
				script = d.createElement("script"),
				id = "__test_" + Number(new Date()),
				root = d.getElementsByTagName("script")[0].parentNode;

			script.id = id;
			script.type = "text/javascript";
			root.insertBefore(script, root.firstChild);
			buggy = d.getElementsByName(id)[0] === script;
			root.removeChild(script);
			return buggy;
		});

		// true for IE < 9
		// http://msdn.microsoft.com/en-us/library/ms536389(VS.85).aspx vs
		// http://www.w3.org/TR/DOM-Level-3-Core/core.html#ID-2141741547
		addtest("dom-create-attr", function(g, d) {
			var input,
				supported = false;
			try {
				input = d.createElement("<input type='hidden' name='hasjs'>");
				supported = input.type === "hidden" && input.name === "hasjs";
			} catch (e) {
				// Do nothing
			}
			return supported;
		});
	})(has.add);

	(function(addtest) {

		addtest("ie-compat-mode", function(g) {
			var isCompatModeRe = /MSIE 7\..+Trident\/\d/;
			return isCompatModeRe.test(g.navigator.userAgent);
		});

		addtest("activex", function(g) {
			return !!("ActiveXObject" in g);
		});

		addtest("flash", function(g) {
			var flashPlayer, hasFlash = false;
			if (has("activex")) {
				try {
					flashPlayer = new window.ActiveXObject("ShockwaveFlash.ShockwaveFlash");
					if (flashPlayer) {
						hasFlash = true;
					}
				} catch (ignore) {
					// ignore
				}
			}
			hasFlash = hasFlash || g.navigator && g.navigator.plugins && g.navigator.plugins["Shockwave Flash"];
			return hasFlash;
		});

		addtest("bug-button-value", function(g, d) {
			var button, value = "hi";
			button = d.createElement("button");
			button.value = value;
			button.innerHTML = "<span>howdy</span>";
			return button.value !== value;
		});

		addtest("formdata", function(g) {
			return "FormData" in g;
		});

		addtest("draganddrop", function(g, d, el) {
			return "draggable" in el;
		});

		addtest("native-console", function(g) {
			return ("console" in g);
		});

		addtest("native-console-debug", function(g) {
			return (has("native-console") && "debug" in g.console);
		});

		addtest("native-console-time", function(g) {
			return (has("native-console") && "time" in g.console);
		});

		addtest("native-console-group", function(g) {
			return (has("native-console") && "group" in g.console);
		});

		addtest("native-console-table", function(g) {
			return (has("native-console") && "table" in g.console);
		});

		addtest("global-keyevent", function(g) {
			return ("KeyEvent" in g);
		});

		addtest("global-performance", function(g) {
			return (("performance" in g) && !!g.performance);  // will be present but null in FF 17 if page generated by XSLT
		});

		addtest("global-performance-marking", function(g) {
			return (has("global-performance") && "mark" in g.performance);
		});

		addtest("dom-canvas", function(g, d) {
			var e = d.createElement("canvas");
			return !!(e.getContext && e.getContext("2d"));
		});

		addtest("promise-es6", function(g) {
			return ("Promise" in g);
		});

		addtest("object-assign", function(g) {
			return (typeof g.Object.assign === "function");
		});

		addtest("string-repeat", function() {
			return !!String.prototype.repeat;
		});

		addtest("element-datalist", function() {
			return "list" in document.createElement("input");
		});

		addtest("element-details", function() {
			return "open" in document.createElement("details");
		});

		addtest("css-flex", function(g, d) {
			var c,
				start,
				end;
			if (!g.getComputedStyle) {
				return false;
			}
			c = d.createElement("div");
			try {
				d.body.appendChild(c);
				start = g.getComputedStyle(c, null).display;
				c.style.display = "flex";
				end = g.getComputedStyle(c, null).display;
				return (start !== end);
			} catch (e) {
				return false;
			} finally {
				d.body.removeChild(c);
			}
		});

		addtest("native-dateinput", function() {
			var el,
				d = "date",
				res = false;
			try {
				el = document.createElement("input");
				el.type = d;
				res = d === el.type;
			} catch (e) {
				res = false;
			}
			return res;
		});

		addtest("rtc-gum", function(g) {
			var i, next, props = ["getUserMedia", "webkitGetUserMedia", "mozGetUserMedia", "msGetUserMedia"];
			for (i = 0; i < props.length; i++) {
				next = props[i];
				if ((g.navigator.mediaDevices && g.navigator.mediaDevices[next]) || g.navigator[next]) {
					return true;
				}
			}
			return false;
		});
	})(has.add);

	// ALWAYS FETCH
	// as little as possible

	// CONDITIONALLY FETCH
	if (!has("string-repeat")) {
		result.push("wc/ecma6/String.prototype.repeat");
	}

	if (!has("object-assign")) {
		result.push("wc/ecma6/Object.assign");
	}

	if (!has("promise-es6")) {
		result.push("Promise");
	}

	if (!(has("native-console") && has("native-console-debug") && has("native-console-table") && has("native-console-group"))) {
		result.push("wc/compat/console");
	}

	if (!has("global-keyevent")) {
		result.push("wc/compat/KeyEvent");
	}

	if (has("bug-getelementsbyname")) {
		result.push("wc/fix/getElementsByName_ie9");
	}
	if (has("activex")) {
		// while not strictly a fix classes can't put this in their dependency lists so we need to load it really early for conditional loading.
		result.push("wc/fix/getActiveX_ieAll");
	}

	/*
	 * The polyfill for global performance gets loaded up too late to attach load event listeners.
	 * Putting it into wc/fixes gets loaded up too late to attach load event listeners. They have
	 * to be here whether we like it or not. This fix is ONLY needed for IE versions which do not
	 * have globel performance.
	 */
	if (!(has("global-performance") || has("dom-addeventlistener"))) {
		window.attachEvent("onload", function() {
			if (window.requirejs) {
				window.requirejs.config({config: {"wc/compat/navigationTiming": {
					"loadEventStart": ((new Date()) * 1),/* NOTE: our polyfill of Date.now() has not yet loaded */
					"loadEventEnd": ((new Date()) * 1)}}});
			}
		});
	}

	result.load = function (id, parentRequire, callback) {
		parentRequire(result, callback);
	};
	return result;
});
