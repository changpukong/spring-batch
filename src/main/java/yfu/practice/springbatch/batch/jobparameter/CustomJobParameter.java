package yfu.practice.springbatch.batch.jobparameter;

import org.springframework.batch.core.JobParameter;

public class CustomJobParameter<T> extends JobParameter {

	private static final long serialVersionUID = -8180715291862990948L;
	
	private T customParam;
	
	public CustomJobParameter(T customParam) {
		super(customParam.toString());
		this.customParam = customParam;
	}

	@Override
	public T getValue() {
		return customParam;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((customParam == null) ? 0 : customParam.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomJobParameter other = (CustomJobParameter) obj;
		if (customParam == null) {
			if (other.customParam != null)
				return false;
		} else if (!customParam.equals(other.customParam))
			return false;
		return true;
	}
	
}