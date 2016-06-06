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
	
	Map<String,List<String>> profileGraph;
	
	public void buildGraph(){
		Queue<String> oldProfiles = new LinkedList<>();
		oldProfiles.add((new Pm(Constants.PM).getUsedProfile()));
		while(oldProfiles!=null){
			Queue<String> newProfiles = new LinkedList<>();
			for(String profile : oldProfiles){
				if(profileGraph.containsKey(profile)) continue;
				Pm pm = new Pm(profile,Constants.PM);
				
				List<String> list = new LinkedList<>();
				list.addAll(pm.getNewProfiles());
				profileGraph.put(profile, list);
			
				newProfiles.addAll(list);
			}
			oldProfiles = newProfiles;
		}
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
