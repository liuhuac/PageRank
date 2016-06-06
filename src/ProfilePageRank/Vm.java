package ProfilePageRank;

public class Vm {
	int nCores;
	int vCPUs; // vCPU per core
	double memory;
	int nDisks;
	int dCap; // capacity per disk
	
	
	
	public int getnCores() {
		return nCores;
	}

	public void setnCores(int nCores) {
		this.nCores = nCores;
	}

	public int getvCPUs() {
		return vCPUs;
	}

	public void setvCPUs(int vCPUs) {
		this.vCPUs = vCPUs;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public int getnDisks() {
		return nDisks;
	}

	public void setnDisks(int nDisks) {
		this.nDisks = nDisks;
	}

	public int getdCap() {
		return dCap;
	}

	public void setdCap(int dCap) {
		this.dCap = dCap;
	}

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
