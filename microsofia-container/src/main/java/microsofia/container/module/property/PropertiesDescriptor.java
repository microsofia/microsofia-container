package microsofia.container.module.property;

import java.util.HashMap;
import java.util.Map;

public class PropertiesDescriptor {
	private Map<String,PropertyDescriptor> propertiesDescriptor;
	
	public PropertiesDescriptor(){
		setPropertiesDescriptor(new HashMap<String,PropertyDescriptor>());
	}

	public void addPropertyDescriptor(PropertyDescriptor d){
		propertiesDescriptor.put(d.getName(), d);
	}
	
	public Map<String,PropertyDescriptor> getPropertiesDescriptor() {
		return propertiesDescriptor;
	}

	public void setPropertiesDescriptor(Map<String,PropertyDescriptor> propertiesDescriptor) {
		this.propertiesDescriptor = propertiesDescriptor;
	}
}
