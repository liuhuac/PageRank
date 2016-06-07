package ProfilePageRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PageRank {

	/**
	 * @param args
	 */
	
	Map<String,List<Double>> pageRank = new HashMap<>();
	
	Map<String,List<String>> profileGraph = new HashMap<>();
	
	public void buildGraph(){
		Queue<String> oldProfiles = new LinkedList<>();
		oldProfiles.add((new Pm(Constants.PM).getUsedProfile()));
		while(oldProfiles.size()!=0){
			printInfo(true, "Debug: oldProfiles.size()="+oldProfiles.size(), Constants.debugLevel);
			Queue<String> newProfiles = new LinkedList<>();
			for(String profile : oldProfiles){
				
				if(profileGraph.containsKey(profile)) {
					//printInfo(true, "Debug: profile="+profile, Constants.debugLevel);
					continue;
				}
				Pm pm = new Pm(profile,Constants.PM);
				
				List<String> list = new LinkedList<>();
				List<String> listForGraph = new LinkedList<>();
				list.addAll(pm.getNewProfiles());
				listForGraph.addAll(list);
				profileGraph.put(profile, listForGraph);
			
				newProfiles.addAll(list);
			}
			oldProfiles = newProfiles;
		}
	}
	
	private void printInfo(boolean b, String info, int debugLevel) {
		// TODO Auto-generated method stub
		if(!b) return;
		if(Constants.debugLevel==0) return;
		System.out.println(info);
	}

	public void initMap(){
		int N = profileGraph.size();
		for(String profile : profileGraph.keySet()){
			List<Double> tmp = new ArrayList<>();
			tmp.add(1.0/N);
			tmp.add(0.0);
			pageRank.put(profile, tmp);
		}		
	}
	
	public void print(){
		System.out.println(this.profileGraph.size());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PageRank pr = new PageRank();
		pr.buildGraph();
		pr.initMap();
		pr.print();
	}

}
