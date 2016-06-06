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
	
	public List<Pm> addVm(String p){
		List<Pm> pmList = new ArrayList<>();
		Vm vm = new Vm(p);
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
}
