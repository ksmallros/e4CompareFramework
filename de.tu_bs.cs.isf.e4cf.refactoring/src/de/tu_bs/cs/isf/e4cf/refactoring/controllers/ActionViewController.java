package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.views.ActionView;

@Singleton
@Creatable
public class ActionViewController extends Controller<ActionView> {

	public ActionViewController() {
		super(new ActionView());
	}

	@Override
	protected void initView() {
		view.getActionTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;
				ActionScope actionScope = (ActionScope) item.getData();

				if (event.detail == SWT.CHECK) {
					boolean checked = item.getChecked();
					actionScope.setApply(checked);

					if (item.getParentItem() != null) {
						TreeItem parent = item.getParentItem();
						parent.setChecked(checked);

						for (TreeItem child : parent.getItems()) {
							child.setChecked(checked);
						}
					} else {
						for (TreeItem child : item.getItems()) {
							child.setChecked(checked);
						}
					}

				}

				view.getComponentTree().deselectAll();

				for (TreeItem treeItem : view.getComponentTree().getItems()) {
					view.markNodeRecursively(treeItem, actionScope.getAction().getAffectedNode());
				}

			}
		});

	}

	public void showView(Map<Component, List<ActionScope>> scopes) {
		view.showView(scopes);

	}

}
