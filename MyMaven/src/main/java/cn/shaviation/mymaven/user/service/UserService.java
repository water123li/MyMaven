package cn.shaviation.mymaven.user.service;

import cn.shaviation.mymaven.user.model.User;

public interface UserService{

	public boolean isUserExisted(String username, String password);

	public User addUser(String username, String password);
}
