
package com.company.graphparttransfer.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.company.graphparttransfer.func.storage.DB;
import com.company.graphparttransfer.func.transfer.Export;
import com.company.graphparttransfer.model.Client;
import com.company.graphparttransfer.model.client.Category;
import com.company.graphparttransfer.model.client.Product;
import com.rapidclipse.framework.server.resources.CaptionUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.StreamResource;

import elemental.json.Json;


@Route("")
public class MainLayout extends VerticalLayout implements PageConfigurator
{
	Pair<String, byte[]> exportData;

	private final MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
	
	public MainLayout()
	{
		super();
		this.initUI();
		
		this.grid.setItems(DB.root1().getClients());
		this.grid2.setItems(DB.root2().getClients());
		
		this.upload.setReceiver(this.buffer);

		this.upload.addFinishedListener(e -> {
			System.out.println("Upload finished");
			// final BinaryElement file = new BinaryElement(
			// this.multiFileMemoryBuffer.getOutputBuffer(e.getFileName()).toByteArray(),
			// e.getFileName(),
			// e.getMIMEType());
			//
			// this.pendingFiles.add(file);
			// this.addItem(file);
			//
			// this.save();
			
			// Kills the FileList of the Vaadin uploader.
			this.upload.getElement().setPropertyJson("files", Json.createArray());
		});
	}
	
	@Override
	public void configurePage(final InitialPageSettings settings)
	{
		settings.addLink("shortcut icon", "frontend/images/favicon.ico");
		settings.addFavIcon("icon", "frontend/images/favicon256.png", "256x256");
	}
	
