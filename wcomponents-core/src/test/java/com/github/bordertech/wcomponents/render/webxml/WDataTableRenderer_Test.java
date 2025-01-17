package com.github.bordertech.wcomponents.render.webxml;

import com.github.bordertech.wcomponents.SimpleTableDataModel;
import com.github.bordertech.wcomponents.TableDataModel;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDataTable;
import com.github.bordertech.wcomponents.WDataTable.ActionConstraint;
import com.github.bordertech.wcomponents.WDataTable.ExpandMode;
import com.github.bordertech.wcomponents.WDataTable.PaginationMode;
import com.github.bordertech.wcomponents.WDataTable.SortMode;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.util.mock.MockRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.Assert;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.xml.sax.SAXException;

import static com.github.bordertech.wcomponents.render.webxml.WDataTableRenderer.TAG_ACTION;
import static com.github.bordertech.wcomponents.render.webxml.WDataTableRenderer.TAG_CONDITION;

/**
 * Junit test case for {@link WDataTableRenderer}.
 *
 * @author Anthony O'Connor
 * @since 1.0.0
 */
public class WDataTableRenderer_Test extends AbstractWebXmlRendererTestCase {

	/**
	 * table summary test string.
	 */
	private static final String TABLE_SUMMARY_TEST = "Table Summary Test";

	/**
	 * caption test.
	 */
	private static final String CAPTION_TEST = "Caption";

	/**
	 * col 1 heading.
	 */
	private static final String COL1_HEADING_TEST = "First name";

	/**
	 * col 2 heading.
	 */
	private static final String COL2_HEADING_TEST = "Last name";

	/**
	 * col 3 heading.
	 */
	private static final String COL3_HEADING_TEST = "DOB";

	/**
	 * test ActionOne.
	 */
	private static final String TEST_ACTION_ONE = "ACTIONONE";

	/**
	 * test ActionTwo.
	 */
	private static final String TEST_ACTION_TWO = "ACTIONTWO";

	/**
	 * true.
	 */
	private static final String TRUE = "true";

	@Test
	public void testRendererCorrectlyConfigured() {
		WDataTable component = new WDataTable();
		Assert.assertTrue("Incorrect renderer supplied",
				getWebXmlRenderer(component) instanceof WDataTableRenderer);
	}

	@Test
	public void testDoPaintEmptyTableNoAttributes() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setVisible(true);

