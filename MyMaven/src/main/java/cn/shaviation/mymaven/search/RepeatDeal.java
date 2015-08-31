package cn.shaviation.mymaven.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RepeatDeal {

	public static List<Record> oldList = new ArrayList<>();
	public static List<Record> newList = new ArrayList<>();

	public static void main(String[] args) {
		setOldList();
		System.out.println("----------oldList----------");
		printList(oldList);
		setNewList();
		System.out.println("----------newList----------");
		printList(newList);
		repeatDeal(newList, oldList);

//		Set<Record> set = new HashSet<>();
		contain(newList, oldList);
	}

	public static void contain(List<Record> newList, List<Record> oldList) {
		List<String> old = new LinkedList<>();
		List<String> added = new LinkedList<>();
		for (Record record : oldList) {
			old.add(record.getuName());
		}
		for (Record record : newList) {
			added.add(record.getuName());
		}
		
		if (old.containsAll(added)) {
			System.out.println("数据重复！");
		}
	}

	public static void repeatDeal(List<Record> newList, List<Record> oldList) {
		List<String> uNameList = new LinkedList<>();
		List<String> tempList = new LinkedList<>();
		for (Record record : oldList) {
			uNameList.add(record.getuName());
		}

		for (Record record : newList) {
			// 判断新添加的数据是否已在旧数据中存在
			// System.out.println("判断新添加的数据是否已在旧数据中存在:");
			if (uNameList.contains(record.getuName())) {
				System.out.println("用户名<" + record.getuName() + ">在数据库中已存在！");
			}

			// 判断新添加的数据中是否重复
			// System.out.println("判断新添加的数据中是否重复:");
			if (tempList.contains(record.getuName())) {
				System.out.println("新添加的数据中用户名<" + record.getuName() + ">重复！");
			} else {
				tempList.add(record.getuName());
			}

		}

	}

	public static void printList(List<Record> recordList) {

		for (Record record : recordList) {
			System.out.println("编号：" + record.getId() + "\t用户名："
					+ record.getuName() + "\t支持数：" + record.getuSupport()
					+ "\t用户编号：" + record.getuId());
		}
	}

	public static void setNewList() {
		// 1:李瑞鹏 2：姜琳 3：姜永刚 4：张楠 5：马丁 6：丁伟 7：刘庆良 8：沈伟
		Record r1 = new Record();
		r1.setId(1);
		r1.setuId(1);
		r1.setuName("李瑞鹏");
		r1.setuSupport(2);
		newList.add(r1);

		Record r2 = new Record();
		r2.setId(2);
		r2.setuId(2);
		r2.setuName("姜琳");
		r2.setuSupport(5);
		newList.add(r2);

		Record r3 = new Record();
		r3.setId(3);
		r3.setuId(1);
		r3.setuName("李瑞鹏");
		r3.setuSupport(5);
		newList.add(r3);
	}

	public static void setOldList() {
		// 1:李瑞鹏 2：姜琳 3：姜永刚 4：张楠 5：马丁 6：丁伟 7：刘庆良 8：沈伟
		Record r1 = new Record();
		r1.setId(1);
		r1.setuId(1);
		r1.setuName("李瑞鹏");
		r1.setuSupport(2);
		oldList.add(r1);

		Record r2 = new Record();
		r2.setId(2);
		r2.setuId(2);
		r2.setuName("姜琳");
		r2.setuSupport(5);
		oldList.add(r2);

		Record r3 = new Record();
		r3.setId(3);
		r3.setuId(3);
		r3.setuName("姜永刚");
		r3.setuSupport(5);
		oldList.add(r3);

		Record r4 = new Record();
		r4.setId(4);
		r4.setuId(4);
		r4.setuName("张楠");
		r4.setuSupport(4);
		oldList.add(r4);

		Record r5 = new Record();
		r5.setId(5);
		r5.setuId(5);
		r5.setuName("马丁");
		r5.setuSupport(4);
		oldList.add(r5);
	}
	
	
}
