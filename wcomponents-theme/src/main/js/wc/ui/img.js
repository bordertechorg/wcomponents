define(["wc/dom/initialise",
	"wc/dom/Widget",
	"wc/loader/resource",
	"wc/template",
	"wc/i18n/i18n",
	"wc/ui/ajax/processResponse"],
	function(initialise, Widget, loader, template, i18n, processResponse) {
		"use strict";

		function Image() {
			var EDIT, IMG = new Widget("img", "", {"data-wc-editor": null});

			function makeEditButton(element) {
				var id = element.id,
					sibling = element.nextSibling;
				if (sibling && sibling.getAttribute("data-wc-img") === id) {
					return;
				}
				loader.load("imgedit.html", true, true).then(function (rawTemplate) {
					var props = {
						id: id,
						editor: element.getAttribute("data-wc-editor"),
						text: EDIT || (EDIT = i18n.get("imgedit_edit"))
					};

					template.process({
						source: rawTemplate,
						target: element,
						context: props,
						position: "afterend"
					});
				});
			}

			function makeEditButtons(element) {
				var el = element || document.body;
				if (element && IMG.isOneOfMe(element)) {
					makeEditButton(element);
				}
				else {
					Array.prototype.forEach.call(IMG.findDescendants(el), makeEditButton);
				}
			}

			this.postInit = function() {
				processResponse.subscribe(makeEditButtons, true);
			};

			this.initialise = function (element) {
				makeEditButtons(element);
			};
		}

		/**
		 * Models an editable image.
		 * @module
		 */
		var instance = new Image();
		initialise.register(instance);
		return instance;
	});