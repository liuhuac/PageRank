package ProfilePageRank;

public class Vm {
	int nCores;
	int vCPUs; // vCPU per core
	double memory;
	int nDisks;
	int dCap; // capacity per disk
	
	public Vm(int nCores, int vCPUs, double memory, int nDisks, int dCap) {
		super();
		this.nCores = nCores;
		this.vCPUs = vCPUs;
		this.memory = memory;
		this.nDisks = nDisks;
		this.dCap = dCap;
	}
	
	public Vm(String cap){
		String[] elementsCap = cap.split(":");
		
		memory = Double.valueOf(elementsCap[1]);
		
		String[] cpuCap = elementsCap[0].split(",");
		nCores = Integer.valueOf(cpuCap.length);
		vCPUs = Integer.valueOf(cpuCap[0]);
		
		String[] diskCap = elementsCap[2].split(",");
		nDisks = Integer.valueOf(diskCap.length);
		dCap = Integer.valueOf(diskCap[0]);
		
	}
}
