package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAttribute implements Attribute {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attributeKey;
	private List<Value> attributeValues;

	public AbstractAttribute() {
		setAttributeValues(new ArrayList<Value>());
	}

	@Override
	public float compare(Attribute attr) {
		float similarity = 0f;
		// checks if the keys are equals
		if (attr.getAttributeKey().equals(getAttributeKey())) {
			similarity += 0.25f;
		}
		value: for (Value first_value : attr.getAttributeValues()) {
			for (Value second_value : getAttributeValues()) {
				if (first_value.equals(second_value)) {
					similarity += 0.75f;
					break value;
				}
			}
		}
		return similarity;
	}
	
	@Override
	public void addAttributeValues(List<Value> value) {
		attributeValues.addAll(value);	
	}
	
	@Override
	public String getAttributeKey() {
		return attributeKey;
	}

	@Override
	public List<Value> getAttributeValues() {
		return attributeValues;
	}

	@Override
	public void addAttributeValue(Value value) {
		attributeValues.add(value);
	}

	public void setAttributeValues(List<Value> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
}
