package cn.shaviation.mymaven.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search {

	public static List<Record> recordList = new ArrayList<>();

	public static void main(String[] args) {
		setList();
		System.out.println("----------原数据：----------");
		printList(recordList);
		System.out.println("----------查找结果：---------");
		List<Record> list = searchList(recordList);
		Collections.sort(list, new Comparator<Record>() {
			@Override
			public int compare(Record o1, Record o2) {

				if (o1.getId() > o2.getId()) {
					return 1;
				}else if(o1.getId() == o2.getId()){
					return 0;
				}else {
					return -1;
				}
				
			}
		});
		
		printList(list);
	}

	public static List<Record> searchList(List<Record> recordList) {
		Map<Integer, Record> map = new HashMap<Integer, Record>();
		for (Record record : recordList) {
			if (map.get(record.getuId()) == null) {
				map.put(record.getuId(), record);
			} else {
				Record oldRecord = map.get(record.getuId());
				if (record.getuSupport() > oldRecord.getuSupport()) {
					map.put(record.getuId(), record);
				}
			}
		}

		return new ArrayList<Record>(map.values());
	}

	public static void printList(List<Record> recordList) {

		for (Record record : recordList) {
			System.out.println("编号：" + record.getId() + "\t用户名："
					+ record.getuName() + "\t支持数：" + record.getuSupport()
					+ "\t用户编号：" + record.getuId());
		}
	}

	public static void setList() {
		// 1:李瑞鹏 2：姜琳 3：姜永刚 4：张楠 5：马丁 6：丁伟 7：刘庆良 8：沈伟
		Record r1 = new Record();
		r1.setId(1);
		r1.setuId(1);
		r1.setuName("李瑞鹏");
		r1.setuSupport(2);
		recordList.add(r1);

		Record r2 = new Record();
		r2.setId(2);
		r2.setuId(2);
		r2.setuName("姜琳");
		r2.setuSupport(5);
		recordList.add(r2);

		Record r3 = new Record();
		r3.setId(3);
		r3.setuId(3);
		r3.setuName("姜永刚");
		r3.setuSupport(5);
		recordList.add(r3);

		Record r4 = new Record();
		r4.setId(4);
		r4.setuId(3);
		r4.setuName("姜永刚");
		r4.setuSupport(4);
		recordList.add(r4);

		Record r5 = new Record();
		r5.setId(5);
		r5.setuId(1);
		r5.setuName("李瑞鹏");
		r5.setuSupport(4);
		recordList.add(r5);
	}

}
