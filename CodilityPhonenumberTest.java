package test2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

public class CodilityPhonenumberTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data="00:01:07,400-234-090\n" + 
					"00:05:01,701-080-080\n" + 
					"00:05:00,400-234-090";
		Map<String,ArrayList<String>>phonenumberDurations = new HashMap<String, ArrayList<String>>();
		
		HashSet<String>phonenumbers = new HashSet<String>();
		String[] phonetimearr = data.split("\n");
		/*for(String phone:phonetime) {
			System.out.println(phone);
		}*/
		for(String phonetime:phonetimearr) {
			String phonenumber=phonetime.split(",")[1];
			//System.out.println(phonenumber);
			phonenumbers.add(phonenumber);
		}
		for(String phonenumber:phonenumbers) {
			ArrayList<String>durations=new ArrayList<>();
			for(String phonetime:phonetimearr) {
				if(phonetime.contains(phonenumber)){
					durations.add(phonetime.split(",")[0]);
				}
			}
			phonenumberDurations.put(phonenumber, durations);
		}
		System.out.println(phonenumberDurations);
		
		// total durations-phonenumber
		HashMap<String, Long>totaldurationsMap = new HashMap<>();
		for(String phonenumber:phonenumbers) {
			ArrayList<String>durations= phonenumberDurations.get(phonenumber);
			long totaldurss=0L;
			for(String duration:durations) {
				String[] timearr= duration.split(":");
				int hh= Integer.parseInt(timearr[0]);
				int mm= Integer.parseInt(timearr[1]);
				int ss= Integer.parseInt(timearr[2]);
				totaldurss+= hh*60*60+mm*60+ss;				
			}
			totaldurationsMap.put(phonenumber, totaldurss);
		}
		System.out.println(totaldurationsMap);
		
		//total cost-based on tarif
		HashMap<String, Long>totalcostMap=new HashMap<>();
		for(String phonenumber:phonenumbers) {
			ArrayList<String>durations= phonenumberDurations.get(phonenumber);
			long totalcost=0L;
			for(String duration:durations) {
				String[] timearr= duration.split(":");
				int hh= Integer.parseInt(timearr[0]);
				int mm= Integer.parseInt(timearr[1]);
				int ss= Integer.parseInt(timearr[2]);
				long totaldurss= hh*60*60+mm*60+ss;	
				if(totaldurss<300) {
					totalcost+=totaldurss*3;
				}else if(totaldurss>=300) {
					long totaldurmm= hh*60+mm;
					if(ss>0)
						totaldurmm+=1;
					totalcost+=totaldurmm*150;
				}
			}
			totalcostMap.put(phonenumber, totalcost);
		}
		System.out.println(totalcostMap);
		//apply promotion start
		long maxduration=Collections.max(totaldurationsMap.values());
		String offerphonenumber="";
		ArrayList<String>offerphonenumberList= new ArrayList<>();
		/*for(String phonenumber:totaldurationsMap.keySet()) {
			maxduration=(totaldurationsMap.get(phonenumber)>maxduration)?totaldurationsMap.get(phonenumber):maxduration;				
		}*/
		for(Entry<String,Long> entry:totaldurationsMap.entrySet()) {
			if(entry.getValue()==maxduration) {
				//offerphonenumber=entry.getKey();
				offerphonenumberList.add(entry.getKey());
			}
		}
		if(offerphonenumberList.size()>1) {
			offerphonenumber=offerphonenumberList.get(0);
			for(String number:offerphonenumberList) {
				String[] numberarr=number.split("-");
				String [] offernumberarr= offerphonenumber.split("-");
				if(Integer.parseInt(numberarr[0])>Integer.parseInt(offernumberarr[0]))
					offerphonenumber=number;
				else if(Integer.parseInt(numberarr[1])>Integer.parseInt(offernumberarr[1]))
					offerphonenumber=number;
				else if(Integer.parseInt(numberarr[2])>Integer.parseInt(offernumberarr[2]))
					offerphonenumber=number;
			}
		}else {
			offerphonenumber=offerphonenumberList.get(0);
		}
		System.out.println("offer phone number "+offerphonenumber);
		
		totalcostMap.remove(offerphonenumber);
		//apply promotion end
		long billamound= 0L;
		for(Entry<String,Long> entry:totalcostMap.entrySet()) {
			billamound+=entry.getValue();
		}
		System.out.println("total bill: " +billamound);
		
	}

}
