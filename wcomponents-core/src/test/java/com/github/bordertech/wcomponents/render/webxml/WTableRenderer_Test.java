package com.github.bordertech.wcomponents.render.webxml;

import com.github.bordertech.wcomponents.AdapterBasicTableModel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.SimpleTableModel;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTable.ActionConstraint;
import com.github.bordertech.wcomponents.WTable.ExpandMode;
import com.github.bordertech.wcomponents.WTable.PaginationLocation;
import com.github.bordertech.wcomponents.WTable.PaginationMode;
import com.github.bordertech.wcomponents.WTable.SortMode;
import com.github.bordertech.wcomponents.WTable.TableModel;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.util.mock.MockRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import static com.github.bordertech.wcomponents.render.webxml.WTableRenderer.TAG_ACTION;
import static com.github.bordertech.wcomponents.render.webxml.WTableRenderer.TAG_CONDITION;

/**
 * Junit test case for {@link WTableRenderer}.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class WTableRenderer_Test extends AbstractWebXmlRendererTestCase {

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

	/**
	 * Path for footer column.
	 */
	private static final String FOOTER_PATH = "//ui:table/html:tfoot/html:tr/html:td";

	@Test
	public void testRendererCorrectlyConfigured() {
		WTable component = new WTable();
		Assert.assertTrue("Incorrect renderer supplied",
				getWebXmlRenderer(component) instanceof WTableRenderer);
	}

	@Test
	public void testDoPaintEmptyTableNoAttributes() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setVisible(true);

		setActiveContext(createUIContext());
		assertXpathEvaluatesTo(component.getNoDataMessage(), "//ui:table/ui:tbody/ui:nodata",
				component);

		assertSchemaMatch(component);
	}

	@Test
	public void testDoPaintMissingAttributes() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setTableModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WTable.StripingType.NONE);
		component.setSeparatorType(WTable.SeparatorType.NONE);

		assertSchemaMatch(component);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathNotExists("//ui:table/@striping", component);
		assertXpathNotExists("//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintMissingAttributesRowStriping() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setTableModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WTable.StripingType.ROWS);
		component.setSeparatorType(WTable.SeparatorType.HORIZONTAL);

		assertSchemaMatch(component);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathEvaluatesTo("rows", "//ui:table/@striping", component);
		assertXpathEvaluatesTo("horizontal", "//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintMissingAttributesColumnStriping() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setTableModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WTable.StripingType.COLUMNS);
		component.setSeparatorType(WTable.SeparatorType.VERTICAL);

		assertSchemaMatch(component);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathEvaluatesTo("cols", "//ui:table/@striping", component);
		assertXpathEvaluatesTo("vertical", "//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintMissingAttributesColumnStripingSeparatorsBoth() throws IOException,
			SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		component.setTableModel(createTableModel());
		component.setVisible(true);
		component.setStripingType(WTable.StripingType.COLUMNS);
		component.setSeparatorType(WTable.SeparatorType.BOTH);

		assertSchemaMatch(component);

		assertXpathNotExists("//ui:table/@caption", component);
		assertXpathEvaluatesTo("table", "//ui:table/@type", component);
		assertXpathEvaluatesTo("cols", "//ui:table/@striping", component);
		assertXpathEvaluatesTo("both", "//ui:table/@separators", component);
	}

	@Test
	public void testDoPaintAttributesAndContent() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class)); // renderer class
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, new WTextField())); // renderer instance
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);
		component.setCaption(CAPTION_TEST);
		component.setType(WTable.Type.HIERARCHIC);

		setActiveContext(createUIContext());

		assertSchemaMatch(component);

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
			List<Integer> row = new ArrayList<>();
			row.add(i);
			for (int j = 0; j < component.getColumnCount(); j++) {
				assertXpathEvaluatesTo((String) tableModel.getValueAt(row, j),
						"//ui:table/ui:tbody/ui:tr[" + (i + 1)
						+ "]/ui:td[" + (j + 1)
						+ "]/ui:textfield", component);
			}
		}
	}

	@Test
	public void testDoPaintPaginatedClient() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setPaginationMode(PaginationMode.CLIENT);

		setActiveContext(createUIContext());

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("client", "//ui:table/ui:pagination/@mode", component);
		assertXpathEvaluatesTo((new Integer(component.getCurrentPage())).toString(),
				"//ui:table/ui:pagination/@currentPage", component);
		assertXpathEvaluatesTo((new Integer(component.getRowsPerPage())).toString(),
				"//ui:table/ui:pagination/@rowsPerPage", component);
		assertXpathEvaluatesTo((new Integer(tableModel.getRowCount())).toString(),
				"//ui:table/ui:pagination/@rows",
				component);

		assertXpathNotExists("//ui:table/ui:pagination/@controls", component);

		component.setPaginationLocation(PaginationLocation.BOTTOM);

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("bottom", "//ui:table/ui:pagination/@controls", component);

	}

	@Test
	public void testDoPaintPaginatedDynamic() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setPaginationMode(PaginationMode.DYNAMIC);

		setActiveContext(createUIContext());

		assertSchemaMatch(component);

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
	public void testDoPaintWithRowsPerPageOptions() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setPaginationMode(PaginationMode.DYNAMIC);
		component.setRowsPerPageOptions(Arrays.asList(10, 20, 30));

		setActiveContext(createUIContext());

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:pagination/@mode", component);
		assertXpathEvaluatesTo((new Integer(component.getCurrentPage())).toString(),
				"//ui:table/ui:pagination/@currentPage", component);
		assertXpathEvaluatesTo((new Integer(component.getRowsPerPage())).toString(),
				"//ui:table/ui:pagination/@rowsPerPage", component);
		assertXpathEvaluatesTo((new Integer(tableModel.getRowCount())).toString(),
				"//ui:table/ui:pagination/@rows",
				component);
		assertXpathEvaluatesTo("10", "//ui:table/ui:pagination/ui:rowsselect/ui:option[1]/@value",
				component);
		assertXpathEvaluatesTo("20", "//ui:table/ui:pagination/ui:rowsselect/ui:option[2]/@value",
				component);
		assertXpathEvaluatesTo("30", "//ui:table/ui:pagination/ui:rowsselect/ui:option[3]/@value",
				component);

	}

	@Test
	public void testDoPaintSelectModeSingle() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WTable.SelectMode.SINGLE);

		assertSchemaMatch(component);

		assertXpathExists("//ui:table/ui:rowselection", component);
	}

	@Test
	public void testDoPaintSelectModeMultipleSelectAllText() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WTable.SelectMode.MULTIPLE);
		component.setSelectAllMode(WTable.SelectAllType.TEXT);

		assertSchemaMatch(component);

		assertXpathExists("//ui:table/ui:rowselection", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:rowselection/@multiple", component);
		assertXpathEvaluatesTo("text", "//ui:table/ui:rowselection/@selectAll", component);
	}

	@Test
	public void testDoPaintSelectModeMultipleSelectAllControl() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WTable.SelectMode.MULTIPLE);
		component.setSelectAllMode(WTable.SelectAllType.CONTROL);

		assertSchemaMatch(component);

		assertXpathExists("//ui:table/ui:rowselection", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:rowselection/@multiple", component);
		assertXpathEvaluatesTo("control", "//ui:table/ui:rowselection/@selectAll", component);
		// toggle not available by default
		assertXpathNotExists("//ui:table/ui:rowselection/@toggle", component);
	}

	@Test
	public void testDoPaintToggleSubRowSelection() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WTable.SelectMode.MULTIPLE);
		component.setToggleSubRowSelection(true);
		component.setExpandMode(ExpandMode.CLIENT);

		assertSchemaMatch(component);

		assertXpathExists("//ui:table/ui:rowselection", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:rowselection/@toggle", component);
	}

	@Test
	public void testDoPaintSelectModeMultipleSelectAllControlSubmitOnChangeAndGroupName() throws
			IOException,
			SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setSelectMode(WTable.SelectMode.MULTIPLE);
		component.setSelectAllMode(WTable.SelectAllType.CONTROL);

		setActiveContext(createUIContext());

		assertSchemaMatch(component);

		assertXpathExists("//ui:table/ui:rowselection", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:rowselection/@multiple", component);
		assertXpathEvaluatesTo("control", "//ui:table/ui:rowselection/@selectAll", component);
	}

	@Test
	public void testDoPaintExpandModeClient() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setExpandMode(ExpandMode.CLIENT);

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("client", "//ui:table/ui:rowexpansion/@mode", component);
	}

	@Test
	public void testDoPaintExpandModeLazy() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setExpandMode(ExpandMode.LAZY);

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("lazy", "//ui:table/ui:rowexpansion/@mode", component);
	}

	@Test
	public void testDoPaintExpandModeDynamic() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setExpandMode(ExpandMode.DYNAMIC);

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:rowexpansion/@mode", component);
	}

	@Test
	public void testDoPaintSortableSortModeDynamic() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));

		TableModel tableModel = createTableModelSortable();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setSortMode(SortMode.DYNAMIC); // sortMode dynamic

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:sort/@mode", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[1]/@sortable", component);
		assertXpathNotExists("//ui:table/ui:thead/ui:th[2]/@sortable", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[3]/@sortable", component);
	}

	@Test
	public void testDoPaintWithColAlignment() throws IOException, SAXException, XpathException {
		WTable table = new WTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));

		table.getColumn(0).setAlign(WTableColumn.Alignment.LEFT);
		table.getColumn(1).setAlign(WTableColumn.Alignment.CENTER);
		table.getColumn(2).setAlign(WTableColumn.Alignment.RIGHT);

		TableModel tableModel = createTableModel();
		table.setTableModel(tableModel);

		assertSchemaMatch(table);

		assertXpathNotExists("//ui:table/ui:thead/ui:th[1]/@align", table);
		assertXpathEvaluatesTo("center", "//ui:table/ui:thead/ui:th[2]/@align", table);
		assertXpathEvaluatesTo("right", "//ui:table/ui:thead/ui:th[3]/@align", table);
	}

	@Test
	public void testDoPaintWithColWidth() throws IOException, SAXException, XpathException {

		WTable table = new WTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));

		table.getColumn(0).setWidth(0);
		table.getColumn(1).setWidth(1);
		table.getColumn(2).setWidth(100);

		TableModel tableModel = createTableModel();
		table.setTableModel(tableModel);

		assertSchemaMatch(table);

		assertXpathNotExists("//ui:table/ui:thead/ui:th[1]/@width", table);
		assertXpathEvaluatesTo("1", "//ui:table/ui:thead/ui:th[2]/@width", table);
		assertXpathEvaluatesTo("100", "//ui:table/ui:thead/ui:th[3]/@width", table);
	}

	@Test
	public void testDoPaintOfRowHeaderWithNoRowHeaderColumn() throws IOException, SAXException, XpathException {

		WTable table = new WTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		// no row headers set

		TableModel tableModel = createTableModel();
		table.setTableModel(tableModel);

		assertSchemaMatch(table);

		assertXpathNotExists("//ui:table/ui:tbody/ui:tr/ui:th", table);
	}

	@Test
	public void testDoPainWithRowHeaderColumn() throws IOException, SAXException, XpathException {

		WTable table = new WTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		// set the first column as the row headers
		table.setRowHeaders(true);

		TableModel tableModel = createTableModel();
		table.setTableModel(tableModel);

		assertSchemaMatch(table);

		assertXpathExists("//ui:table/ui:tbody/ui:tr/ui:th", table);
		assertXpathNotExists("//ui:table/ui:tbody/ui:tr/ui:th[2]", table);
	}

	@Test
	public void testDoPaintSortableSortModeDynamicClientSettings() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModelSortable(); // sortable data model
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.setSortMode(SortMode.DYNAMIC);

		setActiveContext(createUIContext());
		MockRequest request = new MockRequest();
		String colIndexStr = "0";
		request.setParameter(component.getId() + "-h", "x");
		request.setParameter(component.getId() + ".sort", colIndexStr);
		request.setParameter(component.getId() + ".sortDesc", TRUE);
		component.handleRequest(request);

		assertSchemaMatch(component);

		assertXpathEvaluatesTo("dynamic", "//ui:table/ui:sort/@mode", component);
		assertXpathEvaluatesTo(colIndexStr, "//ui:table/ui:sort/@col", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:sort/@descending", component);

		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[1]/@sortable", component);
		assertXpathNotExists("//ui:table/ui:thead/ui:th[2]/@sortable", component);
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/ui:th[3]/@sortable", component);
	}

	@Test
	public void testDoPaintTableActions() throws IOException, SAXException, XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		component.addAction(new WButton(TEST_ACTION_ONE));
		component.addAction(new WButton(TEST_ACTION_TWO));

		// assertSchemaMatch(component);

		assertXpathExists("//ui:table/ui:actions", component);
		assertXpathEvaluatesTo(TEST_ACTION_ONE, String.format("//ui:table/ui:actions/html:%s[1]/html:button", TAG_ACTION), component);
		assertXpathEvaluatesTo(TEST_ACTION_TWO, String.format("//ui:table/ui:actions/html:%s[2]/html:button", TAG_ACTION), component);
	}

	@Test
	public void testDoPaintTableActionsWithConstraints() throws IOException, SAXException,
			XpathException {
		final int minSelectedRowCount1 = 1;
		final int maxSelectedRowCount1 = 2;
		final int selectedOnOther1 = 0;
		final String message1 = "message1";

		final int minSelectedRowCount2 = 1;
		final int maxSelectedRowCount2 = 2;
		final int selectedOnOther2 = 0;
		final String message2 = "message2";

		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		WButton buttonOne = new WButton(TEST_ACTION_ONE);
		WButton buttonTwo = new WButton(TEST_ACTION_TWO);
		component.addAction(buttonOne);
		component.addAction(buttonTwo);
		component.addActionConstraint(buttonOne, new ActionConstraint(minSelectedRowCount1,
				maxSelectedRowCount1, true,
				message1));
		component.addActionConstraint(buttonTwo, new ActionConstraint(minSelectedRowCount2,
				maxSelectedRowCount2,
				false, message2));

		// assertSchemaMatch(component);

		assertXpathExists("//ui:table/ui:actions", component);
		assertXpathEvaluatesTo(TEST_ACTION_ONE, String.format("//ui:table/ui:actions/html:%s[1]/html:button", TAG_ACTION), component);
		assertXpathEvaluatesTo(TEST_ACTION_TWO, String.format("//ui:table/ui:actions/html:%s[2]/html:button", TAG_ACTION), component);

		String expectedWarning = "error";
		assertXpathEvaluatesTo(((new Integer(minSelectedRowCount1))).toString(),
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@min", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(((new Integer(maxSelectedRowCount1))).toString(),
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@max", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(((new Integer(selectedOnOther1))).toString(),
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@other", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(expectedWarning,
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@type", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(message1, String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@message", TAG_ACTION, TAG_CONDITION),
				component);

		expectedWarning = "warning";
		assertXpathEvaluatesTo(((new Integer(minSelectedRowCount2))).toString(),
				String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@min", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(((new Integer(maxSelectedRowCount2))).toString(),
				String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@max", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(((new Integer(selectedOnOther2))).toString(),
				String.format("//ui:table/ui:actions/html:%s[1]/html:%s/@other", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(expectedWarning,
				String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@type", TAG_ACTION, TAG_CONDITION), component);
		assertXpathEvaluatesTo(message2, String.format("//ui:table/ui:actions/html:%s[2]/html:%s/@message", TAG_ACTION, TAG_CONDITION), component);
	}

	@Test
	public void testDoPaintTableActionsInvisibleButton() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		WButton button1 = new WButton(TEST_ACTION_ONE);
		component.addAction(button1);

		// assertSchemaMatch(component);

		// Visible
		assertXpathExists("//ui:table/ui:actions", component);

		// Not Visible
		button1.setVisible(false);
		assertXpathNotExists("//ui:table/ui:actions", component);
	}

	@Test
	public void testDoPaintWithInvisibleColumnAndNoColumnHeaders() throws IOException, SAXException,
			XpathException {
		WTable component = new WTable();
		component.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		component.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));
		TableModel tableModel = createTableModel();
		component.setTableModel(tableModel);
		component.setVisible(true);

		final int testColIndex = 1;
		final boolean testShowColHeaders = false;
		component.getColumn(testColIndex).setVisible(false);
		component.setShowColumnHeaders(testShowColHeaders);

		assertSchemaMatch(component);

		// head hidden=true
		assertXpathEvaluatesTo(TRUE, "//ui:table/ui:thead/@hidden", component);

		// column headers - only COL1 and COL3 showing - in positions 1 and 2 respectively - only 2 cols
		assertXpathEvaluatesTo(COL1_HEADING_TEST,
				"//ui:table/ui:thead/ui:th[1]/ui:decoratedlabel/ui:labelbody",
				component);
		assertXpathEvaluatesTo(COL3_HEADING_TEST,
				"//ui:table/ui:thead/ui:th[2]/ui:decoratedlabel/ui:labelbody",
				component);
		assertXpathNotExists("//ui:table/ui:thead/ui:th[3]/ui:decoratedlabel/ui:labelbody",
				component);

		// first row - col1 and col3 from model in positions 1 and 2 respectively - only 2 cols showing
		List<Integer> row = new ArrayList<>();
		row.add(0);

		String firstName = (String) tableModel.getValueAt(row, 0);
		String entryDate = (String) tableModel.getValueAt(row, 2);

		assertXpathEvaluatesTo(firstName, "//ui:table/ui:tbody/ui:tr[1]/ui:td[1]/ui:textfield",
				component);
		assertXpathEvaluatesTo(entryDate, "//ui:table/ui:tbody/ui:tr[1]/ui:td[2]/ui:textfield",
				component);
		assertXpathNotExists("//ui:table/ui:tbody/ui:tr[1]/ui:td[3]/ui:textfield", component);
	}

	@Test
	public void testRenderedWithMargins() throws IOException, SAXException, XpathException {
		WTable table = new WTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		assertXpathNotExists("//ui:table/ui:margin", table);

		Margin margin = new Margin(null);
		table.setMargin(margin);
		assertXpathNotExists("//ui:table/ui:margin", table);

		margin = new Margin(Size.SMALL);
		table.setMargin(margin);

		assertSchemaMatch(table);

		assertXpathEvaluatesTo("sm", "//ui:table/ui:margin/@all", table);
		assertXpathEvaluatesTo("", "//ui:table/ui:margin/@north", table);
		assertXpathEvaluatesTo("", "//ui:table/ui:margin/@east", table);
		assertXpathEvaluatesTo("", "//ui:table/ui:margin/@south", table);
		assertXpathEvaluatesTo("", "//ui:table/ui:margin/@west", table);

		margin = new Margin(Size.SMALL, Size.MEDIUM, Size.LARGE, Size.XL);
		table.setMargin(margin);

		assertSchemaMatch(table);

		assertXpathEvaluatesTo("", "//ui:table/ui:margin/@all", table);
		assertXpathEvaluatesTo("sm", "//ui:table/ui:margin/@north", table);
		assertXpathEvaluatesTo("med", "//ui:table/ui:margin/@east", table);
		assertXpathEvaluatesTo("lg", "//ui:table/ui:margin/@south", table);
		assertXpathEvaluatesTo("xl", "//ui:table/ui:margin/@west", table);
	}

	@Test
	public void testDoPaintColumnFooter() throws IOException, SAXException, XpathException {

		WTable table = createTableWithColumnFooters();

		assertSchemaMatch(table);

		// Should be 3 columns
		assertXpathEvaluatesTo("3", "count(" + FOOTER_PATH + ")", table);

		assertXpathEvaluatesTo("FOOTER1", FOOTER_PATH + "[1]", table);
		assertXpathEvaluatesTo("", FOOTER_PATH + "[2]", table);
		assertXpathEvaluatesTo("FOOTER3", FOOTER_PATH + "[3]", table);
	}

	@Test
	public void testDoPaintColumnFooterExpandable() throws IOException, SAXException, XpathException {

		WTable table = createTableWithColumnFooters();

		// Make table expandable
		table.setExpandMode(ExpandMode.DYNAMIC);

		assertSchemaMatch(table);

		// Should be 4 columns (Should have extra column for expand controls)
		assertXpathEvaluatesTo("4", "count(" + FOOTER_PATH + ")", table);

		assertXpathEvaluatesTo("FOOTER1", FOOTER_PATH + "[2]", table);
		assertXpathEvaluatesTo("", FOOTER_PATH + "[3]", table);
		assertXpathEvaluatesTo("FOOTER3", FOOTER_PATH + "[4]", table);
	}

	@Test
	public void testDoPaintColumnFooterSelectable() throws IOException, SAXException, XpathException {

		WTable table = createTableWithColumnFooters();

		// Make table selectable
		table.setSelectMode(WTable.SelectMode.SINGLE);

		assertSchemaMatch(table);

		// Should be 4 columns (Should have extra column for select controls)
		assertXpathEvaluatesTo("4", "count(" + FOOTER_PATH + ")", table);

		assertXpathEvaluatesTo("FOOTER1", FOOTER_PATH + "[2]", table);
		assertXpathEvaluatesTo("", FOOTER_PATH + "[3]", table);
		assertXpathEvaluatesTo("FOOTER3", FOOTER_PATH + "[4]", table);
	}

	@Test
	public void testDoPaintColumnFooterExpandableSelectable() throws IOException, SAXException, XpathException {

		WTable table = createTableWithColumnFooters();

		// Make table expandable
		table.setExpandMode(ExpandMode.DYNAMIC);
		// Make table selectable
		table.setSelectMode(WTable.SelectMode.SINGLE);

		assertSchemaMatch(table);

		// Should be 5 columns (Should have two extra column for expand controls)
		assertXpathEvaluatesTo("5", "count(" + FOOTER_PATH + ")", table);

		assertXpathEvaluatesTo("FOOTER1", FOOTER_PATH + "[3]", table);
		assertXpathEvaluatesTo("", FOOTER_PATH + "[4]", table);
		assertXpathEvaluatesTo("FOOTER3", FOOTER_PATH + "[5]", table);
	}

	@Test
	public void testDoPaintColumnFooterNoColumns() throws IOException, SAXException, XpathException {

		// Table with columns and no footers defined
		WTable table = new WTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class));

		// Should have no footers. ie Footer path not exist
		assertXpathNotExists(FOOTER_PATH, table);

		// Turn on footers
		table.setRenderColumnFooters(true);

		assertSchemaMatch(table);

		// Should have 3 empty footers for each column
		assertXpathEvaluatesTo("3", "count(" + FOOTER_PATH + ")", table);

	}

	@Test
	public void testXssEscaping() throws IOException, SAXException, XpathException {
		WTable table = new WTable();
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

		TableModel tableModel = createTableModel();
		table.setTableModel(tableModel);
		uic.clearScratchMap(); // clear out cached data from previous renders
		assertSafeContent(table);

		table.setCaption(getMaliciousAttribute("ui:table"));
		assertSafeContent(table);

	}

	/**
	 * @return a test table model.
	 */
	private TableModel createTableModel() {
		String[][] data = new String[][]{new String[]{"Joe", "Bloggs", "01/02/1973"},
		new String[]{"Jane", "Bloggs", "04/05/1976"},
		new String[]{"Kid", "Bloggs", "31/12/1999"}};

		SimpleTableModel model = new SimpleTableModel(data);
		model.setEditable(true);

		return new AdapterBasicTableModel(model);
	}

	/**
	 * @return a test table model for sorting.
	 */
	private TableModel createTableModelSortable() {
		String[][] data = new String[][]{new String[]{"Joe2", "Bloggs2", "01/02/1971"},
		new String[]{"Jane2", "Bloggs2", "04/05/1972"},
		new String[]{"Abel2", "Bloggs2", "31/12/2000"}};

		SimpleTableModel model = new SimpleTableModel(data);

		// col 1 and 3 sortable
		model.setComparator(0, new StringComparator());
		// model.setComparator(1, new StringComparator());
		model.setComparator(2, new StringComparator());

		// sort on column1, descending - XTreeTableDataModel only
		model.sort(0, false);

		model.setEditable(true);
		return new AdapterBasicTableModel(model);
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

	/**
	 * @return a table setup with column footers
	 */
	private WTable createTableWithColumnFooters() {
		WTable table = new WTable();
		table.addColumn(new WTableColumn(COL1_HEADING_TEST, WTextField.class, new WText("FOOTER1")));
		table.addColumn(new WTableColumn(COL2_HEADING_TEST, WTextField.class));
		table.addColumn(new WTableColumn(COL3_HEADING_TEST, WTextField.class, new WText("FOOTER3")));

		// Turn on column footers
		table.setRenderColumnFooters(true);

		TableModel tableModel = createTableModel();
		table.setTableModel(tableModel);

		return table;
	}

}
