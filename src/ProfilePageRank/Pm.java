package ProfilePageRank;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	
	public List<Pm> addVm(String vmProfileStr){
		List<Pm> pmList = new ArrayList<>();
		Vm vm = new Vm(vmProfileStr);
		List<List<Integer>> posCPU = helper(vm.getnCores(), this.getnCores());
		List<List<Integer>> posDisk = helper(vm.getnDisks(), this.getnDisks());
		
		// check memory
		if(this.getUsedMem()+vm.getMemory()>this.getMemory()) return pmList;
		
		for(List<Integer> pC : posCPU){
			// check cpu validation
			boolean cpuValid = true;
			for(int i=0; i<this.getnCores(); i++){ // for every PM core
				if(this.usedCPU[i]+vm.getvCPUs() > this.vCPUs) {
					cpuValid = false;
					break;
				}
			}
			if(!cpuValid) continue;
			
			for(List<Integer> pD : posDisk){
				// check disk validation
				boolean diskValid = true;
				for(int i=0; i<this.getnDisks(); i++){ // for every PM disk
					if(this.usedDisk[i]+vm.getnDisks() > this.nDisks) {
						diskValid = false;
						break;
					}
				}
				if(!diskValid) continue;
				
				// if code arrives here, pm is feasible to hold this vm
				Pm newPm = new Pm()
			}
		}
		return null;
	}

	public Queue<String> getNewProfiles() {
		// TODO Auto-generated method stub
		Queue<String> newProfiles = new LinkedList<>();
		for(String vm : Constants.VMs){
			
		}
		return null;
	}

	public String getUsedProfile() {
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
		result.addAll(part2);
		return result;
	}
}
