 
package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.fx.ui.di.FXMLBuilder.Data;
import org.eclipse.fx.ui.di.InjectingFXMLLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareST;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.CompareEngineView;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CompareEngineController {
	CompareEngineView view ;
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
		//view = new CompareEngineViewImpl(new FXCanvas(parent, SWT.None), services);

		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		InjectingFXMLLoader<Parent> iFXMLLoader = InjectingFXMLLoader.create(
			      context, getClass(), "/ui/test.fxml"
			    );

		
		Data<Parent, Object> fxmlData = iFXMLLoader.loadWithController();
		view = (CompareEngineView) fxmlData.getController();
		canvans.setScene(new Scene(fxmlData.getNode()));
		view.test();
		
	}
	
	
	
	
	
	/**
	 * Shows artifacts in a view
	 * @param artifacts
	 */
	@Optional
	@Inject
	public void loadArtifacts(@UIEventTopic(CompareST.LOAD_ARTIFACTS_EVENET) List<Tree> artifacts) {
		//view.showArtifacts(artifacts);
	}
}