package cn.shaviation.mymaven.user.dao;

import java.util.List;

import cn.shaviation.mymaven.user.model.User;

public interface UserDao{

	public List<User> getAll();
	public void saveUser(User user);
}