	/**
	 * Event handler delegate method for the {@link Button} {@link #btnExport}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void btnExport_onClick(final ClickEvent<Button> event)
	{
		this.objExportAndDownload.removeAll();
		this.objExportAndDownload.add(this.btnExport);
		
		final Optional<Client> client = this.grid.getSelectionModel().getFirstSelectedItem();
		
		if(client.isPresent())
		{

			try
			{
				this.exportData = Export.export(client.get());
				
				final StreamResource resource =
					new StreamResource(
						this.exportData.getLeft(),
						() -> new ByteArrayInputStream(this.exportData.getRight()));

				final Anchor download = new Anchor(resource, this.exportData.getLeft());
				download.getElement().setAttribute("download", true);
				
				this.objExportAndDownload.add(download);
			}
			catch(final IOException e)
			{

				e.printStackTrace();
			}
		}
		else
		{

		}

	}
	
	/**
	 * Event handler delegate method for the {@link Grid} {@link #grid}.
	 *
	 * @see SelectionListener#selectionChange(SelectionEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void grid_selectionChange(final SelectionEvent<Grid<Client>, Client> event)
	{
		final Optional<Client> firstSelectedItem = event.getFirstSelectedItem();

		this.gridProducts.setItems(firstSelectedItem.map(Client::getProducts).orElse(Collections.emptySet()));

	}
	
	/**
	 * Event handler delegate method for the {@link Grid} {@link #grid2}.
	 *
	 * @see SelectionListener#selectionChange(SelectionEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void grid2_selectionChange(final SelectionEvent<Grid<Client>, Client> event)
	{
		final Optional<Client> firstSelectedItem = event.getFirstSelectedItem();

		this.gridProducts2.setItems(firstSelectedItem.map(Client::getProducts).orElse(Collections.emptySet()));
	}
	
	/* WARNING: Do NOT edit!<br>The content of this method is always regenerated by the UI designer. */
	// <generated-code name="initUI">
	private void initUI()
	{
		this.objHeader            = new HorizontalLayout();
		this.h2                   = new H2();
		this.objContent           = new SplitLayout();
		this.mainLeft             = new VerticalLayout();
		this.h3                   = new H3();
		this.objExportAndDownload = new Div();
		this.btnExport            = new Button();
		this.grid                 = new Grid<>(Client.class, false);
		this.gridProducts         = new Grid<>(Product.class, false);
		this.mainRight            = new VerticalLayout();
		this.h32                  = new H3();
		this.upload               = new Upload();
		this.grid2                = new Grid<>(Client.class, false);
		this.gridProducts2        = new Grid<>(Product.class, false);

		this.setSpacing(false);
		this.setPadding(false);
		this.objHeader.setPadding(true);
		this.h2.setText("Export and import parts of a storage ");
		this.h2.getStyle().set("margin-top", "unset");
		this.mainLeft.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
		this.h3.setText("Storage 1: Export one of the clients");
		this.btnExport.setText("Export");
		this.grid.addThemeVariants(GridVariant.LUMO_COMPACT);
		this.grid.getStyle().set("flex-basis", "0");
		this.grid.addColumn(Client::getName).setKey("name").setHeader(CaptionUtils.resolveCaption(Client.class, "name"))
			.setSortable(true);
		this.grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		this.gridProducts.addThemeVariants(GridVariant.LUMO_COMPACT);
		this.gridProducts.getStyle().set("flex-basis", "0");
		this.gridProducts.addColumn(Product::getName).setKey("name")
			.setHeader(CaptionUtils.resolveCaption(Product.class, "name")).setResizable(true).setSortable(true);
		this.gridProducts.addColumn(Product::getDescription).setKey("description")
			.setHeader(CaptionUtils.resolveCaption(Product.class, "description")).setResizable(true).setSortable(true);
		this.gridProducts
			.addColumn(v -> Optional.ofNullable(v).map(Product::getCategory).map(Category::getName).orElse(null))
			.setKey("category.name").setHeader(CaptionUtils.resolveCaption(Product.class, "category.name"))
			.setResizable(true).setSortable(true);
		this.gridProducts.setSelectionMode(Grid.SelectionMode.SINGLE);
		this.mainRight.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
		this.h32.setText("Storage 2: Import a client");
		this.grid2.addThemeVariants(GridVariant.LUMO_COMPACT);
		this.grid2.getStyle().set("flex-basis", "0");
		this.grid2.addColumn(Client::getName).setKey("name")
			.setHeader(CaptionUtils.resolveCaption(Client.class, "name"))
			.setSortable(true);
		this.grid2.setSelectionMode(Grid.SelectionMode.SINGLE);
		this.gridProducts2.addThemeVariants(GridVariant.LUMO_COMPACT);
		this.gridProducts2.getStyle().set("flex-basis", "0");
		this.gridProducts2.addColumn(Product::getName).setKey("name")
			.setHeader(CaptionUtils.resolveCaption(Product.class, "name")).setResizable(true).setSortable(true);
		this.gridProducts2.addColumn(Product::getDescription).setKey("description")
			.setHeader(CaptionUtils.resolveCaption(Product.class, "description")).setResizable(true).setSortable(true);
		this.gridProducts2
			.addColumn(v -> Optional.ofNullable(v).map(Product::getCategory).map(Category::getName).orElse(null))
			.setKey("category.name").setHeader(CaptionUtils.resolveCaption(Product.class, "category.name"))
			.setResizable(true).setSortable(true);
		this.gridProducts2.setSelectionMode(Grid.SelectionMode.SINGLE);

		this.h2.setSizeUndefined();
		this.objHeader.add(this.h2);
		this.btnExport.setSizeUndefined();
		this.objExportAndDownload.add(this.btnExport);
		this.h3.setSizeUndefined();
		this.objExportAndDownload.setSizeUndefined();
		this.grid.setWidth(null);
		this.grid.setHeight("150px");
		this.gridProducts.setWidthFull();
		this.gridProducts.setHeight("150px");
		this.mainLeft.add(this.h3, this.objExportAndDownload, this.grid, this.gridProducts);
		this.mainLeft.setFlexGrow(1.0, this.gridProducts);
		this.h32.setSizeUndefined();
		this.upload.setSizeUndefined();
		this.grid2.setWidthFull();
		this.grid2.setHeight("150px");
		this.gridProducts2.setWidthFull();
		this.gridProducts2.setHeight("150px");
		this.mainRight.add(this.h32, this.upload, this.grid2, this.gridProducts2);
		this.mainRight.setFlexGrow(1.0, this.grid2);
		this.mainRight.setFlexGrow(1.0, this.gridProducts2);
		this.objContent.addToPrimary(this.mainLeft);
		this.objContent.addToSecondary(this.mainRight);
		this.objContent.setSplitterPosition(50.0);
		this.objHeader.setSizeUndefined();
		this.objContent.setSizeFull();
		this.add(this.objHeader, this.objContent);
		this.setFlexGrow(1.0, this.objContent);
		this.setSizeFull();

		this.btnExport.addClickListener(this::btnExport_onClick);
		this.grid.addSelectionListener(this::grid_selectionChange);
		this.grid2.addSelectionListener(this::grid2_selectionChange);
	} // </generated-code>
	
	// <generated-code name="variables">
	private Grid<Product>    gridProducts, gridProducts2;
	private Button           btnExport;
	private SplitLayout      objContent;
	private Upload           upload;
	private HorizontalLayout objHeader;
	private VerticalLayout   mainLeft, mainRight;
	private Div              objExportAndDownload;
	private Grid<Client>     grid, grid2;
	private H3               h3, h32;
	private H2               h2;
	// </generated-code>
	
}
