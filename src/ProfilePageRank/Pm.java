package ProfilePageRank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Pm {
	int nCores;
	int vCPUs; // vCPU per core
	double memory;
	int nDisks;
	int dCap; // capacity per disk
	
	int[] usedCPU;
	double usedMem;
	int[] usedDisk;
	
	double minMem = 3.75; // depends on minimum memory request from VM instances
	int minDisk = 4;
	
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

	public int getdCap() {
		return dCap;
	}

	public void setdCap(int dCap) {
		this.dCap = dCap;
	}

	public double getUsedMem() {
		return usedMem;
	}

	public void setUsedMem(double usedMem) {
		this.usedMem = usedMem;
	}

	public double getMinMem() {
		return minMem;
	}

	public void setMinMem(double minMem) {
		this.minMem = minMem;
	}

	public int getMinDisk() {
		return minDisk;
	}

	public void setMinDisk(int minDisk) {
		this.minDisk = minDisk;
	}

	public int getnCores() {
		return nCores;
	}

	public void setnCores(int nCores) {
		this.nCores = nCores;
	}

	public int getnDisks() {
		return nDisks;
	}

	public void setnDisks(int nDisks) {
		this.nDisks = nDisks;
	}

	public int[] getUsedCPU() {
		return usedCPU;
	}

	public void setUsedCPU(int[] usedCPU) {
		this.usedCPU = usedCPU;
	}

	public int[] getUsedDisk() {
		return usedDisk;
	}

	public void setUsedDisk(int[] usedDisk) {
		this.usedDisk = usedDisk;
	}

	public Pm(int nCores, int vCPUs, double memory, int nDisks, int dCap) {
		super();
		this.nCores = nCores;
		this.vCPUs = vCPUs;
		this.memory = memory;
		this.nDisks = nDisks;
		this.dCap = dCap;
		
		usedCPU = new int[nCores];
		usedDisk = new int[nDisks];
	}
	
	public Pm(String used, String cap){
		String[] elementsUsed = used.split(":");
		String[] elementsCap = cap.split(":");
		
		usedMem = Double.valueOf(elementsUsed[1]);
		memory = Double.valueOf(elementsCap[1]);
		
		String[] cpuUsed = elementsUsed[0].split(",");
		String[] cpuCap = elementsCap[0].split(",");
		usedCPU = new int[Integer.valueOf(cpuUsed.length)];
		for(int i=0; i<cpuUsed.length; i++) usedCPU[i] = Integer.valueOf(cpuUsed[i]);
		nCores = Integer.valueOf(cpuCap.length);
		vCPUs = Integer.valueOf(cpuCap[0]);
		
		String[] diskUsed = elementsUsed[2].split(",");
		String[] diskCap = elementsCap[2].split(",");
		usedDisk = new int[Integer.valueOf(diskUsed.length)];
		for(int i=0; i<diskUsed.length; i++) usedDisk[i] = Integer.valueOf(diskUsed[i]);
		nDisks = Integer.valueOf(diskCap.length);
		dCap = Integer.valueOf(diskCap[0]);
		
	}

	public Pm(String cap) {
		// TODO Auto-generated constructor stub
		String[] elementsCap = cap.split(":");
		
		usedMem = 0;
		memory = Double.valueOf(elementsCap[1]);
		
		String[] cpuCap = elementsCap[0].split(",");
		usedCPU = new int[Integer.valueOf(cpuCap.length)];
		nCores = Integer.valueOf(cpuCap.length);
		vCPUs = Integer.valueOf(cpuCap[0]);
		
		String[] diskCap = elementsCap[2].split(",");
		usedDisk = new int[Integer.valueOf(diskCap.length)];
		nDisks = Integer.valueOf(diskCap.length);
		dCap = Integer.valueOf(diskCap[0]);
	}
	
	public Pm(int[] newUsedCPU, double newUsedMem, int[] newUsedDisk,
			int getnCores, int getvCPUs, double memory2, int getnDisks,
			int getdCap) {
		// TODO Auto-generated constructor stub
		this.usedCPU = new int[newUsedCPU.length];
		for(int i=0; i<newUsedCPU.length; i++) this.usedCPU[i] = newUsedCPU[i];
		this.usedMem = newUsedMem;
		this.usedDisk = new int[newUsedDisk.length];
		for(int i=0; i<newUsedDisk.length; i++) this.usedDisk[i] = newUsedDisk[i];
		
		this.nCores = getnCores;
		this.vCPUs = getvCPUs;
		this.memory = memory2;
		this.nDisks = getnDisks;
		this.dCap = getdCap;
	}

	public List<Pm> addVm(String vmProfileStr){
		List<Pm> pmList = new ArrayList<>();
		Vm vm = new Vm(vmProfileStr);
		List<List<Integer>> posCPU = helper(vm.getnCores(), this.getnCores());
		List<List<Integer>> posDisk = helper(vm.getnDisks(), this.getnDisks());
			
		double newUsedMem;
		
		// check memory
		newUsedMem = this.getUsedMem()+vm.getMemory();
		if(newUsedMem>this.getMemory()) return pmList;
		
		for(List<Integer> pC : posCPU){
			int[] newUsedCPU = new int[this.getnCores()];
			// check cpu validation
			boolean cpuValid = true;
			for(int i=0; i<this.getnCores(); i++){ // for every PM core
				if(i!=pC.get(0) && i!=pC.get(1)) continue; // if PM core is not selected
				newUsedCPU[i] = this.usedCPU[i]+vm.getvCPUs();
				if(newUsedCPU[i] > this.vCPUs) {
					cpuValid = false;
					break;
				}
			}
			if(!cpuValid) continue;
			
			for(List<Integer> pD : posDisk){
				int[] newUsedDisk = new int[this.getnDisks()];
				// check disk validation
				boolean diskValid = true;
				for(int i=0; i<this.getnDisks(); i++){ // for every PM disk
					if(i!=pD.get(0) && i!=pD.get(1)) continue; // if PM disk is not selected
					newUsedDisk[i] = this.usedDisk[i]+vm.getdCap();
					if(newUsedDisk[i] > this.dCap) {
						diskValid = false;
						break;
					}
				}
				if(!diskValid) continue;
				
				// if code arrives here, pm is feasible to hold this vm
				Pm newPm = new Pm(newUsedCPU, 
						newUsedMem, 
						newUsedDisk, 
						this.getnCores(), 
						this.getvCPUs(), 
						this.getMemory(), 
						this.getnDisks(), 
						this.getdCap());
				pmList.add(newPm);
			}	
		}
		return pmList;
	}

	public Set<String> getNewProfiles() {
		// TODO Auto-generated method stub
		Set<String> newProfiles = new HashSet<>();
		for(String vm : Constants.VMs){ // add every VM type
			for(Pm pm : this.addVm(vm)){ // for each VM type, iterate all possible addition (i.e, which core holds vCPU, which disk holds vDisk)
				newProfiles.add(pm.getUsedProfile());
			}
		}
		return newProfiles;
	}

	public String getUsedProfile() {
		// TODO Auto-generated method stub
		String sb = "";
		String mark = "";
		for(int u : this.usedCPU){
			sb += mark;
			mark = ",";
			sb += u;			
		}
		
		sb += ':';
		sb += this.usedMem;
		sb += ':';
		
		mark = "";
		for(int u : this.usedDisk){
			sb += mark;
			mark = ",";
			sb += u;
		}
		return sb;
	}
	
	/*public String getUsedProfile() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for(int u : this.usedCPU){
			sb.append(u);
			sb.append(",");
		}
		
		sb.setCharAt(sb.length()-1, ':');
		sb.append(this.usedMem);
		sb.append(":");
		
		for(int u : this.usedDisk){
			sb.append(u);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}*/
	
	public String getCapString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<this.nCores; i++){
			sb.append(this.vCPUs);
			sb.append(",");
		}
		
		sb.setCharAt(sb.length()-1, ':');
		sb.append(this.memory);
		sb.append(":");
		
		for(int i=0; i<this.nDisks; i++){
			sb.append(this.dCap);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public List<List<Integer>> helper(int k, int n){
		List<List<Integer>> result = new ArrayList<>();
		if(k==0){
			result.add(new ArrayList<Integer>());
			return result;
		}
		if(k>n) return result;
		
		List<List<Integer>> part1 = helper(k-1, n-1);
		List<List<Integer>> part2 = helper(k, n-1);
		result.addAll(part2);
		for(List<Integer> list : part1){
			list.add(n-1);
		}
		result.addAll(part1);
		return result;
	}
}
