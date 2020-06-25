package com.javacloud.assetmanagementsystem.cloud.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javacloud.assetmanagementsystem.cloud.dto.UserBean;
import com.javacloud.assetmanagementsystem.cloud.exceptions.UserNotFoundExceptions;
import com.javacloud.assetmanagementsystem.cloud.response.Response;
import com.javacloud.assetmanagementsystem.cloud.services.UserService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userDetailsService;
	

	

	
	@Autowired
	public UserController(UserService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@GetMapping("/user")
	public Response<List<UserBean>> findAllUsers() {
		List<UserBean> details = userDetailsService.findAllUsers();
		return new Response<>(false,"list retrived",details);
	}
	
	@GetMapping("/user/getusers/{id}")
	public Response<UserBean> getByIdUser(@PathVariable int id) {
		UserBean details = userDetailsService.getByIdUser(id);
		
		if (details != null) {
			return new Response<UserBean>(false,"Record found",details);
		}else {
			throw new UserNotFoundExceptions("Record not found");
		}
	}
	
	@DeleteMapping("/user/deleteuser/{id}")
	public Response<UserBean> deleteUsers(@PathVariable  int id) {
		UserBean details  = userDetailsService.getByIdUser(id);
		
		if (details != null) {
			userDetailsService.deleteUser(id);
			return new Response<UserBean>(false, "Deleted ", details);
		}else {
			return new Response<UserBean>(false, "Deleted ", details);

		}

	
	}
	
	
	@PutMapping("/user/updateuser")
	public Response<UserBean> updateUsers(@Valid @RequestBody UserBean details) {
		
		UserBean bean  = userDetailsService.getByIdUser(details.getId());
		details.setRole(bean.getRole());
		details.setPassword(bean.getPassword());
		//details.setRole("ROLE_USER");
		UserBean userBean = userDetailsService.update(details);

		if(userBean != null) {
			userDetailsService.saveUser(userBean);
			return new Response<UserBean>(false, "Updated succesfully ", userBean);
		} else {
			return new Response<UserBean>(true,"Update failed",null);

		}
		
	}
	
	
	@PostMapping("/user/forgetpwd")
	public Response<UserBean> forgetPwd( @RequestBody UserBean details) {

		UserBean bean  = userDetailsService.getByIdUser(details.getId());
		 if(bean != null) {
			 UserBean userBean = userDetailsService.forgetPwd(details);
			 if(userBean != null) {
			 return new Response<>(false,"Password changed succesfully",userBean);
		 }else {
			 return new Response<>(true,"Password couldn't change",null);
		 }
		 }else {
			 return new Response<UserBean>(true,"user does not exist",details);
		 }
		 
	}
	
	@PostMapping("/user/adduser")
	public Response<UserBean> save(@Valid @RequestBody UserBean details) {
		details.setRole("ROLE_USER");

		UserBean details2 = userDetailsService.saveUser(details);
		
		if(details2 != null) {
			return new Response<UserBean>(false,"successfully saved" , details2);
			
		} else {
			return new Response<UserBean>(true,"Save failed",null);
		}
	
	}
	
	@GetMapping("/user/{pageNo}/{usersPerPage}")
	public Page<UserBean> getUserss(@PathVariable int pageNo,@PathVariable int usersPerPage) {
		return userDetailsService.getUserss(pageNo, usersPerPage);
	}
	
	@GetMapping("/user/{pageNo}/{usersPerPage}/{userFieldName}")
	public Page<UserBean> getSortedAssets(@PathVariable int pageNo,@PathVariable int usersPerPage,@PathVariable String userFieldName) {
		return userDetailsService.getSortedAssets(pageNo, usersPerPage, userFieldName);
	}    


}