		setActiveContext(createUIContext());
		assertXpathEvaluatesTo(component.getNoDataMessage(), "//ui:table/ui:tbody/ui:nodata",
				component);
	}

	@Test
	public void testDoPaintMissingAttributes() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setDataModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WDataTable.StripingType.NONE);
		component.setSeparatorType(WDataTable.SeparatorType.NONE);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathNotExists("//ui:table/@striping", component);
		assertXpathNotExists("//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintMissingAttributesRowStriping() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setDataModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WDataTable.StripingType.ROWS);
		component.setSeparatorType(WDataTable.SeparatorType.HORIZONTAL);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathEvaluatesTo("rows", "//ui:table/@striping", component);
		assertXpathEvaluatesTo("horizontal", "//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintMissingAttributesColumnStriping() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setDataModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WDataTable.StripingType.COLUMNS);
		component.setSeparatorType(WDataTable.SeparatorType.VERTICAL);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathEvaluatesTo("cols", "//ui:table/@striping", component);
		assertXpathEvaluatesTo("vertical", "//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintMissingAttributesColumnStripingSeparatorsBoth() throws IOException,
			SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setDataModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WDataTable.StripingType.COLUMNS);
		component.setSeparatorType(WDataTable.SeparatorType.BOTH);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathEvaluatesTo("cols", "//ui:table/@striping", component);
		assertXpathEvaluatesTo("both", "//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintAttributesAndContent() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class)); // renderer class
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, new WTextField())); // renderer instance
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);
		component.setSummary(TABLE_SUMMARY_TEST);
		component.setCaption(CAPTION_TEST);
		component.setType(WDataTable.Type.HIERARCHIC);

		setActiveContext(createUIContext());

		// check ui:table attributes
		String tableId = component.getId();
		assertXpathEvaluatesTo(tableId, "//ui:table/@id", component);
		assertXpathEvaluatesTo(CAPTION_TEST, "//ui:table/@caption", component);
		assertXpathEvaluatesTo("hierarchic", "//ui:table/@type", component);

		// check header values
		String[] colHeaders = {COL1_HEADING_TEST, COL2_HEADING_TEST, COL3_HEADING_TEST};
		for (int i = 0; i < component.getColumnCount(); i++) {
			assertXpathEvaluatesTo(colHeaders[i], "//ui:table/ui:thead/ui:th[" + (i + 1)
					+ "]/ui:decoratedlabel/ui:labelbody", component);
		}

		// check table content
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			for (int j = 0; j < component.getColumnCount(); j++) {
				assertXpathEvaluatesTo((String) tableModel.getValueAt(i, j),
						"//ui:table/ui:tbody/ui:tr[" + (i + 1)
						+ "]/ui:td[" + (j + 1) + "]/ui:textfield",
						component);
			}
		}
	}

	@Test
	public void testDoPaintPaginatedClient() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setPaginationMode(PaginationMode.CLIENT);

		setActiveContext(createUIContext());

		assertXpathEvaluatesTo("client", "//ui:table/ui:pagination/@mode", component);
		assertXpathEvaluatesTo((new Integer(component.getCurrentPage())).toString(),
				"//ui:table/ui:pagination/@currentPage", component);
		assertXpathEvaluatesTo((new Integer(component.getRowsPerPage())).toString(),
				"//ui:table/ui:pagination/@rowsPerPage", component);
		assertXpathEvaluatesTo((new Integer(tableModel.getRowCount())).toString(),
				"//ui:table/ui:pagination/@rows",
				component);

	}

	@Test
	public void testDoPaintPaginatedDynamic() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setPaginationMode(PaginationMode.DYNAMIC);

		setActiveContext(createUIContext());

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:pagination/@mode", component);
		assertXpathEvaluatesTo((new Integer(component.getCurrentPage())).toString(),
				"//ui:table/ui:pagination/@currentPage", component);
		assertXpathEvaluatesTo((new Integer(component.getRowsPerPage())).toString(),
				"//ui:table/ui:pagination/@rowsPerPage", component);
		assertXpathEvaluatesTo((new Integer(tableModel.getRowCount())).toString(),
				"//ui:table/ui:pagination/@rows",
				component);

	}

	@Test
	public void testDoPaintPaginatedServer() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setPaginationMode(PaginationMode.DYNAMIC);

		setActiveContext(createUIContext());

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:pagination/@mode", component);
		assertXpathEvaluatesTo((new Integer(component.getCurrentPage())).toString(),
				"//ui:table/ui:pagination/@currentPage", component);
		assertXpathEvaluatesTo((new Integer(component.getRowsPerPage())).toString(),
				"//ui:table/ui:pagination/@rowsPerPage", component);
		assertXpathEvaluatesTo((new Integer(tableModel.getRowCount())).toString(),
				"//ui:table/ui:pagination/@rows",
				component);
	}

	@Test
	public void testDoPaintSelectModeSingle() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WDataTable.SelectMode.SINGLE);

		assertXpathExists("//ui:table/ui:rowselection", component);
	}

	@Test
	public void testDoPaintSelectModeMultipleSelectAllText() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WDataTable.SelectMode.MULTIPLE);
		component.setSelectAllMode(WDataTable.SelectAllType.TEXT);

		assertXpathExists("//ui:table/ui:rowselection", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:rowselection/@multiple", component);
		assertXpathEvaluatesTo("text", "//ui:table/ui:rowselection/@selectAll", component);
	}

	@Test
	public void testDoPaintSelectModeMultipleSelectAllControl() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WDataTable.SelectMode.MULTIPLE);
		component.setSelectAllMode(WDataTable.SelectAllType.CONTROL);

		assertXpathExists("//ui:table/ui:rowselection", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:rowselection/@multiple", component);
		assertXpathEvaluatesTo("control", "//ui:table/ui:rowselection/@selectAll", component);
	}

	@Test
	public void testDoPaintSelectModeMultipleSelectAllControlSubmitOnChangeAndGroupName() throws
			IOException,
			SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WDataTable.SelectMode.MULTIPLE);
		component.setSelectAllMode(WDataTable.SelectAllType.CONTROL);
		component.setSelectGroup("TEST GROUP NAME");
		component.setSubmitOnRowSelect(true);

		setActiveContext(createUIContext());
		assertXpathExists("//ui:table/ui:rowselection", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:rowselection/@multiple", component);
		assertXpathEvaluatesTo("control", "//ui:table/ui:rowselection/@selectAll", component);
		assertXpathEvaluatesTo(component.getSelectGroup(), "//ui:table/ui:rowselection/@groupName",
				component);
	}

	@Test
	public void testDoPaintExpandModeClient() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setExpandMode(ExpandMode.CLIENT);

		assertXpathEvaluatesTo("client", "//ui:table/ui:rowexpansion/@mode", component);
	}

	// SERVER outputs "dynamic" see https://github.com/BorderTech/wcomponents/issues/701
	@Test
	public void testDoPaintExpandModeServer() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setExpandMode(ExpandMode.SERVER);

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:rowexpansion/@mode", component);
	}

	@Test
	public void testDoPaintExpandModeLazy() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setExpandMode(ExpandMode.LAZY);

		assertXpathEvaluatesTo("lazy", "//ui:table/ui:rowexpansion/@mode", component);
	}

	@Test
	public void testDoPaintExpandModeDynamic() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setExpandMode(ExpandMode.DYNAMIC);

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:rowexpansion/@mode", component);
	}

	@Test
	public void testDoPaintSortableSortModeDynamic() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));

		TableDataModel tableModel = createTableModelSortable();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setSortMode(SortMode.DYNAMIC); // sortMode dynamic

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:sort/@mode", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[1]/@sortable", component);
		assertXpathNotExists("//ui:table/ui:thead/ui:th[2]/@sortable", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[3]/@sortable", component);
	}

	@Test
	public void testDoPaintWithColAlignment() throws IOException, SAXException, XpathException {
		WDataTable table = new WDataTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));

		table.getColumn(0).setAlign(WTableColumn.Alignment.LEFT);
		table.getColumn(1).setAlign(WTableColumn.Alignment.CENTER);
		table.getColumn(2).setAlign(WTableColumn.Alignment.RIGHT);

		TableDataModel tableModel = createTableModel();
		table.setDataModel(tableModel);

		assertXpathNotExists("//ui:table/ui:thead/ui:th[1]/@align", table);
		assertXpathEvaluatesTo("center", "//ui:table/ui:thead/ui:th[2]/@align", table);
		assertXpathEvaluatesTo("right", "//ui:table/ui:thead/ui:th[3]/@align", table);
	}

	@Test
	public void testDoPaintWithColWidth() throws IOException, SAXException, XpathException {

		WDataTable table = new WDataTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));

		table.getColumn(0).setWidth(0);
		table.getColumn(1).setWidth(1);
		table.getColumn(2).setWidth(100);

		TableDataModel tableModel = createTableModel();
		table.setDataModel(tableModel);

		assertXpathNotExists("//ui:table/ui:thead/ui:th[1]/@width", table);
		assertXpathEvaluatesTo("1", "//ui:table/ui:thead/ui:th[2]/@width", table);
		assertXpathEvaluatesTo("100", "//ui:table/ui:thead/ui:th[3]/@width", table);
	}

	// SERVER outputs "dynamic" see https://github.com/BorderTech/wcomponents/issues/701
	@Test
	public void testDoPaintSortableSortModeServer() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModelSortable(); // sortable data model
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.setSortMode(SortMode.SERVER);

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:sort/@mode", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[1]/@sortable", component);
		assertXpathNotExists("//ui:table/ui:thead/ui:th[2]/@sortable", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[3]/@sortable", component);
	}

	@Test
	public void testDoPaintSortableSortModeDynamicClientSettings() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableDataModel = createTableModelSortable(); // sortable data model
		component.setDataModel(tableDataModel);
		component.setVisible(true);

		component.setSortMode(SortMode.DYNAMIC);

		setActiveContext(createUIContext());
		MockRequest request = new MockRequest();
		String colIndexStr = "0";
		request.setParameter(component.getId() + "-h", "x");
		request.setParameter(component.getId() + ".sort", colIndexStr);
		request.setParameter(component.getId() + ".sortDesc", TRUE);
		component.handleRequest(request);


		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:sort/@mode", component);
		assertXpathEvaluatesTo(colIndexStr, "//ui:table/ui:sort/@col", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:sort/@descending", component);

		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[1]/@sortable", component);
		assertXpathNotExists("//ui:table/ui:thead/ui:th[2]/@sortable", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[3]/@sortable", component);
	}

	@Test
	public void testDoPaintTableActions() throws IOException, SAXException, XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		component.addAction(new WButton(TEST_ACTION_ONE));
		component.addAction(new WButton(TEST_ACTION_TWO));

		assertXpathExists("//ui:table/ui:actions", component);
		assertXpathEvaluatesTo(TEST_ACTION_ONE, String.format("//ui:table/ui:actions/html:%s[1]/html:button", TAG_ACTION),
				component);
		assertXpathEvaluatesTo(TEST_ACTION_TWO, String.format("//ui:table/ui:actions/html:%s[2]/html:button", TAG_ACTION),
				component);
	}

	@Test
	public void testDoPaintTableActionsWithConstraints() throws IOException, SAXException,
			XpathException {
		final int minSelectedRowCount1 = 1;
		final int maxSelectedRowCount1 = 2;
		final String message1 = "message1";

		final int minSelectedRowCount2 = 1;
		final int maxSelectedRowCount2 = 2;
		final String message2 = "message2";

		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		WButton buttonOne = new WButton(TEST_ACTION_ONE);
		WButton buttonTwo = new WButton(TEST_ACTION_TWO);
		component.addAction(buttonOne);
		component.addAction(buttonTwo);
		component.addActionConstraint(buttonOne, new ActionConstraint(minSelectedRowCount1,
				maxSelectedRowCount1,
				true, message1));
		component.addActionConstraint(buttonTwo, new ActionConstraint(minSelectedRowCount2,
				maxSelectedRowCount2,
				false, message2));

		assertXpathExists("//ui:table/ui:actions", component);
		assertXpathEvaluatesTo(TEST_ACTION_ONE, String.format("//ui:table/ui:actions/html:%s[1]/html:button", TAG_ACTION),
				component);
		assertXpathEvaluatesTo(TEST_ACTION_TWO, String.format("//ui:table/ui:actions/html:%s[2]/html:button", TAG_ACTION),
				component);

		String expectedWarning = "error";
		assertXpathEvaluatesTo(((new Integer(minSelectedRowCount1))).toString(),
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@min", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(((new Integer(maxSelectedRowCount1))).toString(),
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@max", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(expectedWarning,
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@type", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(message1, String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@message", TAG_ACTION, TAG_CONDITION),
				component);

		expectedWarning = "warning";
		assertXpathEvaluatesTo(((new Integer(minSelectedRowCount2))).toString(),
				String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@min", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(((new Integer(maxSelectedRowCount2))).toString(),
				String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@max", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(expectedWarning,
				String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@type", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(message2, String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@message", TAG_ACTION, TAG_CONDITION),
				component);
	}

	@Test
	public void testDoPaintTableActionsInvisibleButton() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableModel = createTableModel();
		component.setDataModel(tableModel);
		component.setVisible(true);

		WButton button1 = new WButton(TEST_ACTION_ONE);
		component.addAction(button1);

		// Visible
		assertXpathExists("//ui:table/ui:actions", component);

		// Not Visible
		button1.setVisible(false);
		assertXpathNotExists("//ui:table/ui:actions", component);
	}

	@Test
	public void testDoPaintWithInvisibleColumnAndNoColumnHeaders() throws IOException, SAXException,
			XpathException {
		WDataTable component = new WDataTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableDataModel tableDataModel = createTableModel();
		component.setDataModel(tableDataModel);
		component.setVisible(true);

		final int testColIndex = 1;
		final boolean testShowColHeaders = false;
		component.getColumn(testColIndex).setVisible(false);
		component.setShowColumnHeaders(testShowColHeaders);

		// head hidden=true
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/@hidden", component);

		// column headers - only COL1 and COL3 showing - in positions 1 and 2 respectively - only 2 cols
		assertXpathEvaluatesTo(COL1_HEADING_TEST,
				"//ui:table/ui:thead/ui:th[1]/ui:decoratedlabel/ui:labelbody", component);
		assertXpathEvaluatesTo(COL3_HEADING_TEST,
				"//ui:table/ui:thead/ui:th[2]/ui:decoratedlabel/ui:labelbody", component);
		assertXpathNotExists("//ui:table/ui:thead/ui:th[3]/ui:decoratedlabel/ui:labelbody",
				component);

		// first row - col1 and col3 from model in positions 1 and 2 respectively - only 2 cols showing
		String firstName = (String) tableDataModel.getValueAt(0, 0);
		String entryDate = (String) tableDataModel.getValueAt(0, 2);

		assertXpathEvaluatesTo(firstName, "//ui:table/ui:tbody/ui:tr[1]/ui:td[1]/ui:textfield",
				component);
		assertXpathEvaluatesTo(entryDate, "//ui:table/ui:tbody/ui:tr[1]/ui:td[2]/ui:textfield",
				component);
		assertXpathNotExists("//ui:table/ui:tbody/ui:tr[1]/ui:td[3]/ui:textfield", component);
	}

	@Test
	public void testXssEscaping() throws IOException, SAXException, XpathException {
		WDataTable table = new WDataTable();
		table.addColumn(new WTableColumn(getMaliciousContent(), WText.class));
		table.addColumn(new WTableColumn(getMaliciousContent(), WText.class));
		table.addColumn(new WTableColumn(getMaliciousContent(), WText.class));
		table.setNoDataMessage(getMaliciousAttribute("ui:table"));

		UIContext uic = createUIContext();
		assertSafeContent(table);

		WButton button = new WButton("dummy");
		table.addAction(button);
		table.addActionConstraint(button, new ActionConstraint(0, 1, false, getMaliciousAttribute(
				"ui:action")));
		assertSafeContent(table);

		TableDataModel tableModel = createTableModel();
		table.setDataModel(tableModel);
		uic.clearScratchMap(); // clear out cached data from previous renders
		assertSafeContent(table);

		table.setCaption(getMaliciousAttribute("ui:table"));
		assertSafeContent(table);

		table.setSummary(getMaliciousAttribute("ui:table"));
		assertSafeContent(table);

		table.setSelectGroup(getMaliciousAttribute("ui:rowselection"));
		assertSafeContent(table);

		table.setActiveFilters(Arrays.asList(new String[]{getMaliciousAttribute("ui:table")}));
	}

	/**
	 * @return a test TableDataModel.
	 */
	private TableDataModel createTableModel() {
		String[][] data = new String[][]{new String[]{"Joe", "Bloggs", "01/02/1973"},
		new String[]{"Jane", "Bloggs", "04/05/1976"},
		new String[]{"Kid", "Bloggs", "31/12/1999"}};

		SimpleTableDataModel model = new SimpleTableDataModel(data);
		model.setEditable(true);

		return model;
	}

	/**
	 * @return a test TableDataModel for sroting
	 */
	private TableDataModel createTableModelSortable() {
		String[][] data = new String[][]{new String[]{"Joe2", "Bloggs2", "01/02/1971"},
		new String[]{"Jane2", "Bloggs2", "04/05/1972"},
		new String[]{"Abel2", "Bloggs2", "31/12/2000"}};

		SimpleTableDataModel model = new SimpleTableDataModel(data);

		// col 1 and 3 sortable
		model.setComparator(0, new StringComparator());
		// model.setComparator(1, new StringComparator());
		model.setComparator(2, new StringComparator());

		// sort on column1, descending - tableDataModel only
		model.sort(0, false);

		model.setEditable(true);
		return model;
	}

	/**
	 * Simple String Comparator - for use in test.
	 */
	private static final class StringComparator implements Comparator<String> {

		@Override
		public int compare(final String str1, final String str2) {
			return str1.compareTo(str2);
		}
	}
}
