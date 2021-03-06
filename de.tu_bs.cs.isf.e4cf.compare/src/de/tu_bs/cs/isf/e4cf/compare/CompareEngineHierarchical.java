package de.tu_bs.cs.isf.e4cf.compare;

import java.util.Iterator;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.StringComparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.ICompareEngine;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;

/**
 * Decomposes two trees and compares , match and merges them hierarchical which
 * decomposes the Node bottom/up.
 * 
 * @author NoLimit
 *
 */
public class CompareEngineHierarchical implements ICompareEngine<Node> {
	private StringComparator defaultComparator = new StringComparator();
	private Metric metric;
	private Matcher matcher;

	public CompareEngineHierarchical(Matcher selectedMatcher, Metric selectedMetric) {
		this.metric = selectedMetric;
		this.matcher = selectedMatcher;
	}

	@Override
	public Tree compare(Tree first, Tree second) {
		try {
			// compare
			NodeComparison root = compare(first.getRoot(), second.getRoot());
			// match
			root.updateSimilarity();
			getMatcher().calculateMatching(root);
			root.updateSimilarity();
			// Merge
			Node mergedRoot = root.mergeArtifacts();

			return new TreeImpl(first, second, mergedRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tree compare(List<Tree> variants) {
		Iterator<Tree> variantIterator = variants.iterator();
		Tree mergedTree = null;
		for (Tree variant : variants) {
			// first variant
			if (mergedTree == null) {
				mergedTree = variantIterator.next();
			} else {
				mergedTree = compare(mergedTree, variant);
			}
		}
		return mergedTree;
	}

	@Override
	public NodeComparison compare(Node first, Node second) {
		NodeComparison comparison = new NodeComparison(first, second);
		// if nodes are of the same type
		if (first.getNodeType().equals(second.getNodeType())) {
			List<Comparator> comparators = metric.getComparatorForNodeType(first.getNodeType());
			// check if the metric contains attribute for the comparison of this node type
			if (!comparators.isEmpty()) {
				for (Comparator comparator : comparators) {
					comparison.addResultElement(comparator.compare(first, second));
				}
			} else {
				comparison.addResultElement(defaultComparator.compare(first, second));
			}
			// if no children available the recursion ends here
			if (first.getChildren().isEmpty() && second.getChildren().isEmpty()) {
				return comparison;
			} else {
				// if one of both has no children the other elements are optional
				if (first.getChildren().isEmpty() || second.getChildren().isEmpty()) {
					first.getChildren().stream()
							.forEach(e -> comparison.addChildComparison(new NodeComparison(e, null, 0f)));
					second.getChildren().stream()
							.forEach(e -> comparison.addChildComparison(new NodeComparison(null, e, 0f)));
				} else {
					// compare children recursively
					first.getChildren().stream().forEach(e -> {
						second.getChildren().stream().forEach(f -> {
							NodeComparison innerComp = compare(e, f);
							if (innerComp != null) {
								comparison.addChildComparison(innerComp);
							}
						});
					});
				}
			}
			return comparison;
		} else {
			return comparison;
		}
	}

	@Override
	public Comparator getDefaultComparator() {
		return defaultComparator;
	}

	@Override
	public Metric getMetric() {
		return this.metric;
	}

	@Override
	public Matcher getMatcher() {
		return this.matcher;
	}

}
