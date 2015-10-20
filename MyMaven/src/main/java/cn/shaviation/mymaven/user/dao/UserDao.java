package cn.shaviation.mymaven.user.dao;

import java.util.List;

import cn.shaviation.mymaven.user.model.User;

public interface UserDao{

	/**
	 * 
	 * @return
	 */
	public List<User> getAll();
	/**
	 * 
	 * @param user
	 */
	public void saveUser(User user);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User getUser(Long userId);
	/**
	 * 
	 * @param userId
	 */
	public void deleteUser(Long userId);
}
