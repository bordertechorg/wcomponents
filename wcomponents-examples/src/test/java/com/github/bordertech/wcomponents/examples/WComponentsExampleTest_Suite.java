package com.github.bordertech.wcomponents.examples;

import com.github.bordertech.wcomponents.examples.petstore.PetStore_Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This class is the <a href="http://www.junit.org">JUnit</a> TestSuite for the classes within
 * {@link com.github.bordertech.wcomponents.examples} package.
 *
 * @author Yiannis Paschalidis
 * @author Mark Reeves
 * @since 1.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AjaxWDropdownExample_Test.class,
	AppPreferenceParameterExample_Test.class,
	AutoReFocusExample_Test.class,
	AutoReFocusRepeaterExample_Test.class,
	CheckBoxExample_Test.class,
	EntryFieldExample_Test.class,
	ErrorGenerator_Test.class,
	ForwardExample_Test.class,
	HtmlInjector_Test.class,
	InfoDump_Test.class,
	RadioButtonExample_Test.class,
	// TODO Not sure why not working
	// SimpleFileUpload_Test.class,
	SimplePaginationWithRowOptionsTableExample_Test.class,
	SimpleTabs_Test.class,
	TextAreaExample_Test.class,
	TextDuplicatorHandleRequestImpl_Test.class,
	TextDuplicatorSeleniumImpl_Test.class,
	TextDuplicatorVelocityImpl_Test.class,
	TextFieldExample_Test.class,
	WButtonExample_Test.class,
	WCheckBoxExample_Test.class,
	WCheckBoxSelectExample_Test.class,
	WCheckBoxTriggerActionExample_Test.class,
	WDialogExample_Test.class,
	WDropdownOptionsExample_Test.class,
	WDropdownSpaceHandlingExample_Test.class,
	WDropdownSpecialCharHandlingExample_Test.class,
	WDropdownSubmitOnChangeExample_Test.class,
	WDropdownTriggerActionExample_Test.class,
	WPopupExample_Test.class,
	WRadioButtonSelectExample_Test.class,
	WRadioButtonSubmitOnChangeExample_Test.class,
	WRadioButtonTriggerActionExample_Test.class,
	WTextExample_Test.class,
	PetStore_Suite.class
})

/**
 * WComponets example test suite.
 */
public class WComponentsExampleTest_Suite {

}
